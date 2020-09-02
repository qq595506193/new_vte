package contract;

import java.util.HashMap;

import base.BasePresenter;
import base.IBaseModel;
import base.IBaseView;
import base.IRequestCallback;
import model.LoginModel;

public interface ILoginContract {

    abstract class LoginPresenter extends BasePresenter<ILoginModel, ILoginView> {
        public abstract void getLogin(HashMap<String, Object> params);

        @Override
        public ILoginModel getModel() {
            return new LoginModel();
        }
    }

    interface ILoginModel extends IBaseModel {
        void getLogin(HashMap<String, Object> params, IRequestCallback iRequestCallback);
    }

    interface ILoginView extends IBaseView {
        void onLoginSuccess(Object result);

        void onLoginFailure(Object error);
    }
}
