package presenter;

import java.text.ParseException;
import java.util.HashMap;

import base.IRequestCallback;
import contract.IDocAuditContract;

public class DocAuditPresenter extends IDocAuditContract.DocAuditPresenter {
    @Override
    public void getDocAudit(HashMap<String, Object> params) {
        if (view != null) {
            view.showLoading();
            model.getDocAudit(params, new IRequestCallback() {
                @Override
                public void onSuccess(Object result) throws ParseException {
                    view.onDocAuditSuccess(result);
                    view.hideLoading();
                }

                @Override
                public void onFailed(Object error) {
                    view.failure(error);
                    view.hideLoading();
                }
            });
        }
    }
}
