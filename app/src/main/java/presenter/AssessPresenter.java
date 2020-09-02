package presenter;

import java.text.ParseException;
import java.util.HashMap;

import base.IRequestCallback;
import contract.IAssessContract;

public class AssessPresenter extends IAssessContract.AssessPresenter {

    /**
     * 查询问卷
     *
     * @param params
     */
    @Override
    public void getTestTitle(HashMap<String, Object> params) {
        if (view != null) {
            view.showLoading();
            model.getTestTitle(params, new IRequestCallback() {
                @Override
                public void onSuccess(Object result) throws ParseException {
                    view.onTestTitleSuccess(result);
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
