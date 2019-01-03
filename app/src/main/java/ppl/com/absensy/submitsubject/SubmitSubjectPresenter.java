package ppl.com.absensy.submitsubject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import ppl.com.absensy.model.Subject;
import ppl.com.absensy.repository.SubjectDao;

public class SubmitSubjectPresenter implements SubmitSubjectContract.Presenter {

    private CompositeDisposable compositeDisposable;
    private SubmitSubjectContract.View view;
    private SubjectDao subjectDao;

    public SubmitSubjectPresenter(CompositeDisposable compositeDisposable,
                                  SubmitSubjectContract.View view,
                                  SubjectDao subjectDao) {
        this.compositeDisposable = compositeDisposable;
        this.view = view;
        this.subjectDao = subjectDao;
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
                        view.showToast("Berhasil menambahkan " + subject.getName());
                        view.moveToPreviousPage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast("Waduh, error saat mencoba menambahkan " + subject.getName() + "\n" + e.getMessage());
                    }
                });
    }

    @Override
    public void updateSubject(final Subject subject) {
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
                        view.showToast("Berhasil mengubah informasi mata kuliah " + subject.getName());
                        view.moveToPreviousPage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast("Waduh, error saat mencoba mengedit " + subject.getName() + "\n" + e.getMessage());
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
                        view.moveToPreviousPage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast("Hmmm kok gagal hapus ya :/\nError : " + e.getMessage());
                    }
                });
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
    }
}
