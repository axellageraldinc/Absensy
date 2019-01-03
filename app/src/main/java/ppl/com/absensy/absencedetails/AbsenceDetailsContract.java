package ppl.com.absensy.absencedetails;

import java.util.List;

import ppl.com.absensy.base.BasePresenter;
import ppl.com.absensy.base.BaseView;
import ppl.com.absensy.model.AbsenceDetail;

public interface AbsenceDetailsContract {
    interface View extends BaseView {
        void showAbsenceDetails(List<AbsenceDetail> absenceDetailList);
    }

    interface Presenter extends BasePresenter {
        void loadAbsenceDetails(String subjectId);
    }
}
