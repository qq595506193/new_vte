package contract;

import java.util.HashMap;

import base.BasePresenter;
import base.IBaseModel;
import base.IBaseView;
import base.IRequestCallback;
import model.SelectTableModel;

public interface ISelectTableContract {

    abstract class SelectTablePresenter extends BasePresenter<ISelectTableModel, ISelectTableView> {

        public abstract void getRiskTableList(HashMap<String, Object> parmas);

        @Override
        public ISelectTableModel getModel() {
            return new SelectTableModel();
        }
    }

    interface ISelectTableModel extends IBaseModel {
        void getRiskTableList(HashMap<String, Object> parmas, IRequestCallback iRequestCallback);
    }

    interface ISelectTableView extends IBaseView {
        void onRiskTableListSuccess(Object result);
    }
}
