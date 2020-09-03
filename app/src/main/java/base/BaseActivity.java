package base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.new_vte_2020_01.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import app.App;
import bean.LoginTypeBean;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ui.LoginActivity;
import ui.SelectActivity;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 是否显示状态栏导航栏
     */
    private boolean isShow = false;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    private Unbinder unbinder;
    private ActionBar actionBar;

    public static final int NOSTATE = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 适配华为底部虚拟操作按钮
//        if (NavigationBarUtil.hasNavigationBar(this)) {
//            NavigationBarUtil.initActivity(findViewById(android.R.id.content));
//        }
        Log.d(TAG, "BaseActivity-->onCreate()");
        Bundle bundle = getIntent().getExtras();
        initParms(bundle);
//        setStatusBar();
        setContentView(bindLayout());
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
//        setSystemUIVisible(isShow);
        //为 Activity 指定 windowSoftInputMode
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        unbinder = ButterKnife.bind(this);
        initView(getIntent());
        initData();
        doBusiness(this);
        setListener();
        EventBus.getDefault().register(this);
        App.getinstance().addActivity(this);
    }

    protected void setStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //这里做了两件事情，1.使状态栏透明并使contentView填充到状态栏 2.预留出状态栏的位置，防止界面上的控件离顶部靠的太近。这样就可以实现开头说的第二种情况的沉浸式状态栏了
//        StatusBarUtil.setTransparent(this);

    }

    //结束界面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void backToMenuActivity(Integer i) {
        if (i == NOSTATE) {
            if (!TAG.equals(LoginActivity.TAG_EXIT)) {
                onExit();
            }
        }
    }

    //弹出dialog
    protected void onExit() {
        builder = new AlertDialog.Builder(this).setTitle("提示").setMessage("您的账号已在其它设备登录。如果不是您本人操作，请及时修改密码。").setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getOut();
                dialog.dismiss();
            }
        }).setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }


    //跳转到栈底的activity 然后根据tag退出程序
    protected void getOut() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(LoginActivity.TAG_EXIT, true);
        startActivity(intent);
    }


    private void setSystemUIVisible(boolean show) {
        if (show) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiFlags |= 0x00001000;
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            uiFlags |= 0x00001000;
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();


    protected abstract void initData();

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * [初始化参数]
     *
     * @param parms
     */
    public abstract void initParms(Bundle parms);


    /**
     * [初始化控件]
     *
     * @param intent
     */
    public abstract void initView(Intent intent);

    /**
     * [绑定控件]
     *
     * @param resId
     * @return
     */
    protected <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }


    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    /**
     * [业务操作]
     *
     * @param mContext
     */
    public abstract void doBusiness(Context mContext);


    /**
     * [设置监听]
     */
    public abstract void setListener();

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        if (unbinder != null) {
            unbinder.unbind();//解绑butterkinife
        }
        App.getinstance().killActivity(this);
        //移除粘性事件
        EventBus.getDefault().removeAllStickyEvents();
        //注销注册
        EventBus.getDefault().unregister(this);
    }

    /**
     * [简化Toast]
     *
     * @param msg
     */
    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
