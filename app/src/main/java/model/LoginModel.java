package model;

import java.util.HashMap;

import base.IRequestCallback;
import base.http.ApiService;
import base.http.IRetrofitServer;
import base.http.RetrofitUtils;
import bean.LoginBean;
import contract.ILoginContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginModel implements ILoginContract.ILoginModel {
    /**
     * 登录
     *
     * @param params
     * @param iRequestCallback
     */
    @Override
    public void getLogin(HashMap<String, Object> params, final IRequestCallback iRequestCallback) {
        RetrofitUtils.getInstance().createService(IRetrofitServer.class)
                .getLogin(ApiService.LOGIN, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        if (iRequestCallback != null) {
                            iRequestCallback.onSuccess(loginBean);
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
