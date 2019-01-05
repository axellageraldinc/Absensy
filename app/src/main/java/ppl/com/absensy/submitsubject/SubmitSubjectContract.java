package ppl.com.absensy.submitsubject;

import ppl.com.absensy.base.BasePresenter;
import ppl.com.absensy.base.BaseView;
import ppl.com.absensy.model.Subject;

public interface SubmitSubjectContract {
    interface View extends BaseView {
        void moveToPreviousPage();
    }

    interface Presenter extends BasePresenter {
        void saveSubject(Subject subject);

        void updateSubject(Subject originalSubject, Subject subject);

        void deleteSubject(Subject subject);
    }
}
