package ppl.com.absensy.app;

import dagger.Component;
import ppl.com.absensy.di.ApplicationScope;
import ppl.com.absensy.repository.AppDatabase;
import ppl.com.absensy.repository.RepositoryModule;
import ppl.com.absensy.repository.SettingSharedPreferences;

@ApplicationScope
@Component(modules = {AbsensyAppModule.class, RepositoryModule.class})
public interface AbsensyAppComponent {
    AppDatabase appDatabase();

    SettingSharedPreferences settingSharedPreferences();
}
