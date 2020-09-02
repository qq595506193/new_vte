package presenter;

import java.text.ParseException;
import java.util.HashMap;

import base.IRequestCallback;
import contract.ILoginContract;

public class LoginPresenter extends ILoginContract.LoginPresenter {
    /**
     * 登录
     *
     * @param params
     */
    @Override
    public void getLogin(HashMap<String, Object> params) {
        if (view != null) {
            view.showLoading();
            model.getLogin(params, new IRequestCallback() {
                @Override
                public void onSuccess(Object result) throws ParseException {
                    view.onLoginSuccess(result);
                    view.hideLoading();
                }

                @Override
                public void onFailed(Object error) {
                    view.onLoginFailure(error);
                    view.hideLoading();
                }
            });
        }
    }
}
