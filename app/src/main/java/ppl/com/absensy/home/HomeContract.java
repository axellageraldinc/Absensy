package ppl.com.absensy.home;

import java.util.List;

import ppl.com.absensy.base.BasePresenter;
import ppl.com.absensy.base.BaseView;
import ppl.com.absensy.model.Subject;

public interface HomeContract {
    interface View extends BaseView {
        void showSubjectList(List<Subject> subjectList);

        void dismissDialogAbsence();
    }

    interface Presenter extends BasePresenter {
        void getAllSubjects();

        void absenceSubject(Subject subject);

        void deleteSubject(Subject subject);
    }
}
