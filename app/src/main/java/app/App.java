package app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import ui.LoginActivity;
import ui.SelectActivity;


public class App extends Application {
    private static Context context;
    private List<Activity> activityList = new ArrayList<Activity>();
    private static App instance;
    private BaseActivity mActivity;

    public BaseActivity getmActivity() {
        return mActivity;
    }

    // 单列模式获取唯一MyApplication的实例
    public static App getinstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    @Override
    public void onCreate() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork() // 这里可以替换为detectAll() 就包括了磁盘读写和网络I/O
                .penaltyLog() //打印logcat，当然也可以定位到dropbox，通过文件保存相应的log
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects() //探测SQLite数据库操作
                .penaltyLog() //打印logcat
                .penaltyDeath()
                .build());
        super.onCreate();

        context = getApplicationContext();

    }

    // 添加Activity到容器中
    public void addActivity(Activity acivity) {
        activityList.add(acivity);
    }

    // 删除Activity
    public void killActivity(Activity acivity) {
        activityList.remove(acivity);
    }

    // 遍历所有的Activity并fanish
    public void closeAllActiivty() {
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = activityList.get(i);
            activity.finish();
        }
        System.exit(0);
    }

    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        return context;
    }

}
