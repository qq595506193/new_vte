package ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.List;

import adapter.RadioAdapter;
import adapter.RiskAdater;
import app.App;
import base.BaseMvpFragment;
import base.BasePresenter;
import bean.LoginBean;
import bean.PatientListBean;
import bean.RiskAssessBean;
import butterknife.BindView;
import contract.IRiskFragmentContract;
import presenter.RiskFragmentPresenter;

public class RiskAssessFragment extends BaseMvpFragment<IRiskFragmentContract.IRiskFragmentModel, IRiskFragmentContract.RiskFragmentPresenter> implements IRiskFragmentContract.IRiskFragmentView {

    public static final String TAG = "RiskAssessFragment";

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

    private RiskAssessBean.ServerParamsBean.WENJUANNAMEBean wenjuannameBean;// 试卷数据
    private PatientListBean.ServerParamsBean serverParamsBean;// 当前患者信息
    private LoginBean loginBean;// 登录信息
    private RadioAdapter radioAdapter;
    private RiskAdater riskAdater;

    public static RiskAssessFragment newInstance() {
        return new RiskAssessFragment();
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