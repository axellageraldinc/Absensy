package ppl.com.absensy.absencedetails;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ppl.com.absensy.model.AbsenceDetail;
import ppl.com.absensy.repository.AbsenceDetailDao;

public class AbsenceDetailsPresenter implements AbsenceDetailsContract.Presenter {

    private static final String CANNOT_LOAD_ABSENCE_DETAILS_ERROR_MESSAGE = "Hmmm kok error ya pas mau loading detail absen\n";

    private AbsenceDetailsContract.View view;
    private AbsenceDetailDao absenceDetailDao;
    private CompositeDisposable compositeDisposable;

    public AbsenceDetailsPresenter(AbsenceDetailsContract.View view, AbsenceDetailDao absenceDetailDao, CompositeDisposable compositeDisposable) {
        this.view = view;
        this.absenceDetailDao = absenceDetailDao;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadAbsenceDetails(String subjectId) {
        compositeDisposable.add(absenceDetailDao.findAllBySubjectId(subjectId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<AbsenceDetail>>() {
                    @Override
                    public void onSuccess(List<AbsenceDetail> absenceDetailList) {
                        view.showAbsenceDetails(absenceDetailList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(CANNOT_LOAD_ABSENCE_DETAILS_ERROR_MESSAGE + e.getMessage());
                    }
                })
        );
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
    }
}
