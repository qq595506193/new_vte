package contract;

import base.BasePresenter;
import base.IBaseModel;
import base.IBaseView;
import model.RiskFragmentModel;

public interface IRiskFragmentContract {

    abstract class RiskFragmentPresenter extends BasePresenter<IRiskFragmentModel, IRiskFragmentView> {

        @Override
        public IRiskFragmentModel getModel() {
            return new RiskFragmentModel();
        }
    }

    interface IRiskFragmentModel extends IBaseModel {

    }

    interface IRiskFragmentView extends IBaseView {

    }
}
