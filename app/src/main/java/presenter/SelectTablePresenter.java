package presenter;

import java.text.ParseException;
import java.util.HashMap;

import base.IRequestCallback;
import contract.ISelectTableContract;

public class SelectTablePresenter extends ISelectTableContract.SelectTablePresenter {
    @Override
    public void getRiskTableList(HashMap<String, Object> parmas) {
        if (view != null) {
            view.showLoading();
            model.getRiskTableList(parmas, new IRequestCallback() {
                @Override
                public void onSuccess(Object result) throws ParseException {
                    view.onRiskTableListSuccess(result);
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
