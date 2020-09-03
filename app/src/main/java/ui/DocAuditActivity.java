package ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.HashMap;
import java.util.List;

import adapter.DiagnoseAdapter;
import app.App;
import base.BaseMvpActivity;
import base.BasePresenter;
import bean.DocAuditBean;
import bean.HistoryAssessBean;
import bean.LoginBean;
import bean.PatientListBean;
import butterknife.BindView;
import butterknife.OnClick;
import contract.IDocAuditContract;
import presenter.DocAuditPresenter;
import utils.LoadingDialog;
import utils.SpUtil;

public class DocAuditActivity extends BaseMvpActivity<IDocAuditContract.IDocAuditModel, IDocAuditContract.DocAuditPresenter> implements IDocAuditContract.IDocAuditView {

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
    @BindView(R.id.rv_table_list)
    RecyclerView rvTableList;
    @BindView(R.id.rv_doc_audit)
    RecyclerView rvDocAudit;
    @BindView(R.id.tv_diagnose_zanwu)
    TextView tvDiagnoseZanwu;

    private LoadingDialog loadingDialog;// 加载中动画

    private PatientListBean.ServerParamsBean serverParamsBean;// 当前患者信息

    private LoginBean loginBean;// 登录信息
    private DiagnoseAdapter diagnoseAdapter;

    @Override
    @OnClick({R.id.tv_back, R.id.iv_message, R.id.iv_select_back})
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_message:
                break;
            case R.id.iv_select_back:
                break;
        }
    }


    @Override
    public int bindLayout() {
        return R.layout.activity_doc_audit;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(Intent intent) {
        if (tvBack.getVisibility() == View.GONE) {
            tvBack.setVisibility(View.VISIBLE);
        }
        serverParamsBean = (PatientListBean.ServerParamsBean) intent.getSerializableExtra("patientInfo");
        loginBean = (LoginBean) intent.getSerializableExtra("loginMsg");

        tvMainTitle.setText("历史报告");
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
        rvZhenduanContent.setLayoutManager(new GridLayoutManager(this, 2));
        diagnoseAdapter = new DiagnoseAdapter(this);
        rvZhenduanContent.setAdapter(diagnoseAdapter);

    }

    @Override
    protected void initData() {
        super.initData();
        if (serverParamsBean != null) {
            getDocAudit(serverParamsBean.getPATIENT_ID());
        }
    }

    private void getDocAudit(String patient_id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("Type", "queryJibingReportLIST");
        params.put("PATIENT_ID", patient_id);
        params.put("tk", SpUtil.getString(App.getContext(), "tk", null));
        params.put("CONFIRM_FLAG", "10");
        presenter.getDocAudit(params);
    }

    /**
     * 设置诊断内容列表数据
     */
    private void setDiagnoseData(List<DocAuditBean.ServerParamsBean.JibinlistBean> jibinlistBeans) {
        diagnoseAdapter.setJibinlistBeans(jibinlistBeans);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void setListener() {

    }

    @Override
    protected void init() {

    }

    @Override
    public BasePresenter initPresenter() {
        return new DocAuditPresenter();
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
     * 查询医生审核列表成功回调
     *
     * @param result
     */
    @Override
    public void onDocAuditSuccess(Object result) {
        if (result != null) {
            if (result instanceof DocAuditBean) {
                if (((DocAuditBean) result).getCode().equals("0")) {
                    Log.e(TAG, ((DocAuditBean) result).getMessage());
                    if (((DocAuditBean) result).getServer_params().getJibinlist().size() != 0) {
                        setDiagnoseData(((DocAuditBean) result).getServer_params().getJibinlist());
                        tvDiagnoseZanwu.setVisibility(View.GONE);
                        rvZhenduanContent.setVisibility(View.VISIBLE);
                    } else {
                        tvDiagnoseZanwu.setVisibility(View.VISIBLE);
                        rvZhenduanContent.setVisibility(View.GONE);
                    }
                }
            }
        }
    }
}