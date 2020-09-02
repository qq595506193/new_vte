package model;

import java.util.HashMap;

import base.IRequestCallback;
import base.http.ApiService;
import base.http.IRetrofitServer;
import base.http.RetrofitUtils;
import bean.RiskAssessBean;
import contract.IAssessContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AssessModel implements IAssessContract.IAssessModel {
    /**
     * 查询问卷
     *
     * @param params
     * @param iRequestCallback
     */
    @Override
    public void getTestTitle(HashMap<String, Object> params, final IRequestCallback iRequestCallback) {
        RetrofitUtils.getInstance().createService(IRetrofitServer.class)
                .getRisk(ApiService.RISK_TABLE_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RiskAssessBean>() {
                    @Override
                    public void accept(RiskAssessBean riskAssessBean) throws Exception {
                        if (iRequestCallback != null) {
                            iRequestCallback.onSuccess(riskAssessBean);
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
