package model;

import java.util.HashMap;

import base.IRequestCallback;
import base.http.ApiService;
import base.http.IRetrofitServer;
import base.http.RetrofitUtils;
import bean.HistoryAssessBean;
import bean.RefreshPatientBean;
import contract.IHistoryContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HistoryModel implements IHistoryContract.IHistoryModel {

    /**
     * 查询历史
     *
     * @param params
     * @param iRequestCallback
     */
    @Override
    public void getHistory(HashMap<String, Object> params, final IRequestCallback iRequestCallback) {
        RetrofitUtils.getInstance().createService(IRetrofitServer.class)
                .getHistory(ApiService.HISTORY_ASSESS, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HistoryAssessBean>() {
                    @Override
                    public void accept(HistoryAssessBean historyAssessBean) throws Exception {
                        if (iRequestCallback != null) {
                            iRequestCallback.onSuccess(historyAssessBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (iRequestCallback != null) {
                            iRequestCallback.onSuccess(throwable.toString());
                        }
                    }
                });

    }
}
