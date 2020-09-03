package contract;

import java.util.HashMap;

import base.BasePresenter;
import base.IBaseModel;
import base.IBaseView;
import base.IRequestCallback;
import model.MessageModel;

public interface IMessageContract {

    abstract class MessagePresenter extends BasePresenter<IMessageModel, IMessageView> {

        public abstract void getMsgList(HashMap<String, Object> params);

        @Override
        public IMessageModel getModel() {
            return new MessageModel();
        }
    }

    interface IMessageModel extends IBaseModel {
        void getMsgList(HashMap<String, Object> params, IRequestCallback iRequestCallback);
    }

    interface IMessageView extends IBaseView {
        void onGetMsgSuccess(Object result);
    }
}
