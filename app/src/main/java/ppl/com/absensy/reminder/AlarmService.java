package ppl.com.absensy.reminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

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

    private static final int NOTIFICATION_ID = 14;
    private static final String NOTIFICATION_TITLE = "Jangan lupa kuliah ";

    private static final String CANNOT_START_ALARM_SERVICE_ERROR_MESSAGE = "Error onStartJob AlarmService : ";

    @Inject
    SharedPreferencesManager sharedPreferencesManager;
    @Inject
    SubjectDao subjectDao;

    private Uri defaultNotificationSound;
    private CompositeDisposable compositeDisposable;

    @Override
    public void onCreate() {
        super.onCreate();
        AbsensyAppComponent absensyAppComponent = ((AbsensyApp) getApplication()).getAbsensyAppComponent();
        DaggerAlarmServiceComponent.builder()
                .absensyAppComponent(absensyAppComponent)
                .build()
                .inject(this);

        defaultNotificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        compositeDisposable = new CompositeDisposable();
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
                            notifyUser(subject.getId(), NOTIFICATION_TITLE + subject.getName(), "Saat ini kamu kosong " + subject.getAbsenceAmount());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, CANNOT_START_ALARM_SERVICE_ERROR_MESSAGE + e.getMessage());
                    }
                })
        );
        return false;
    }

    private void notifyUser(String subjectId, String title, String content) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, this.getResources().getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_notification_mark)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_notification_mark))
                .setContentTitle(title)
                .setContentText(content)
                .setSound(defaultNotificationSound)
                .setVibrate(new long[] { 500, 1000, 1000, 500 }) // I don't actually know how to interpret this vibration array :/
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(this.getResources().getString(R.string.app_name), this.getResources().getString(R.string.notification_channel_name_class_reminder), importance);
            notificationBuilder.setChannelId(this.getResources().getString(R.string.app_name));
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(subjectId, NOTIFICATION_ID, notificationBuilder.build());
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        compositeDisposable.dispose();
        return false;
    }
}
