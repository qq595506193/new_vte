package presenter;

import java.text.ParseException;
import java.util.HashMap;

import base.IRequestCallback;
import contract.IAuditContract;

public class AuditPresenter extends IAuditContract.AuditPresenter {
    /**
     * 审核通过
     *
     * @param params
     */
    @Override
    public void getApprove(HashMap<String, Object> params) {
        if (view != null) {
            view.showLoading();
            if (model != null) {
                model.getApprove(params, new IRequestCallback() {
                    @Override
                    public void onSuccess(Object result) throws ParseException {
                        if (view != null) {
                            view.onApproveSuccess(result);
                            view.hideLoading();
                        }
                    }

                    @Override
                    public void onFailed(Object error) {
                        if (view != null) {
                            view.failure(error);
                            view.hideLoading();
                        }
                    }
                });
            }
        }
    }

    /**
     * 此卷作废
     *
     * @param params
     */
    @Override
    public void getCancellation(HashMap<String, Object> params) {
        if (view != null) {
            view.showLoading();
            if (model != null) {
                model.getCancellation(params, new IRequestCallback() {
                    @Override
                    public void onSuccess(Object result) throws ParseException {
                        if (view != null) {
                            view.onCancellationSuccess(result);
                            view.hideLoading();
                        }
                    }

                    @Override
                    public void onFailed(Object error) {
                        if (view != null) {
                            view.failure(error);
                            view.hideLoading();
                        }
                    }
                });
            }
        }
    }
}
