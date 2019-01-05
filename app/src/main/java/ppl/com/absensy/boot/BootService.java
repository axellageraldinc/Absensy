package ppl.com.absensy.boot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ppl.com.absensy.app.AbsensyApp;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.model.Subject;
import ppl.com.absensy.reminder.ClassReminder;
import ppl.com.absensy.repository.SubjectDao;

public class BootService extends Service {

    @Inject
    SubjectDao subjectDao;
    @Inject
    ClassReminder classReminder;

    private CompositeDisposable compositeDisposable;

    @Override
    public void onCreate() {
        super.onCreate();

        AbsensyAppComponent absensyAppComponent = ((AbsensyApp) getApplication()).getAbsensyAppComponent();
        DaggerBootServiceComponent.builder()
                .absensyAppComponent(absensyAppComponent)
                .bootServiceModule(new BootServiceModule())
                .build()
                .inject(this);

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        compositeDisposable.add(subjectDao.findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Subject>>() {
                    @Override
                    public void onSuccess(List<Subject> subjects) {
                        for (Subject subject : subjects) {
                            classReminder.setReminder(subject);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(BootService.this, "Hmm ada yang salah dengan BootService\nError : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        );
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
