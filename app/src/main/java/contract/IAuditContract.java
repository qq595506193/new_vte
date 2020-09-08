package contract;

import java.util.HashMap;

import base.BasePresenter;
import base.IBaseModel;
import base.IBaseView;
import base.IRequestCallback;
import model.AuditModel;

public interface IAuditContract {

    abstract class AuditPresenter extends BasePresenter<IAuditModel, IAuditView> {

        public abstract void getApprove(HashMap<String, Object> params);

        public abstract void getCancellation(HashMap<String, Object> params);

        @Override
        public IAuditModel getModel() {
            return new AuditModel();
        }
    }

    interface IAuditModel extends IBaseModel {
        void getApprove(HashMap<String, Object> params, IRequestCallback iRequestCallback);

        void getCancellation(HashMap<String, Object> params, IRequestCallback iRequestCallback);
    }

    interface IAuditView extends IBaseView {
        void onApproveSuccess(Object result);

        void onCancellationSuccess(Object result);
    }
}
