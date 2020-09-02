package contract;

import java.util.HashMap;

import base.BasePresenter;
import base.IBaseModel;
import base.IBaseView;
import base.IRequestCallback;
import model.AssessModel;

public interface IAssessContract {

    abstract class AssessPresenter extends BasePresenter<IAssessModel, IAssessView> {

        public abstract void getTestTitle(HashMap<String, Object> params);

        @Override
        public IAssessModel getModel() {
            return new AssessModel();
        }
    }

    interface IAssessModel extends IBaseModel {
        void getTestTitle(HashMap<String, Object> params, IRequestCallback iRequestCallback);
    }

    interface IAssessView extends IBaseView {
        void onTestTitleSuccess(Object result);
    }
}
