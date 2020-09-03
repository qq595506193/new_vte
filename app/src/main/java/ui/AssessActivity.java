package ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.TestTitleAdapter;
import app.App;
import base.BaseMvpActivity;
import base.BasePresenter;
import bean.LoginBean;
import bean.PatientListBean;
import bean.RiskAssessBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import contract.IAssessContract;
import presenter.AssessPresenter;
import ui.fragment.RiskAssessFragment;
import utils.LoadingDialog;
import utils.SpUtil;

/**
 * 评估页
 */
public class AssessActivity extends BaseMvpActivity<IAssessContract.IAssessModel, IAssessContract.AssessPresenter> implements IAssessContract.IAssessView {

    @BindView(R.id.tv_back)
    TextView tvBack;// 返回上一页
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;// 标题
    @BindView(R.id.tv_doc_name)
    TextView tvDocName;// 医生姓名
    @BindView(R.id.iv_message)
    ImageView ivMessage;// 消息按钮
    @BindView(R.id.iv_select_back)
    ImageView ivSelectBack;// 返回登录页
    @BindView(R.id.tv_patient_name)
    TextView tvPatientName;// 患者姓名
    @BindView(R.id.tv_patient_sex)
    TextView tvPatientSex;// 患者性别
    @BindView(R.id.tv_patient_office)
    TextView tvPatientOffice;// 患者科室
    @BindView(R.id.tv_patient_mark)
    TextView tvPatientMark;// 住院号
    @BindView(R.id.rv_test_title)
    RecyclerView rvTestTitle;// 问卷标题列表
    @BindView(R.id.frame_test)
    FrameLayout frameTest;// 插入的fragment

    private LoadingDialog loadingDialog;// 加载中动画

    private PatientListBean.ServerParamsBean serverParamsBean;// 当前患者信息

    private LoginBean loginBean;// 登录信息

    private TestTitleAdapter testTitleAdapter;// 标题列表适配器

    private RiskAssessFragment riskAssessFragment;// fragment页面

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    @OnClick({R.id.tv_back, R.id.iv_select_back, R.id.iv_message})
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                if (tvBack.getVisibility() == View.VISIBLE) {
                    finish();
                }
                break;
            case R.id.iv_message:
                Intent intentMsg = new Intent(this, MessageActivity.class);
                intentMsg.putExtra("loginMsg", loginBean);
                startActivity(intentMsg);
                break;
            case R.id.iv_select_back:
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_assess;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(Intent intent) {
        // 展示出上一页按钮
        if (tvBack.getVisibility() == View.GONE) {
            tvBack.setVisibility(View.VISIBLE);
        }
        serverParamsBean = (PatientListBean.ServerParamsBean) intent.getSerializableExtra("patientInfo");
        loginBean = (LoginBean) intent.getSerializableExtra("loginMsg");

        tvMainTitle.setText("评估");
        if (loginBean != null) {
            tvDocName.setText(loginBean.getServer_params().getUSER_NAME());
        }
        if (serverParamsBean != null) {
            tvPatientName.setText(serverParamsBean.getPATIENT_NAME());
            tvPatientSex.setText(serverParamsBean.getPATIENT_SEX());
            tvPatientOffice.setText(serverParamsBean.getCARE_UNIT_NAME());
            tvPatientMark.setText(serverParamsBean.getPATIENT_ID());
        }

    }

    @Override
    protected void initData() {
        super.initData();
        if (serverParamsBean.getRemindelist().size() != 0 && serverParamsBean.getRemindelist() != null) {
            List<Integer> list = new ArrayList<>();
            for (PatientListBean.ServerParamsBean.RemindelistBean remindelistBean : serverParamsBean.getRemindelist()) {
                list.add(remindelistBean.getFORM_ID());
            }
            getTestTitle(list, serverParamsBean.getPATIENT_ID());// 请求
        }


    }

    /**
     * 设置数据
     */
    private void setData(RiskAssessBean riskAssessBean) {
        setFragmentData(riskAssessBean.getServer_params());
    }

    /**
     * 请求评估问卷
     *
     * @param list
     * @param patient_id
     */
    private void getTestTitle(List<Integer> list, String patient_id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("Type", "queryHZfengxianPG1");
        params.put("FORM_ID", list);
        params.put("PATIENT_ID", patient_id);
        params.put("MERCHANT_ID", LoginActivity.MERCHANT_ID);
        params.put("SITE_ID", LoginActivity.SITE_ID);
        params.put("tk", SpUtil.getString(App.getContext(), "tk", null));
        presenter.getTestTitle(params);
    }

    @Override
    public void doBusiness(Context mContext) {
        testTitleAdapter = new TestTitleAdapter(mContext);
        // 横向展示列表
        rvTestTitle.setLayoutManager(new LinearLayoutManager(App.getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvTestTitle.setAdapter(testTitleAdapter);
    }

    @Override
    public void setListener() {
        testTitleAdapter.setSetOnItemClickListener(new TestTitleAdapter.SetOnItemClickListener() {
            @Override
            public void onItemClickListener(int form_id) {

            }
        });
    }

    private void setFragmentData(RiskAssessBean.ServerParamsBean server_params) {
        // fragment
        riskAssessFragment = new RiskAssessFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_test, riskAssessFragment);
        // 通过bundle传值给MyFragment
        Bundle bundle = new Bundle();

        for (RiskAssessBean.ServerParamsBean.WENJUANNAMEBean wenjuannameBean : server_params.getWENJUANNAME()) {

            bundle.putSerializable(RiskAssessFragment.TAG, wenjuannameBean);// 问卷数据
            bundle.putSerializable("patientInfo", serverParamsBean);// 患者信息
            bundle.putSerializable("loginMsg", loginBean);// 登录用户信息
        }
        riskAssessFragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    @Override
    protected void init() {

    }

    @Override
    public BasePresenter initPresenter() {
        return new AssessPresenter();
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
     * 问卷请求成功回调
     *
     * @param result
     */
    @Override
    public void onTestTitleSuccess(Object result) {
        if (result != null) {
            if (result instanceof RiskAssessBean) {
                if (((RiskAssessBean) result).getCode().equals("0")) {
                    Log.e(TAG, ((RiskAssessBean) result).getMessage());
                    ((RiskAssessBean) result).getServer_params().getWENJUANNAME().get(0).setChe_color(true);
                    testTitleAdapter.setmWenjuannameBeans(((RiskAssessBean) result).getServer_params().getWENJUANNAME());
                    setData(((RiskAssessBean) result));
                }
            }
        }
    }
}