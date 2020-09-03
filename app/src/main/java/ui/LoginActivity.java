package ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.new_vte_2020_01.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import app.App;
import base.BaseMvpActivity;
import base.BasePresenter;
import bean.LoginBean;
import butterknife.BindView;
import butterknife.OnClick;
import contract.ILoginContract;
import presenter.LoginPresenter;
import utils.LoadingDialog;
import utils.SpUtil;

/**
 * 登录页
 */
public class LoginActivity extends BaseMvpActivity<ILoginContract.ILoginModel, ILoginContract.LoginPresenter> implements ILoginContract.ILoginView {

    public static final String TAG_EXIT = "LoginActivity";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ed_username)
    EditText edUsername;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private boolean isFinish;

    private LoadingDialog loadingDialog;


    @Override
    @OnClick({R.id.btn_login, R.id.iv_back})
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(edUsername.getText().toString())) {
                    showToast("请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(edPassword.getText().toString())) {
                    showToast("请输入密码");
                    return;
                }
                Log.e(TAG, "点击了登陆");
                login(edUsername.getText().toString(), edPassword.getText().toString());
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(Intent intent) {
        isFinish = intent.getBooleanExtra(TAG_EXIT, false);
    }

    /**
     * 登录
     */
    private void login(String ACCOUNT, String PASS_WORD) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("tk", "");
        params.put("Type", "login");
        params.put("ACCOUNT", ACCOUNT);
        params.put("PASS_WORD", PASS_WORD);
        params.put("SITE_ID", "3100");
        params.put("MERCHANT_ID", "3100");
        presenter.getLogin(params);
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
        return new LoginPresenter();
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
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        super.onDestroy();
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
     * 登录成功回调
     *
     * @param result
     */
    @Override
    public void onLoginSuccess(Object result) {
        if (result != null) {
            if (result instanceof LoginBean) {
                SpUtil.putString(App.getContext(), "tk", ((LoginBean) result).getServer_params().getTk());
                Intent intent = new Intent(this, SelectActivity.class);
                intent.putExtra("loginMsg", (Serializable) result);
                startActivity(intent);
            }
        }
    }

    /**
     * 登录失败回调
     *
     * @param error
     */
    @Override
    public void onLoginFailure(Object error) {
        if (error != null) {
            Log.e(TAG, (String) error);
        }
    }

}