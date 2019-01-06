package ppl.com.absensy.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String SUBJECT_ID_KEY = "subjectId";

    @Override
    public void onReceive(Context context, Intent intent) {
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        String subjectId = intent.getStringExtra(SUBJECT_ID_KEY);
        Bundle bundle = new Bundle();
        bundle.putString(SUBJECT_ID_KEY, subjectId);
        Job alarmJob = firebaseJobDispatcher.newJobBuilder()
                .setService(AlarmService.class)
                .setTag(subjectId)
                .setRecurring(false)
                .setTrigger(Trigger.NOW)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setExtras(bundle)
                .build();
        firebaseJobDispatcher.mustSchedule(alarmJob);
    }
}
