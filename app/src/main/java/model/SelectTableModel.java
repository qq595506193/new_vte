package model;

import java.util.HashMap;

import base.IRequestCallback;
import base.http.ApiService;
import base.http.IRetrofitServer;
import base.http.RetrofitUtils;
import bean.SelectTablesBean;
import contract.ISelectTableContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SelectTableModel implements ISelectTableContract.ISelectTableModel {
    /**
     * 查询可评估的表
     *
     * @param parmas
     * @param iRequestCallback
     */
    @Override
    public void getRiskTableList(HashMap<String, Object> parmas, final IRequestCallback iRequestCallback) {
        RetrofitUtils.getInstance().createService(IRetrofitServer.class)
                .getRiskTableList(ApiService.NOW_SELECT_TABLES, parmas)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SelectTablesBean>() {
                    @Override
                    public void accept(SelectTablesBean selectTablesBean) throws Exception {
                        if (iRequestCallback != null) {
                            iRequestCallback.onSuccess(selectTablesBean);
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
