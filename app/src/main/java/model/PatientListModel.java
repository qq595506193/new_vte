package model;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

import base.IRequestCallback;
import base.http.ApiService;
import base.http.IRetrofitServer;
import base.http.RetrofitUtils;
import bean.LoginTypeBean;
import bean.PatientListBean;
import bean.RefreshPatientBean;
import contract.IPatientListContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.PATCH;

public class PatientListModel implements IPatientListContract.IPatientListModel {
    /**
     * 监听登录状态
     *
     * @param params
     * @param iRequestCallback
     */
    @Override
    public void getLoginType(HashMap<String, Object> params, final IRequestCallback iRequestCallback) {
        RetrofitUtils.getInstance().createService(IRetrofitServer.class)
                .getLoginType(ApiService.LOGIN_TYPE, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginTypeBean>() {
                    @Override
                    public void accept(LoginTypeBean loginTypeBean) throws Exception {
                        if (iRequestCallback != null) {
                            iRequestCallback.onSuccess(loginTypeBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (iRequestCallback != null) {
                            iRequestCallback.onFailed(throwable.toString());
                        }
                    }
                });
    }

    /**
     * 床位图
     *
     * @param params
     * @param iRequestCallback
     */
    @Override
    public void getPatientList(final HashMap<String, Object> params, final IRequestCallback iRequestCallback) {
        RetrofitUtils.getInstance().createService(IRetrofitServer.class)
                .getPatientList(ApiService.CASE_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PatientListBean>() {
                    @Override
                    public void accept(PatientListBean patientListBean) throws Exception {
                        if (iRequestCallback != null) {
                            iRequestCallback.onSuccess(patientListBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (iRequestCallback != null) {
                            iRequestCallback.onFailed(throwable.toString());
                        }
                    }
                });
    }

    /**
     * 刷新患者状态
     *
     * @param params
     * @param iRequestCallback
     */
    @Override
    public void getRefreshPatient(HashMap<String, Object> params, final IRequestCallback iRequestCallback) {
        RetrofitUtils.getInstance().createService(IRetrofitServer.class)
                .getRefreshPatient(ApiService.QUERY_HM, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RefreshPatientBean>() {
                    @Override
                    public void accept(RefreshPatientBean refreshPatientBean) throws Exception {
                        if (iRequestCallback != null) {
                            iRequestCallback.onSuccess(refreshPatientBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (iRequestCallback != null) {
                            iRequestCallback.onFailed(throwable.toString());
                        }
                    }
                });
    }
}
