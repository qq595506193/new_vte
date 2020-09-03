package contract;

import java.util.HashMap;

import base.BasePresenter;
import base.IBaseModel;
import base.IBaseView;
import base.IRequestCallback;
import model.DocAuditModel;

public interface IDocAuditContract {

    abstract class DocAuditPresenter extends BasePresenter<IDocAuditModel, IDocAuditView> {

        public abstract void getDocAudit(HashMap<String, Object> params);

        @Override
        public IDocAuditModel getModel() {
            return new DocAuditModel();
        }
    }

    interface IDocAuditModel extends IBaseModel {
        void getDocAudit(HashMap<String, Object> params, IRequestCallback iRequestCallback);
    }

    interface IDocAuditView extends IBaseView {
        void onDocAuditSuccess(Object result);
    }
}
