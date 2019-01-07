package ppl.com.absensy.home;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ppl.com.absensy.model.AbsenceDetail;
import ppl.com.absensy.model.Subject;
import ppl.com.absensy.repository.AbsenceDetailDao;
import ppl.com.absensy.repository.SharedPreferencesManager;
import ppl.com.absensy.repository.SubjectDao;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;
    private SubjectDao subjectDao;
    private AbsenceDetailDao absenceDetailDao;
    private SharedPreferencesManager sharedPreferencesManager;
    private CompositeDisposable compositeDisposable;

    public HomePresenter(HomeContract.View view,
                         SubjectDao subjectDao,
                         AbsenceDetailDao absenceDetailDao,
                         SharedPreferencesManager sharedPreferencesManager,
                         CompositeDisposable compositeDisposable) {
        this.view = view;
        this.subjectDao = subjectDao;
        this.absenceDetailDao = absenceDetailDao;
        this.sharedPreferencesManager = sharedPreferencesManager;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void getAllSubjects() {
        compositeDisposable.add(subjectDao.findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Subject>>() {
                    @Override
                    public void onSuccess(List<Subject> subjects) {
                        view.showSubjectList(subjects);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast("Hmm ada error pas loading daftar mata kuliah\n" + e.getMessage());
                    }
                })
        );
    }

    @Override
    public void absenceSubject(final Subject subject) {
        int maxAbsence = sharedPreferencesManager.findAllSettings().getMaxAbsenceAmount();
        if (subject.getAbsenceAmount() + 1 > maxAbsence)
            view.showToast("Wah kamu harus datang kuliah ini, jatah absenmu sudah habis");
        else {
            subject.setAbsenceAmount(subject.getAbsenceAmount() + 1);
            Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    subjectDao.update(subject);
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onComplete() {
                            view.showToast("Oke, jatah absenmu berkurang 1 untuk makul ini : " + subject.getName());
                            getAllSubjects();
                            view.dismissDialogAbsence();
                            saveAbsenceDetails(subject);
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showToast("Hmm sepertinya sudah takdirmu untuk masuk kuliah ini :v\nError : " + e.getMessage());
                        }
                    });
        }
    }

    private void saveAbsenceDetails(final Subject subject) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                absenceDetailDao.save(AbsenceDetail.builder()
                        .id(UUID.randomUUID().toString())
                        .subjectId(subject.getId())
                        .absenceDate(new Date()) // current date
                        .build());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        // do nothing
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast("Hmmm, something went wrong when saving absence details\n" + e.getMessage());
                    }
                });
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
    }
}
