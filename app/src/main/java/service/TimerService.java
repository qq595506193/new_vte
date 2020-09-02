package service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.App;
import base.http.ApiService;
import base.http.IRetrofitServer;
import base.http.RetrofitUtils;
import bean.PatientListBean;
import bean.PatientMsgBean;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ui.SelectActivity;
import utils.SpUtil;

public class TimerService extends Service {
    private static PatientMsgBean patientMsgBean;
    private static SelectActivity selectActivity;
    private boolean pushthread = false;

    public TimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TimerService", "onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TimerService", "onStartCommand");
        if (intent.getStringExtra("flags").equals("3")) {
            //判断当系统版本大于20，即超过Android5.0时，我们采用线程循环的方式请求。
            //当小于5.0时的系统则采用定时唤醒服务的方式执行循环
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion > 20) {
                getPushThread();
            } else {
                if (selectActivity != null) {
                    selectActivity.getLoginType();
                    selectActivity.getRefreshPatient();
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //循环请求的线程
    public void getPushThread() {
        pushthread = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pushthread) {
                    try {
                        Thread.sleep(3000);
                        if (selectActivity != null) {
                            selectActivity.getLoginType();
                            selectActivity.getRefreshPatient();
                        }
//                        getHttp(patientMsgBean);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

//    //请求网络获取数据
//    private void getHttp(PatientMsgBean patientMsgBean) {
//        Log.e("changhao", "刷新了一次床位图");
//        HashMap<String, Object> params = new HashMap<>();
//        params.put("tk", patientMsgBean.getTk());
//        params.put("Type", "queryPatient_Basic_InfoBybed");
//        params.put("CURRENT_RISK_LEVEL", patientMsgBean.getCURRENT_RISK_LEVEL());// 危险等级
//        params.put("PATIENT_NAME", patientMsgBean.getPATIENT_NAME());// 患者名称
//        params.put("PATIENT_SEX", patientMsgBean.getPATIENT_SEX());// 患者性别
//        params.put("BED_NUMBER", BED_NUMBER);// 床位号
//        if (DEPARTMENT_ID.size() != 0) {
//            params.put("DEPARTMENT_ID", DEPARTMENT_ID.get(0));// 科室
//            params.put("CARE_UNIT", "");// 单元
//        } else {
//            params.put("DEPARTMENT_ID", "");// 科室
//            params.put("CARE_UNIT", "");// 单元
//        }
//        RetrofitUtils.getInstance().createService(IRetrofitServer.class)
//                .getPatientList(ApiService.CASE_LIST, params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<PatientListBean>() {
//                    @Override
//                    public void accept(PatientListBean patientListBean) throws Exception {
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                });
////        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
////            @Override
////            public void onError(Call call, Exception e, int id) {
////                Log.d("TimerService", "TimerService" + e.toString());
////            }
////
////            @Override
////            public void onResponse(String response, int id) {
////                Log.d("TimerService", "response==" + response);
////            }
////        });
//    }

    @Override
    public void onDestroy() {
        pushthread = false;
        Log.d("TimerService", "onDestroy");
        super.onDestroy();
    }

    //启动服务和定时器
    public static void getConnet(Context mContext, SelectActivity selectActivity) {

        TimerService.selectActivity = selectActivity;
        try {
            Intent intent = new Intent(mContext, TimerService.class);
            intent.putExtra("flags", "3");
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion > 20) {
                //一般的启动服务的方式
                mContext.startService(intent);
            } else {
                //定时唤醒服务的启动方式
                PendingIntent pIntent = PendingIntent.getService(mContext, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) mContext
                        .getSystemService(Context.ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis(), 3000, pIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public static void getConnet(Context mContext, String tk, String CURRENT_RISK_LEVEL,
//                                 String PATIENT_NAME, String PATIENT_SEX, String BED_NUMBER, List<String> DEPARTMENT_ID,
//                                 String CARE_UNIT) {
//
//        patientMsgBean = new PatientMsgBean();
//        patientMsgBean.setTk(tk);
//        patientMsgBean.setCURRENT_RISK_LEVEL(CURRENT_RISK_LEVEL);
//        patientMsgBean.setPATIENT_NAME(PATIENT_NAME);
//        patientMsgBean.setPATIENT_SEX(PATIENT_SEX);
//        patientMsgBean.setBED_NUMBER(BED_NUMBER);
//        if (DEPARTMENT_ID.size() != 0) {
//            patientMsgBean.setDEPARTMENT_ID(DEPARTMENT_ID.get(0));
//            patientMsgBean.setCARE_UNIT("");
//        } else {
//            patientMsgBean.setDEPARTMENT_ID("");
//            patientMsgBean.setCARE_UNIT("");
//        }
//        try {
//            Intent intent = new Intent(mContext, TimerService.class);
//            intent.putExtra("flags", "3");
//            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//            if (currentapiVersion > 20) {
//                //一般的启动服务的方式
//                mContext.startService(intent);
//            } else {
//                //定时唤醒服务的启动方式
//                PendingIntent pIntent = PendingIntent.getService(mContext, 0,
//                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                AlarmManager alarmManager = (AlarmManager) mContext
//                        .getSystemService(Context.ALARM_SERVICE);
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
//                        System.currentTimeMillis(), 3000, pIntent);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    //停止由AlarmManager启动的循环
    public static void stop(Context mContext) {
        Intent intent = new Intent(mContext, TimerService.class);
        PendingIntent pIntent = PendingIntent.getService(mContext, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) mContext
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pIntent);
    }
}