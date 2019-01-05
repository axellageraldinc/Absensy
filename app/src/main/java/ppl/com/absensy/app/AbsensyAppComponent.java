package ppl.com.absensy.app;

import dagger.Component;
import ppl.com.absensy.di.ApplicationScope;
import ppl.com.absensy.reminder.ClassReminder;
import ppl.com.absensy.reminder.ClassReminderModule;
import ppl.com.absensy.repository.AppDatabase;
import ppl.com.absensy.repository.RepositoryModule;
import ppl.com.absensy.repository.SettingSharedPreferences;

@ApplicationScope
@Component(modules = {AbsensyAppModule.class, RepositoryModule.class, ClassReminderModule.class})
public interface AbsensyAppComponent {
    AppDatabase appDatabase();

    SettingSharedPreferences settingSharedPreferences();

    ClassReminder classReminder();
}
