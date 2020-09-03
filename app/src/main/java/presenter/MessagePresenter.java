package presenter;

import java.text.ParseException;
import java.util.HashMap;

import base.IRequestCallback;
import contract.IMessageContract;

public class MessagePresenter extends IMessageContract.MessagePresenter {
    /**
     * 查询消息列表
     *
     * @param params
     */
    @Override
    public void getMsgList(HashMap<String, Object> params) {
        if (view != null) {
            view.showLoading();
            model.getMsgList(params, new IRequestCallback() {
                @Override
                public void onSuccess(Object result) throws ParseException {
                    view.onGetMsgSuccess(result);
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
