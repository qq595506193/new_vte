package ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.BedAdapter;
import app.App;
import base.BaseMvpActivity;
import base.BasePresenter;
import base.DialogCallback;
import bean.LoginBean;
import bean.LoginTypeBean;
import bean.PatientListBean;
import bean.RefreshPatientBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import contract.IPatientListContract;
import presenter.PatientListPresenter;
import service.TimerService;
import utils.LoadingDialog;
import utils.SpUtil;

public class SelectActivity extends BaseMvpActivity<IPatientListContract.IPatientListModel, IPatientListContract.PatientListPresenter> implements IPatientListContract.IPatientListView {

    public static final String TAG_INDEX = "SelectActivity";

    @BindView(R.id.tv_doc_name)
    TextView tvDocName;// 医生姓名
    @BindView(R.id.iv_message)
    ImageView ivMessage;// 消息图标
    @BindView(R.id.iv_select_back)
    ImageView ivSelectBack;// 返回键
    @BindView(R.id.tv_hospital_id)
    TextView tvHospitalId;// 住院号
    @BindView(R.id.et_hospital_id)
    EditText etHospitalId;
    @BindView(R.id.tv_name)
    TextView tvName;// 患者姓名
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_sex)
    TextView tvSex;// 患者性别
    @BindView(R.id.sp_sex)
    AppCompatSpinner spSex;
    @BindView(R.id.tv_bed)
    TextView tvBed;// 患者床位号
    @BindView(R.id.et_bed)
    EditText etBed;
