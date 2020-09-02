package contract;

import java.util.HashMap;

import base.BasePresenter;
import base.IBaseModel;
import base.IBaseView;
import base.IRequestCallback;
import model.PatientListModel;

public interface IPatientListContract {

    abstract class PatientListPresenter extends BasePresenter<IPatientListModel, IPatientListView> {

        public abstract void getLoginType(HashMap<String, Object> params);

        public abstract void getPatientList(HashMap<String, Object> params);

        public abstract void getRefreshPatient(HashMap<String, Object> params);

        @Override
        public IPatientListModel getModel() {
            return new PatientListModel();
        }
    }

    interface IPatientListModel extends IBaseModel {

        void getLoginType(HashMap<String, Object> params, IRequestCallback iRequestCallback);

        void getPatientList(HashMap<String, Object> params, IRequestCallback iRequestCallback);

        void getRefreshPatient(HashMap<String, Object> params, IRequestCallback iRequestCallback);
    }

    interface IPatientListView extends IBaseView {

        void onLoginTypeSuccess(Object reslut);

        void onPatientListSuccess(Object reslut);

        void onRefreshPatientSuccess(Object reslut);
    }
}
