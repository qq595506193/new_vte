package ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.DiagnoseAdapter;
import adapter.DocAuditTitleAdapter;
import app.App;
import base.BaseMvpActivity;
import base.BasePresenter;
import bean.DocAuditBean;
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
    @BindView(R.id.tv_diagnose_zanwu)
    TextView tvDiagnoseZanwu;
    @BindView(R.id.showAllGoods)
    TextView showAllGoods;

    private LoadingDialog loadingDialog;// 加载中动画

    private PatientListBean.ServerParamsBean serverParamsBean;// 当前患者信息

    private LoginBean loginBean;// 登录信息
    private DiagnoseAdapter diagnoseAdapter;
    private DocAuditTitleAdapter docAuditTitleAdapter;

    @Override
    @OnClick({R.id.tv_back, R.id.iv_message, R.id.iv_select_back, R.id.showAllGoods})
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

        tvMainTitle.setText("选择报告");
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        rvZhenduanContent.setLayoutManager(gridLayoutManager);
        rvZhenduanContent.setHasFixedSize(true);
        rvZhenduanContent.setNestedScrollingEnabled(false);
        diagnoseAdapter = new DiagnoseAdapter(this);

        // 表名列表
        rvTableList.setLayoutManager(new LinearLayoutManager(App.getContext(), LinearLayoutManager.HORIZONTAL, false));
        docAuditTitleAdapter = new DocAuditTitleAdapter(this);
        rvTableList.setAdapter(docAuditTitleAdapter);

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


    List<DocAuditBean.ServerParamsBean.JibinlistBean> openList = new ArrayList<>();
    List<DocAuditBean.ServerParamsBean.JibinlistBean> hideList = new ArrayList<>();
    private boolean isOpen;

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
     * 设置表头数据
     *
     * @param reportListBeans
     */
    protected void setDocAuditTitleData(List<DocAuditBean.ServerParamsBean.ReportListBean> reportListBeans) {
        if (reportListBeans != null) {
            docAuditTitleAdapter.setReportListBeans(reportListBeans);
        }
    }


    @Override
    public void doBusiness(Context mContext) {

    }

    /**
     * 设置跳转需要的数据
     *
     * @param jibenBean
     */
    public void setJibingData(final DocAuditBean.ServerParamsBean jibenBean) {
        docAuditTitleAdapter.setSetTableItem(new DocAuditTitleAdapter.SetTableItem() {
            @Override
            public void setOnClickTableItem(int position, DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean wenjuanBean) {
                Intent intent = new Intent(DocAuditActivity.this, AuditActivity.class);
                intent.putExtra("wenjuanBean", wenjuanBean);
                intent.putExtra("position", position);
                intent.putExtra("jibenBean", jibenBean);
                intent.putExtra("patientInfo", serverParamsBean);
                intent.putExtra("loginMsg", loginBean);
                startActivity(intent);
                finish();
            }
        });
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
                        setJibingData(((DocAuditBean) result).getServer_params());
                        setListener();
                        setDocAuditTitleData(((DocAuditBean) result).getServer_params().getReportList());
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