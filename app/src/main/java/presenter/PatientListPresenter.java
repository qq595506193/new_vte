package presenter;

import java.text.ParseException;
import java.util.HashMap;

import base.IRequestCallback;
import contract.IPatientListContract;

public class PatientListPresenter extends IPatientListContract.PatientListPresenter {
    /**
     * 登录状态监听
     *
     * @param params
     */
    @Override
    public void getLoginType(HashMap<String, Object> params) {
        if (view != null) {
            model.getLoginType(params, new IRequestCallback() {
                @Override
                public void onSuccess(Object result) throws ParseException {
                    view.onLoginTypeSuccess(result);
                }

                @Override
                public void onFailed(Object error) {
                    view.failure(error);
                }
            });
        }
    }

    /**
     * 床位图
     *
     * @param params
     */
    @Override
    public void getPatientList(HashMap<String, Object> params) {
        if (view != null) {
            model.getPatientList(params, new IRequestCallback() {
                @Override
                public void onSuccess(Object result) throws ParseException {
                    view.onPatientListSuccess(result);
                }

                @Override
                public void onFailed(Object error) {
                    view.failure(error);
                }
            });
        }
    }

    /**
     * 刷新患者状态
     *
     * @param params
     */
    @Override
    public void getRefreshPatient(HashMap<String, Object> params) {
        if (view != null) {
            model.getRefreshPatient(params, new IRequestCallback() {
                @Override
                public void onSuccess(Object result) throws ParseException {
                    view.onRefreshPatientSuccess(result);
                }

                @Override
                public void onFailed(Object error) {
                    view.failure(error);
                }
            });
        }
    }
}
