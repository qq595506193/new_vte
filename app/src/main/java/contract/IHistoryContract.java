package contract;

import java.util.HashMap;

import base.BasePresenter;
import base.IBaseModel;
import base.IBaseView;
import base.IRequestCallback;
import model.HistoryModel;

public interface IHistoryContract {
    abstract class HistoryPresenter extends BasePresenter<IHistoryModel, IHistoryView> {

        public abstract void getHistory(HashMap<String, Object> params);

        @Override
        public IHistoryModel getModel() {
            return new HistoryModel();
        }
    }

    interface IHistoryModel extends IBaseModel {
        void getHistory(HashMap<String, Object> params, IRequestCallback iRequestCallback);
    }

    interface IHistoryView extends IBaseView {
        void onHistorySuccess(Object result);
    }
}
