package presenter;

import java.text.ParseException;
import java.util.HashMap;

import base.IRequestCallback;
import contract.IHistoryContract;

public class HistoryPresenter extends IHistoryContract.HistoryPresenter {


    /**
     * 查询历史
     *
     * @param params
     */
    @Override
    public void getHistory(HashMap<String, Object> params) {
        if (view != null) {
            view.showLoading();
            model.getHistory(params, new IRequestCallback() {
                @Override
                public void onSuccess(Object result) throws ParseException {
                    view.onHistorySuccess(result);
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
