package ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.HashMap;
import java.util.List;

import adapter.SelectTablesAdapter;
import adapter.TableAdapter;
import app.App;
import base.BaseMvpActivity;
import base.BasePresenter;
import bean.LoginBean;
import bean.PatientListBean;
import bean.SelectTablesBean;
import butterknife.BindView;
import butterknife.OnClick;
import contract.ISelectTableContract;
import presenter.SelectTablePresenter;
import utils.LoadingDialog;
import utils.SpUtil;

/**
 * 主动评估选表页
 */
public class SelectTableActivity extends BaseMvpActivity<ISelectTableContract.ISelectTableModel, ISelectTableContract.SelectTablePresenter> implements ISelectTableContract.ISelectTableView {

    @BindView(R.id.tv_back)
    TextView tvBack;// 返回键
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;// 标题
    @BindView(R.id.tv_doc_name)
    TextView tvDocName;// 用户姓名
    @BindView(R.id.iv_message)
    ImageView ivMessage;// 消息按钮
    @BindView(R.id.iv_select_back)
    ImageView ivSelectBack;// 退出程序
    @BindView(R.id.tv_patient_name)
    TextView tvPatientName;// 患者姓名
    @BindView(R.id.tv_patient_sex)
    TextView tvPatientSex;// 患者性别
    @BindView(R.id.tv_patient_office)
    TextView tvPatientOffice;// 患者所属科室
    @BindView(R.id.tv_patient_bed_id)
    TextView tvPatientBedId;// 患者住院号
    @BindView(R.id.rv_select_tables)
    RecyclerView rvSelectTables;// 左侧列表
    @BindView(R.id.rv_table_name)
    RecyclerView rvTableName;// 右侧列表
    @BindView(R.id.btn_start_assess)
    Button btnStartAssess;// 开始评估按钮

    private LoadingDialog loadingDialog;// 加载中动画

    private PatientListBean.ServerParamsBean serverParamsBean;// 当前患者信息
    private LoginBean loginBean;// 登录信息
    private SelectTablesAdapter selectTablesAdapter;
    private TableAdapter tableAdapter;

    @Override
    @OnClick({R.id.tv_back, R.id.iv_message, R.id.iv_select_back, R.id.btn_start_assess})
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_message:
                Intent intentMsg = new Intent(this, MessageActivity.class);
                intentMsg.putExtra("loginMsg", loginBean);
                startActivity(intentMsg);
                break;
            case R.id.iv_select_back:
                break;
            case R.id.btn_start_assess:
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_select_table;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(Intent intent) {
        serverParamsBean = (PatientListBean.ServerParamsBean) intent.getSerializableExtra("patientInfo");
        loginBean = (LoginBean) intent.getSerializableExtra("loginMsg");

        if (tvBack.getVisibility() == View.GONE) {
            tvBack.setVisibility(View.VISIBLE);
        }
        if (serverParamsBean != null) {
            tvPatientName.setText(serverParamsBean.getPATIENT_NAME());
            tvPatientSex.setText(serverParamsBean.getPATIENT_SEX());
            tvPatientOffice.setText(serverParamsBean.getCARE_UNIT_NAME());
            tvPatientBedId.setText(serverParamsBean.getPATIENT_ID());
        }

        rvSelectTables.setLayoutManager(new LinearLayoutManager(this));
        rvTableName.setLayoutManager(new GridLayoutManager(this, 4));

        selectTablesAdapter = new SelectTablesAdapter(this);
        tableAdapter = new TableAdapter(this);

        rvSelectTables.setAdapter(selectTablesAdapter);
        rvTableName.setAdapter(tableAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        if (serverParamsBean != null) {
            getRiskTableList(serverParamsBean.getPATIENT_ID());
        }
    }

    /**
     * 请求可评估的表
     *
     * @param patient_id
     */
    private void getRiskTableList(String patient_id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("Type", "queryBusinessForms");
        params.put("PATIENT_ID", patient_id);
        params.put("tk", SpUtil.getString(App.getContext(), "tk", null));
        presenter.getRiskTableList(params);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void setListener() {
        // 左侧类别选中回调

        // 选中后回调

    }

    @Override
    protected void init() {

    }


    /**
     * 初始化设置请求到的数据
     */
    private void setTableData(List<SelectTablesBean.ServerParamsBean.BusinesslistBean> businesslistBeans) {
        selectTablesAdapter.setBusinesslistBeans(businesslistBeans);
        setTableNameData(businesslistBeans, businesslistBeans.get(0).getListforms());
        tableAdapter.notifyDataSetChanged();// 第一次设置数据的时候刷新一次右侧列表
    }

    /**
     * 设置右侧数据
     *
     * @param businesslistBeans
     * @param listformsBeans
     */
    private void setTableNameData(List<SelectTablesBean.ServerParamsBean.BusinesslistBean> businesslistBeans, List<SelectTablesBean.ServerParamsBean.BusinesslistBean.ListformsBean> listformsBeans) {
        tableAdapter.setBusinesslistBeans(businesslistBeans);
        tableAdapter.setListformsBeans(listformsBeans);
    }

    @Override
    public BasePresenter initPresenter() {
        return new SelectTablePresenter();
    }

    @Override
    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.getDialog(this,
                    "努力加载中",
                    false,
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
    public void failure(Object msg) {
        Log.e(TAG, (String) msg);
    }

    /**
     * 查询可评估的表成功回调
     *
     * @param result
     */
    @Override
    public void onRiskTableListSuccess(Object result) {
        if (result != null) {
            if (result instanceof SelectTablesBean) {
                if (((SelectTablesBean) result).getCode().equals("0")) {
                    Log.e(TAG, ((SelectTablesBean) result).getMessage());
                    setTableData(((SelectTablesBean) result).getServer_params().getBusinesslist());
                }
            }
        }
    }
}