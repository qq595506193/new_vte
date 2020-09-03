package model;

import java.util.HashMap;

import base.IRequestCallback;
import base.http.ApiService;
import base.http.IRetrofitServer;
import base.http.RetrofitUtils;
import bean.DocAuditBean;
import contract.IDocAuditContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DocAuditModel implements IDocAuditContract.IDocAuditModel {
    /**
     * 查询医生审核列表
     *
     * @param params
     * @param iRequestCallback
     */
    @Override
    public void getDocAudit(HashMap<String, Object> params, final IRequestCallback iRequestCallback) {
        RetrofitUtils.getInstance().createService(IRetrofitServer.class)
                .getDocAudit(ApiService.DOCTOR_AUDIT, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DocAuditBean>() {
                    @Override
                    public void accept(DocAuditBean docAuditBean) throws Exception {
                        if (iRequestCallback != null) {
                            iRequestCallback.onSuccess(docAuditBean);
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
