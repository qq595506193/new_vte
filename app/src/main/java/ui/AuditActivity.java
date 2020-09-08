package ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.AuditSuggestAdapter;
import adapter.DiagnoseAdapter;
import adapter.DocElementAdater;
import adapter.SupplementAdapter;
import app.App;
import base.BaseMvpActivity;
import base.BasePresenter;
import bean.ApproveBean;
import bean.CancellationBean;
import bean.DocAuditBean;
import bean.HistoryAssessBean;
import bean.LoginBean;
import bean.PatientListBean;
import butterknife.BindView;
import butterknife.OnClick;
import contract.IAuditContract;
import presenter.AuditPresenter;
import utils.LoadingDialog;
import utils.SpUtil;

/**
 * 审核报告页
 */
public class AuditActivity extends BaseMvpActivity<IAuditContract.IAuditModel, IAuditContract.AuditPresenter> implements IAuditContract.IAuditView {

    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_doc_name)
    TextView tvDocName;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.iv_select_back)
    ImageView ivSelectBack;
    @BindView(R.id.tv_patient_name)
    TextView tvPatientName;
    @BindView(R.id.tv_patient_sex)
    TextView tvPatientSex;
    @BindView(R.id.tv_patient_office)
    TextView tvPatientOffice;
    @BindView(R.id.tv_patient_mark)
    TextView tvPatientMark;
    @BindView(R.id.rv_zhenduan_content)
    RecyclerView rvZhenduanContent;
    @BindView(R.id.showAllGoods)
    TextView showAllGoods;
    @BindView(R.id.tv_diagnose_zanwu)
    TextView tvDiagnoseZanwu;
    @BindView(R.id.rv_buchong_content)
    RecyclerView rvBuchongContent;
    @BindView(R.id.showAllGoods_buchong)
    TextView showAllGoodsBuchong;
    @BindView(R.id.tv_diagnose_zanwu_buchong)
    TextView tvDiagnoseZanwuBuchong;
    @BindView(R.id.txt_coding)
    TextView txtCoding;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.txt_grade)
    TextView txtGrade;
    @BindView(R.id.txt_show)
    TextView txtShow;
    @BindView(R.id.iv_yuan)
    ImageView ivYuan;
    @BindView(R.id.rv_element)
    RecyclerView rvElement;
    @BindView(R.id.rv_suggest)
    RecyclerView rvSuggest;
    @BindView(R.id.btn_zuofei)
    Button btnZuofei;
    @BindView(R.id.btn_tongguo)
    Button btnTongguo;

    private int position;

    private DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean wenjuanBean;//

    private DocAuditBean.ServerParamsBean jibenBean;

    private LoadingDialog loadingDialog;// 加载中动画

    private PatientListBean.ServerParamsBean serverParamsBean;// 当前患者信息

    private LoginBean loginBean;// 登录信息
    private DiagnoseAdapter diagnoseAdapter;
    private SupplementAdapter supplementAdapter;

    List<DocAuditBean.ServerParamsBean.JibinlistBean> openList = new ArrayList<>();
    List<DocAuditBean.ServerParamsBean.JibinlistBean> hideList = new ArrayList<>();

    List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.WxysBean> openList_wxys = new ArrayList<>();
    List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.WxysBean> hideList_wxys = new ArrayList<>();
    private boolean isOpen;
    private DocElementAdater docElementAdater;
    private AuditSuggestAdapter auditSuggestAdapter;
    private List<String> addElseText = new ArrayList<>();// 存输入框中的值
    private List<String> docAdviceStr;// 存医生建议集合
    private List<String> nuresStr;// 存护士建议集合
    private List<String> noteStr;// 存患者温馨提示集合

    @Override
    @OnClick({R.id.tv_back, R.id.iv_message, R.id.iv_select_back, R.id.showAllGoods, R.id.showAllGoods_buchong, R.id.btn_zuofei, R.id.btn_tongguo})
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_message:
                break;
            case R.id.iv_select_back:
                break;
            case R.id.showAllGoods:
                if (isOpen) {
                    diagnoseAdapter.setHideList(hideList);
                    showAllGoods.setText("展开");
//                    showAllGoods.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ar_down, 0);
                    isOpen = false;
                } else {
                    diagnoseAdapter.setOpenList(openList);
                    showAllGoods.setText("收起");
//                    showAllGoods.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ar_up, 0);
                    isOpen = true;
                }
                break;
            case R.id.showAllGoods_buchong:
                if (isOpen) {
                    supplementAdapter.setHideList(hideList_wxys);
                    showAllGoodsBuchong.setText("展开");
//                    showAllGoodsBuchong.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ar_down, 0);
                    isOpen = false;
                } else {
                    supplementAdapter.setOpenList(openList_wxys);
                    showAllGoodsBuchong.setText("收起");
//                    showAllGoodsBuchong.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ar_up, 0);
                    isOpen = true;
                }
                break;
            case R.id.btn_zuofei:
                getCiJuanZuoFei();
                break;
            case R.id.btn_tongguo:
                getShenHeTongGuo();
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_audit;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(Intent intent) {
        if (tvBack.getVisibility() == View.GONE) {
            tvBack.setVisibility(View.VISIBLE);
        }
        position = intent.getIntExtra("position", 0);
        jibenBean = (DocAuditBean.ServerParamsBean) intent.getSerializableExtra("jibenBean");
        wenjuanBean = (DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean) intent.getSerializableExtra("wenjuanBean");
        serverParamsBean = (PatientListBean.ServerParamsBean) intent.getSerializableExtra("patientInfo");
        loginBean = (LoginBean) intent.getSerializableExtra("loginMsg");

        tvMainTitle.setText("报告审核");
        if (loginBean != null) {
            tvDocName.setText(loginBean.getServer_params().getUSER_NAME());
        }
        if (serverParamsBean != null) {
            tvPatientName.setText(serverParamsBean.getPATIENT_NAME());
            tvPatientSex.setText(serverParamsBean.getPATIENT_SEX());
            tvPatientOffice.setText(serverParamsBean.getCARE_UNIT_NAME());
            tvPatientMark.setText(serverParamsBean.getPATIENT_ID());
        }

        // 诊断情况
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        rvZhenduanContent.setLayoutManager(linearLayoutManager);
        rvZhenduanContent.setHasFixedSize(true);
        rvZhenduanContent.setNestedScrollingEnabled(false);
        diagnoseAdapter = new DiagnoseAdapter(this);


        if (jibenBean != null) {
            setDiagnoseData(jibenBean.getJibinlist());
        }
        // 补充项
        LinearLayoutManager linearLayoutManager_buchong = new LinearLayoutManager(this);
        linearLayoutManager_buchong.setSmoothScrollbarEnabled(true);
        linearLayoutManager_buchong.setAutoMeasureEnabled(true);
        rvBuchongContent.setLayoutManager(linearLayoutManager_buchong);
        rvBuchongContent.setHasFixedSize(true);
        rvBuchongContent.setNestedScrollingEnabled(false);
        supplementAdapter = new SupplementAdapter(this);

        // 危险因素列表
        // recyclerview 添加item的下划线，和方向
        rvElement.setItemAnimator(new DefaultItemAnimator());
        rvElement.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL));
        //
        rvElement.setLayoutManager(new LinearLayoutManager(this));
        docElementAdater = new DocElementAdater(this);
        rvElement.setAdapter(docElementAdater);


        /**
         * 加入header
         */
        setAdaperHeader();

        // 建议
        rvSuggest.setLayoutManager(new LinearLayoutManager(this));
        auditSuggestAdapter = new AuditSuggestAdapter(this);
        rvSuggest.setAdapter(auditSuggestAdapter);

        if (wenjuanBean != null) {
            setSupplementData(wenjuanBean.getWxys());
            setReportData(wenjuanBean);
            setElementData(wenjuanBean.getWxys());
        }
        if (jibenBean != null) {
            setSuggestData(jibenBean.getReportList().get(position).getWENJUAN());
        }

        addElseText.add("");// 初始化其他建议
    }

    private void setAdaperHeader() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_wxys_header, null);
        docElementAdater.setHeader(view);
    }

    /**
     * 此卷作废请求
     */
    private void getCiJuanZuoFei() {
        HashMap<String, Object> params = new HashMap<>();
        if (wenjuanBean != null) {
            params.put("Type", "updateHM_Report_Hp");
            params.put("tk", SpUtil.getString(App.getContext(), "tk", null));
            params.put("REPORT_ID", wenjuanBean.getREPORT_ID());
        }
        presenter.getCancellation(params);
        Log.e(TAG, "请求此卷作废");

    }

    /**
     * 审核通过请求
     */
    private void getShenHeTongGuo() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("tk", SpUtil.getString(App.getContext(), "tk", null));
        params.put("Type", "saveHM_Report_Hp_Advice");
        if (addElseText != null) {
            for (String s : addElseText) {
                params.put("ELSEADC", s);
            }
        }
        if (docAdviceStr != null) {
            params.put("DOCTORADC", docAdviceStr);
        }
        if (nuresStr != null) {
            params.put("NURSEADC", nuresStr);
        }
        if (noteStr != null) {
            params.put("PATIENTADC", noteStr);
        }
        if (wenjuanBean != null) {
            params.put("REPORT_ID", wenjuanBean.getREPORT_ID());
        }
        presenter.getApprove(params);
        Log.e(TAG, "请求审核通过");
    }


    /**
     * 获取输入框的值
     *
     * @param position
     * @param str
     */
    public void saveEditData(int position, String str) {
        addElseText.clear();
        addElseText.add(str);
        showToast(str + "----" + position);
    }

    /**
     * 设置建议
     *
     * @param wenjuan
     */
    private void setSuggestData(List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean> wenjuan) {
        auditSuggestAdapter.setWenjuanBeans(wenjuan);
    }

    /**
     * 危险因素
     *
     * @param wxysBeans
     */
    private void setElementData(List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.WxysBean> wxysBeans) {
        docElementAdater.setWxysBeans(wxysBeans);
    }

    /**
     * 设置报告数据
     *
     * @param wenjuanBean
     */
    private void setReportData(DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean wenjuanBean) {
        for (DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.SublistBean sublistBean : wenjuanBean.getSublist()) {
            txtTotal.setText(sublistBean.getCURRENT_RISK_VALUE() + "分");
            txtCoding.setText(sublistBean.getREPORT_CODE());
            txtTime.setText(sublistBean.getREPORT_TIME());
            // 危险等级
            switch (sublistBean.getCURRENT_RISK_LEVEL()) {
                case "5":
                    ivYuan.setImageResource(R.mipmap.diwei_yuan);
                    txtGrade.setText(sublistBean.getRISK_NAME());
                    txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelGreenColor));
                    break;
                case "6":
                    ivYuan.setImageResource(R.mipmap.zhongwei_yuan);
                    txtGrade.setText(sublistBean.getRISK_NAME());
                    txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelBlueColor));

                    break;
                case "7":
                    ivYuan.setImageResource(R.mipmap.gaowei_yuan);
                    txtGrade.setText(sublistBean.getRISK_NAME());
                    txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelYellowColor));

                    break;
                case "8":
                    ivYuan.setImageResource(R.mipmap.jigaowei_yuan);
                    txtGrade.setText(sublistBean.getRISK_NAME());
                    txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelOrangeColor));

                    break;
                case "9":
                    ivYuan.setImageResource(R.mipmap.quezhen_yuan);
                    txtGrade.setText(sublistBean.getRISK_NAME());
                    txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelRedColor));

                    break;
                case "21":
                    ivYuan.setImageResource(R.mipmap.chuxue_diwei);
                    txtGrade.setText(sublistBean.getRISK_NAME());
                    txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelGreenColor));

                    break;
                case "22":
                    ivYuan.setImageResource(R.mipmap.chuxue_gaowei);
                    txtGrade.setText(sublistBean.getRISK_NAME());
                    txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelRedColor));

                    break;
                default:
                    txtGrade.setText("暂无危险等级");
                    txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelRedColor));
                    break;
            }
            ///
            txtShow.setText(sublistBean.getRISK_DETAIL_DESC());
            txtTime.setText(sublistBean.getREPORT_TIME());// 设置时间
        }
    }


    /**
     * 设置诊断内容列表数据
     */
    private void setDiagnoseData(List<DocAuditBean.ServerParamsBean.JibinlistBean> jibinlistBeans) {
        diagnoseAdapter.setJibinlistBeans(jibinlistBeans);

        if (jibinlistBeans.size() > 4) { //设置大于多少条数据开始隐藏
            for (int i = 0, j = jibinlistBeans.size(); i < j; i++) {
                openList.add(jibinlistBeans.get(i));
            }
            for (int i = 0; i < 4; i++) {
                hideList.add(jibinlistBeans.get(i));
            }
            diagnoseAdapter.setHideList(hideList);
        } else {
            diagnoseAdapter.setRealList(jibinlistBeans);
        }
        showAllGoods.setVisibility(jibinlistBeans.size() > 4 ? View.VISIBLE : View.GONE);
        rvZhenduanContent.setAdapter(diagnoseAdapter);
    }

    /**
     * 设置补充项列表数据
     *
     * @param wxysBeans
     */
    private void setSupplementData(List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.WxysBean> wxysBeans) {
        supplementAdapter.setWxysBeans(wxysBeans);
        if (wxysBeans.size() > 4) { //设置大于多少条数据开始隐藏
            for (int i = 0, j = wxysBeans.size(); i < j; i++) {
                openList_wxys.add(wxysBeans.get(i));
            }
            for (int i = 0; i < 4; i++) {
                hideList_wxys.add(wxysBeans.get(i));
            }
            supplementAdapter.setHideList(hideList_wxys);
        } else {
            supplementAdapter.setRealList(wxysBeans);
        }
        showAllGoodsBuchong.setVisibility(wxysBeans.size() > 4 ? View.VISIBLE : View.GONE);
        rvBuchongContent.setAdapter(supplementAdapter);
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void setListener() {
        auditSuggestAdapter.setSetSuggestArray(new AuditSuggestAdapter.SetSuggestArray() {
            @Override
            public void onDocSuggestArrayListener(List<String> docAdviceStr) {
                AuditActivity.this.docAdviceStr = docAdviceStr;
            }

            @Override
            public void onNurseSuggestArrayListener(List<String> nuresStr) {
                AuditActivity.this.nuresStr = nuresStr;
            }

            @Override
            public void onNoteSuggestArrayListener(List<String> noteStr) {
                AuditActivity.this.noteStr = noteStr;
            }
        });
    }

    @Override
    protected void init() {

    }

    @Override
    public BasePresenter initPresenter() {
        return new AuditPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void failure(Object msg) {
        Log.e(TAG, (String) msg);
    }

    /**
     * 审核通过成功回调
     *
     * @param result
     */
    @Override
    public void onApproveSuccess(Object result) {
        if (result != null) {
            if (result instanceof ApproveBean) {
                if (((ApproveBean) result).getCode().equals("0")) {
                    Log.e(TAG, ((ApproveBean) result).getMessage());
                    showToast("审核已通过");
                    finish();
                } else {
                    Log.e(TAG, ((ApproveBean) result).getMessage());
                }
            }
        }
    }

    /**
     * 此卷作废成功回调
     *
     * @param result
     */
    @Override
    public void onCancellationSuccess(Object result) {
        if (result != null) {
            if (result instanceof CancellationBean) {
                if (((CancellationBean) result).getCode().equals("0")) {
                    Log.e(TAG, ((CancellationBean) result).getMessage());
                    showToast("此卷作废" + ((CancellationBean) result).getMessage());
                    finish();
                }
            }
        }
    }
}