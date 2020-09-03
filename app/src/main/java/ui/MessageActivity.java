package ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.HashMap;
import java.util.List;

import adapter.MsgAdapter;
import app.App;
import base.BaseMvpActivity;
import base.BasePresenter;
import bean.LoginBean;
import bean.MessageBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import contract.IMessageContract;
import presenter.MessagePresenter;
import utils.LoadingDialog;
import utils.SpUtil;

/**
 * 消息页面
 */
public class MessageActivity extends BaseMvpActivity<IMessageContract.IMessageModel, IMessageContract.MessagePresenter> implements IMessageContract.IMessageView {

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
    @BindView(R.id.rv_message_list)
    RecyclerView rvMessageList;
    @BindView(R.id.tv_zanwu)
    TextView tvZanwu;
    private LoadingDialog loadingDialog;// 加载中动画

    private LoginBean loginBean;// 登录信息

    private int PAGE = 1;
    private int COUNT = 10;
    private MsgAdapter msgAdapter;

    @Override
    @OnClick({R.id.tv_back, R.id.iv_select_back})
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_select_back:
                break;
        }
    }


    @Override
    public int bindLayout() {
        return R.layout.activity_message;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(Intent intent) {

        loginBean = (LoginBean) intent.getSerializableExtra("loginMsg");

        tvBack.setVisibility(View.VISIBLE);
        ivMessage.setVisibility(View.GONE);

        if (loginBean != null) {
            tvDocName.setText(loginBean.getServer_params().getUSER_NAME());
        }

        rvMessageList.setLayoutManager(new LinearLayoutManager(this));
        msgAdapter = new MsgAdapter(this);
        rvMessageList.setAdapter(msgAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        getMsgList();
    }

    /**
     * 请求消息列表
     */
    private void getMsgList() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("Type", "queryAS_User_MessageByuid");
        params.put("USER_ID", loginBean.getServer_params().getUSER_ID());
        params.put("PAGE", PAGE);
        params.put("PAGESIZE", COUNT);
        params.put("tk", SpUtil.getString(App.getContext(), "tk", null));
        presenter.getMsgList(params);
    }

    /**
     * 设置消息列表数据
     *
     * @param list
     */
    protected void setMsgListData(List<MessageBean.ServerParamsBean.ListBean> list) {
        msgAdapter.setListBeans(list);
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
        return new MessagePresenter();
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
    public void failure(Object msg) {
        Log.e(TAG, (String) msg);
    }

    /**
     * 查询消息列表成功回调
     *
     * @param result
     */
    @Override
    public void onGetMsgSuccess(Object result) {
        if (result != null) {
            if (result instanceof MessageBean) {
                if (((MessageBean) result).getCode().equals("0")) {
                    Log.e(TAG, ((MessageBean) result).getMessage());
                    if (((MessageBean) result).getServer_params().getList().size() != 0) {
                        setMsgListData(((MessageBean) result).getServer_params().getList());
                        rvMessageList.setVisibility(View.VISIBLE);
                        tvZanwu.setVisibility(View.GONE);
                    } else {
                        rvMessageList.setVisibility(View.GONE);
                        tvZanwu.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }
}