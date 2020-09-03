package ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import adapter.RadioAdapter;
import adapter.RiskAdater;
import app.App;
import base.BaseMvpFragment;
import base.BasePresenter;
import bean.LoginBean;
import bean.PatientListBean;
import bean.RiskAssessBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import contract.IRiskFragmentContract;
import presenter.RiskFragmentPresenter;
import utils.PopWindowUtil;

public class RiskAssessFragment extends BaseMvpFragment<IRiskFragmentContract.IRiskFragmentModel, IRiskFragmentContract.RiskFragmentPresenter> implements IRiskFragmentContract.IRiskFragmentView {

    public static final String TAG = "RiskAssessFragment";

    @BindView(R.id.tv_risk_over_time)
    TextView tvRiskOverTime;
    @BindView(R.id.btn_risk_history)
    Button btnRiskHistory;
    @BindView(R.id.btn_risk_next)
    Button btnRiskNext;
    @BindView(R.id.btn_risk_no)
    Button btnRiskNo;

    private RiskAssessViewModel mViewModel;

    @BindView(R.id.rv_radio)
    RecyclerView rvRadio;// 单选切换样式
    @BindView(R.id.rv_question_check)
    RecyclerView rvQuestionCheck;// 问卷题目列表
    @BindView(R.id.btn_save)
    Button btnSave;// 暂时保存按钮
    @BindView(R.id.btn_sign_list)
    Button btnSignList;// 提交按钮
    @BindView(R.id.tv_score_sum)
    TextView tvScoreSum;// 模型总分
    @BindView(R.id.tv_level)
    TextView tvLevel;// 危险等级
    @BindView(R.id.cly_01)
    LinearLayout cly_01;

    private RiskAssessBean.ServerParamsBean.WENJUANNAMEBean wenjuannameBean;// 试卷数据
    private PatientListBean.ServerParamsBean serverParamsBean;// 当前患者信息
    private LoginBean loginBean;// 登录信息
    private RadioAdapter radioAdapter;
    private RiskAdater riskAdater;
    private View contentView;//popupLayout

    public static RiskAssessFragment newInstance() {
        return new RiskAssessFragment();
    }


    @Override
    @OnClick({R.id.btn_risk_history, R.id.btn_risk_next, R.id.btn_risk_no})
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.btn_risk_history:

                break;
            case R.id.btn_risk_next:
                contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_layout_risk_next, null);
                PopWindowUtil.getInstance().makePopupWindow(getActivity(), cly_01, contentView, Color.WHITE);
                PopWindowUtil.getInstance().showLocationWithAnimation(getActivity(), cly_01, 0, 0, 0);
                break;
            case R.id.btn_risk_no:
                contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_layout_risk_next, null);
                PopWindowUtil.getInstance().makePopupWindow(getActivity(), cly_01, contentView, Color.WHITE);
                PopWindowUtil.getInstance().showLocationWithAnimation(getActivity(), cly_01, 0, 0, 0);
                break;
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.risk_assess_fragment;
    }


    @Override
    protected void initView() {
        // 接值
        initGetBundle();
        // 选项
        rvRadio.setLayoutManager(new LinearLayoutManager(App.getContext(), LinearLayoutManager.HORIZONTAL, false));
        radioAdapter = new RadioAdapter(getContext());
        rvRadio.setAdapter(radioAdapter);
        radioAdapter.setXuanxiang(wenjuannameBean.getXUANXIANG());

        // 问卷内容
        rvQuestionCheck.setLayoutManager(new LinearLayoutManager(getContext()));
        riskAdater = new RiskAdater(getContext());
        rvQuestionCheck.setAdapter(riskAdater);


        setRiskData(wenjuannameBean.getXUANXIANG().get(0).getGROUP_TAB_ID());// 初始化设置

        radioAdapter.setItemRadioClick(new RadioAdapter.ItemRadioClick() {
            @Override
            public void setItemRadioClickListener(int tag) {
                setRiskData(tag);
            }
        });
    }

    /**
     * 设置问卷数据
     */
    private void setRiskData(int tag) {
        for (RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean xuanxiangBean : wenjuannameBean.getXUANXIANG()) {
            if (tag == xuanxiangBean.getGROUP_TAB_ID()) {
                riskAdater.setWenjuanBeans(xuanxiangBean.getWENJUAN());
            }
        }
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RiskAssessViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    protected void init() {

    }


    private void initGetBundle() {
        // 从Activity接过来的问卷对象
        wenjuannameBean = (RiskAssessBean.ServerParamsBean.WENJUANNAMEBean) getArguments().getSerializable(TAG);

        serverParamsBean = (PatientListBean.ServerParamsBean) getArguments().getSerializable("patientInfo");

        loginBean = (LoginBean) getArguments().getSerializable("loginMsg");
    }

    @Override
    public BasePresenter initPresenter() {
        return new RiskFragmentPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void failure(Object msg) {

    }
}