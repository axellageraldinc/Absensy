package ppl.com.absensy.submitsubject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import ppl.com.absensy.model.Subject;
import ppl.com.absensy.reminder.ClassReminder;
import ppl.com.absensy.repository.SubjectDao;

public class SubmitSubjectPresenter implements SubmitSubjectContract.Presenter {

    private static final String SAVE_SUBJECT_SUCCESS_MESSAGE = "Berhasil menambahkan ";
    private static final String EDIT_SUBJECT_SUCCESS_MESSAGE = "Berhasil mengubah informasi mata kuliah ";
    private static final String DELETE_SUBJECT_SUCCESS_MESSAGE = "Berhasil menghapus ";

    private static final String CANNOT_SAVE_SUBJECT_ERROR_MESSAGE = "Waduh, error saat mencoba menambahkan ";
    private static final String CANNOT_EDIT_SUBJECT_ERROR_MESSAGE = "Waduh, error saat mencoba mengedit ";
    private static final String CANNOT_DELETE_SUBJECT_ERROR_MESSAGE = "Hmmm kok gagal hapus ya :/\nError : ";

    private CompositeDisposable compositeDisposable;
    private SubmitSubjectContract.View view;
    private SubjectDao subjectDao;
    private ClassReminder classReminder;

    public SubmitSubjectPresenter(CompositeDisposable compositeDisposable,
                                  SubmitSubjectContract.View view,
                                  SubjectDao subjectDao,
                                  ClassReminder classReminder) {
        this.compositeDisposable = compositeDisposable;
        this.view = view;
        this.subjectDao = subjectDao;
        this.classReminder = classReminder;
    }

    @Override
    public void saveSubject(final Subject subject) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                subjectDao.save(subject);
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
                        view.showToast(SAVE_SUBJECT_SUCCESS_MESSAGE + subject.getName());
                        classReminder.setReminder(subject);
                        view.moveToPreviousPage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(CANNOT_SAVE_SUBJECT_ERROR_MESSAGE + subject.getName() + "\n" + e.getMessage());
                    }
                });
    }

    @Override
    public void updateSubject(final Subject originalSubject, final Subject subject) {
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
                        view.showToast(EDIT_SUBJECT_SUCCESS_MESSAGE + subject.getName());
                        classReminder.cancelExistingReminder(originalSubject);
                        classReminder.setReminder(subject);
                        view.moveToPreviousPage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(CANNOT_EDIT_SUBJECT_ERROR_MESSAGE + subject.getName() + "\n" + e.getMessage());
                    }
                });
    }

    @Override
    public void deleteSubject(final Subject subject) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                subjectDao.delete(subject);
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
                        classReminder.cancelExistingReminder(subject);
                        view.showToast(DELETE_SUBJECT_SUCCESS_MESSAGE + subject.getName());
                        view.moveToPreviousPage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(CANNOT_DELETE_SUBJECT_ERROR_MESSAGE + e.getMessage());
                    }
                });
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
    }
}
