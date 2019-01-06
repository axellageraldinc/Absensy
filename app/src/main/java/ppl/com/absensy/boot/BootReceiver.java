package ppl.com.absensy.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
            Job bootServiceJob = firebaseJobDispatcher.newJobBuilder()
                    .setService(BootService.class)
                    .setTag("BootService")
                    .setRecurring(false)
                    .setTrigger(Trigger.NOW)
                    .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                    .build();
            firebaseJobDispatcher.mustSchedule(bootServiceJob);
        }
    }
}
