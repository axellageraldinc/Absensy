package ppl.com.absensy.reminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ppl.com.absensy.R;
import ppl.com.absensy.app.AbsensyApp;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.model.Subject;
import ppl.com.absensy.repository.SharedPreferencesManager;
import ppl.com.absensy.repository.SubjectDao;

public class AlarmService extends JobService {

    private static final String TAG = AlarmService.class.getName();

    @Inject
    SharedPreferencesManager sharedPreferencesManager;
    @Inject
    SubjectDao subjectDao;

    private CompositeDisposable compositeDisposable;

    @Override
    public void onCreate() {
        super.onCreate();
        AbsensyAppComponent absensyAppComponent = ((AbsensyApp) getApplication()).getAbsensyAppComponent();
        DaggerAlarmServiceComponent.builder()
                .absensyAppComponent(absensyAppComponent)
                .build()
                .inject(this);

        compositeDisposable = new CompositeDisposable();
    }

    private String getNotificationContent(int subjectAbsenceAmount) {
        double classAbsencePercentage = (double) subjectAbsenceAmount / sharedPreferencesManager.findAllSettings().getMaxAbsenceAmount() * 100;
        if (classAbsencePercentage >= 0 && classAbsencePercentage < 25)
            return "Aku gak nyuruh skip kuliah lho, FYI aja baru kosong " + subjectAbsenceAmount;
        if (classAbsencePercentage >= 25 && classAbsencePercentage < 50)
            return "Kosong " + subjectAbsenceAmount + ", demi kemajuan bangsa, masuk kelas aja :)";
        if (classAbsencePercentage >= 50 && classAbsencePercentage < 75)
            return "Mendingan kamu masuk aja deh, udah kosong " + subjectAbsenceAmount;
        if (classAbsencePercentage >= 75)
            return "Sangat disarankan masuk kalo ini, udah kosong " + subjectAbsenceAmount;
        return "";
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        String subjectId = job.getExtras().getString(AlarmReceiver.SUBJECT_ID_KEY);
        compositeDisposable.add(subjectDao.findById(subjectId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Subject>() {
                    @Override
                    public void onSuccess(Subject subject) {
                        if (sharedPreferencesManager.findAllSettings().isSubjectReminder())
                            notifyUser("Jangan lupa kuliah " + subject.getName(), getNotificationContent(subject.getAbsenceAmount()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error onStartJob AlarmService : " + e.getMessage());
                    }
                })
        );
        return false;
    }

    private void notifyUser(String title, String content) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, this.getResources().getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_notification))
                .setContentTitle(title)
                .setContentText(content)
                .setVibrate(new long[] { 500, 1000, 1000, 500 }) // I don't actually know how to interpret this vibration array :/
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(this.getResources().getString(R.string.app_name), this.getResources().getString(R.string.notification_channel_name_class_reminder), importance);
            notificationBuilder.setChannelId(this.getResources().getString(R.string.app_name));
            notificationManager.createNotificationChannel(notificationChannel);
        }
        int notificationId = Integer.parseInt(new SimpleDateFormat("ddyyHHssSS", Locale.US).format(new Date()));
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        compositeDisposable.dispose();
        return false;
    }
}
