package ppl.com.absensy.reminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ppl.com.absensy.R;
import ppl.com.absensy.repository.SettingSharedPreferences;
import ppl.com.absensy.repository.SettingSharedPreferencesImpl;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String SUBJECT_ID_KEY = "subjectId";
    public static final String SUBJECT_NAME_KEY = "subjectName";
    public static final String SUBJECT_ABSENCE_AMOUNT_KEY = "subjectAbsenceAmount";

    private SettingSharedPreferences settingSharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        settingSharedPreferences = new SettingSharedPreferencesImpl(context);

        String subjectName = intent.getStringExtra(SUBJECT_NAME_KEY);
        int subjectAbsenceAmount = intent.getIntExtra(SUBJECT_ABSENCE_AMOUNT_KEY, 0);

        if (settingSharedPreferences.findAll().isSubjectReminder())
            notifyUser(context, subjectName, subjectAbsenceAmount);
    }

    private void notifyUser(Context context, String subjectName, int subjectAbsenceAmount) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, context.getResources().getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification))
                .setContentTitle("Jangan lupa kuliah " + subjectName + " :)")
                .setContentText(getNotificationContent(subjectAbsenceAmount))
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(context.getResources().getString(R.string.app_name), context.getResources().getString(R.string.notification_channel_name_class_reminder), importance);
            notificationBuilder.setChannelId(context.getResources().getString(R.string.app_name));
            notificationManager.createNotificationChannel(notificationChannel);
        }
        int notificationId = Integer.parseInt(new SimpleDateFormat("ddyyHHssSS", Locale.US).format(new Date()));
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private String getNotificationContent(int subjectAbsenceAmount) {
        double classAbsencePercentage = subjectAbsenceAmount / settingSharedPreferences.findAll().getMaxAbsenceAmount() * 100;
        if (classAbsencePercentage >= 0 && classAbsencePercentage < 25)
            return "Aku gak nyuruh skip kuliah lho, FYI aja baru kosong " + String.valueOf(subjectAbsenceAmount);
        if (classAbsencePercentage >= 25 && classAbsencePercentage < 50)
            return "Kosong " + String.valueOf(subjectAbsenceAmount) + ", demi kemajuan bangsa, masuk kelas aja :)";
        if (classAbsencePercentage >= 50 && classAbsencePercentage < 75)
            return "Mendingan kamu masuk aja deh, udah kosong " + String.valueOf(subjectAbsenceAmount);
        if (classAbsencePercentage >= 75)
            return "Sangat disarankan masuk kalo ini, udah kosong " + String.valueOf(subjectAbsenceAmount);
        return "";
    }
}
