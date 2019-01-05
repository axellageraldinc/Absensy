package ppl.com.absensy.reminder;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import ppl.com.absensy.di.ApplicationScope;

@Module
public class ClassReminderModule {
    @Provides
    @ApplicationScope
    public ClassReminder providesClassReminder(Application application) {
        return new ClassReminderImpl(application);
    }
}
