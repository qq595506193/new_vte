package model;

import java.util.HashMap;

import base.IRequestCallback;
import base.http.ApiService;
import base.http.IRetrofitServer;
import base.http.RetrofitUtils;
import bean.ApproveBean;
import bean.CancellationBean;
import contract.IAuditContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AuditModel implements IAuditContract.IAuditModel {
    /**
     * 审核通过
     *
     * @param params
     * @param iRequestCallback
     */
    @Override
    public void getApprove(HashMap<String, Object> params, final IRequestCallback iRequestCallback) {
        RetrofitUtils.getInstance().createService(IRetrofitServer.class)
                .getShenHeTongGuo(ApiService.APPROVE, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApproveBean>() {
                    @Override
                    public void accept(ApproveBean approveBean) throws Exception {
                        if (iRequestCallback != null) {
                            iRequestCallback.onSuccess(approveBean);
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
     * 此卷作废
     *
     * @param params
     * @param iRequestCallback
     */
    @Override
    public void getCancellation(HashMap<String, Object> params, final IRequestCallback iRequestCallback) {
        RetrofitUtils.getInstance().createService(IRetrofitServer.class)
                .getCiJuanZuoFei(ApiService.CANCELLATION, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CancellationBean>() {
                    @Override
                    public void accept(CancellationBean cancellationBean) throws Exception {
                        if (iRequestCallback != null) {
                            iRequestCallback.onSuccess(cancellationBean);
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