//    @BindView(R.id.rb_section)
//    RadioButton rbSection;// 科室单元
//    @BindView(R.id.rb_element)
//    RadioButton rbElement;
    @BindView(R.id.rg_check)
    RadioGroup rgCheck;
    @BindView(R.id.rv_bed)
    RecyclerView rvBed;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;// 页面标题
    @BindView(R.id.tv_null_background)
    TextView tvNullBackground;// 空背景
    @BindView(R.id.sp_type)
    AppCompatSpinner spType;// 状态选择

    private String VISIT_SQ_NO = "";// 床位号
    private String PATIENT_NAME = "";// 患者名称
    private String PATIENT_SEX = "";// 性别  M男   W女
    private String sp_sex_str = "";// 性别的值
    private String sp_type_str = "";// 状态的值
    private String CURRENT_RISK_LEVEL = "";// 危险等级，暂传空串
    private String BED_NUMBER = "";// 床位Id
    private List<String> DEPARTMENT_ID = new ArrayList<>();// 本科室
    private String CARE_UNIT = "";// 本单元

    private LoginBean loginBean;// 登录的用户信息
    private BedAdapter bedAdapter;// 床位图适配器
    private AlertDialog.Builder builder;

    private boolean isShowDiaLog = true;// dialog提示框是否弹出

    private LoadingDialog loadingDialog;// 加载中动画

    private List<Integer> sectionList;// 本科室集合


    @Override
    @OnClick({R.id.iv_message, R.id.iv_select_back})
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.iv_message:
                // 点击消息按钮
                break;
            case R.id.iv_select_back:
                // 点击返回键
                finish();
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_select;
    }

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public void initView(Intent intent) {
        loginBean = (LoginBean) intent.getSerializableExtra("loginMsg");
        if (loginBean != null) {
            tvMainTitle.setText("患者列表");
            tvDocName.setText(loginBean.getServer_params().getUsrmesg().getROBOT_NAME());
        }

        sectionList = new ArrayList<>();

        sectionList.add(loginBean.getServer_params().getDEPARTMENT());// 初始化默认科室


        rgCheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rb_section) {
                    sectionList.clear();
                    sectionList.add(loginBean.getServer_params().getDEPARTMENT());
                    getPatientList();
                } else if (checkedId == R.id.rb_element) {
                    sectionList.clear();
                    CARE_UNIT = loginBean.getServer_params().getCARE_UNIT();
                    getPatientList();
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        getPatientList();
        getRefreshPatient();
        builder = new AlertDialog.Builder(this);
        //启动Android定时器，并且启动服务
        TimerService.getConnet(this, SelectActivity.this);

    }

    /**
     * 刷新患者状态，是否有新患者，是否有需要提醒的评估
     */
    public void getRefreshPatient() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("tk", SpUtil.getString(App.getContext(), "tk", null));
        params.put("Type", "queryHM_Patient_To_Assess");
        presenter.getRefreshPatient(params);
        Log.e(TAG, "查询了一次提醒状态");
    }

    /**
     * 请求床位图
     */
    public void getPatientList() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("tk", SpUtil.getString(App.getContext(), "tk", null));
        params.put("Type", "queryPatient_Basic_InfoBybed");
        params.put("CURRENT_RISK_LEVEL", CURRENT_RISK_LEVEL);// 危险等级
        params.put("PATIENT_NAME", PATIENT_NAME);// 患者名称
        params.put("PATIENT_SEX", PATIENT_SEX);// 患者性别
        params.put("BED_NUMBER", BED_NUMBER);// 床位号
        if (sectionList.size() != 0) {
            params.put("DEPARTMENT_ID", sectionList);// 科室
            params.put("CARE_UNIT", "");// 单元
        } else {
            params.put("DEPARTMENT_ID", "");// 科室
            params.put("CARE_UNIT", CARE_UNIT);// 单元
        }
        presenter.getPatientList(params);
        Log.e(TAG, "请求了一次床位图");
    }

    /**
     * 查询登录状态
     */
    public void getLoginType() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("tk", SpUtil.getString(App.getContext(), "tk", null));
        params.put("Type", "xt");
        params.put("_", loginBean.getServer_params().getACCOUNT());
        presenter.getLoginType(params);
        Log.e(TAG, "查询了一次登录状态");
    }

    @Override
    public void doBusiness(Context mContext) {
        bedAdapter = new BedAdapter(mContext);
        rvBed.setLayoutManager(new GridLayoutManager(mContext, 4));
        rvBed.setAdapter(bedAdapter);

        // 性别选择
        if (spSex != null) {
            spSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {// 监听获取Spinner的值

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {

                    //拿到被选择项的值
                    sp_sex_str = (String) spSex.getSelectedItem();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });
        }
        // 状态选择
        if (spType != null) {
            spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //拿到被选择项的值
                    sp_type_str = (String) spType.getSelectedItem();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @Override
    public void setListener() {
        bedAdapter.setRefreshList(new BedAdapter.RefreshList() {
            @Override
            public void setRefreshListener() {
                getPatientList();// 刷新床位图
            }
        });
        // 条目卡片点击事件
        bedAdapter.setItemClick(new BedAdapter.ItemClick() {
            @Override
            public void setItemClickListener(PatientListBean.ServerParamsBean serverParamsBean, int position) {
                Intent intent = new Intent(SelectActivity.this, HistoryActivity.class);
                intent.putExtra("patientInfo", serverParamsBean);
                intent.putExtra("loginMsg", loginBean);
                startActivity(intent);
            }
        });

        bedAdapter.setItemPingClick(new BedAdapter.ItemPingClick() {
            @Override
            public void setItemPingClickListener(PatientListBean.ServerParamsBean serverParamsBean) {
                Intent intent = new Intent(SelectActivity.this, AssessActivity.class);
                intent.putExtra("patientInfo", serverParamsBean);
                intent.putExtra("loginMsg", loginBean);
                startActivity(intent);
            }
        });

        rgCheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_section:
                        // 科室
                        DEPARTMENT_ID.add("309");
                        CARE_UNIT = "";
                        break;
                    case R.id.rb_element:
                        // 单元
                        DEPARTMENT_ID.clear();
                        CARE_UNIT = "1101";
                        break;
                }
            }
        });
    }

    @Override
    protected void init() {

    }

    @Override
    public BasePresenter initPresenter() {
        return new PatientListPresenter();
    }

    @Override
    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.getDialog(this,
                    "努力加载中",
                    true,
                    null);
        } else if (loadingDialog.isShowing()) {
            loadingDialog.setMessage("努力加载中");
        }
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        //停止由AlarmManager启动的循环
        TimerService.stop(this);
        //停止由服务启动的循环
        Intent intent = new Intent(this, TimerService.class);
        stopService(intent);
        super.onDestroy();
    }

    /**
     * 判断是否有数据，没有则展示空白页
     *
     * @param isShow
     */
    private void isShowNullGround(boolean isShow) {
        if (tvNullBackground != null) {
            if (isShow) {
                if (tvNullBackground.getVisibility() == View.GONE) {
                    tvNullBackground.setVisibility(View.VISIBLE);
                }
            } else {
                if (tvNullBackground.getVisibility() == View.VISIBLE) {
                    tvNullBackground.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 请求失败回调
     *
     * @param msg
     */
    @Override

    public void failure(Object msg) {
        if (msg != null) {
            Log.e(TAG, (String) msg);
        }
    }


    /**
     * 登录状态监听
     *
     * @param reslut
     */
    @Override
    public void onLoginTypeSuccess(Object reslut) {
        if (reslut != null) {
            if (reslut instanceof LoginTypeBean) {
                if (((LoginTypeBean) reslut).getCode().equals("0")) {
                    // 账号正常登录

                } else if (((LoginTypeBean) reslut).getCode().equals("99")) {
                    // 账号被顶掉
                    if (isShowDiaLog) {
                        EventBus.getDefault().post(99);
                        isShowDiaLog = false;
                    }
                } else {

                }
            }
        }
    }

    /**
     * 床位图请求成功回调
     *
     * @param reslut
     */
    @Override
    public void onPatientListSuccess(Object reslut) {
        if (reslut != null) {
            if (reslut instanceof PatientListBean) {
                if (((PatientListBean) reslut).getCode().equals("0")) {
                    // 隐藏空背景
                    isShowNullGround(false);
                    // 为床位图适配器设置数据
                    bedAdapter.setmParamsBeanList(((PatientListBean) reslut).getServer_params());
                } else {
                    // 显示空背景
                    isShowNullGround(true);
                }
                List<String> DEPARTMENT_ID = new ArrayList<>();
                DEPARTMENT_ID.add("");
            }
        }
    }

    /**
     * 查询患者状态，并提醒刷新
     *
     * @param reslut
     */
    @Override
    public void onRefreshPatientSuccess(Object reslut) {
        if (reslut != null) {
            if (reslut instanceof RefreshPatientBean) {
                if (((RefreshPatientBean) reslut).getCode().equals("0")) {
                    RefreshPatientBean.ServerParamsBean serverParamsBean = ((RefreshPatientBean) reslut).getServer_params();

                    bedAdapter.setmServerParamsBeans(serverParamsBean);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}