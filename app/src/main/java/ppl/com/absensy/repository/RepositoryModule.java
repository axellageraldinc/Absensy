package ppl.com.absensy.repository;

import android.app.Application;
import android.arch.persistence.room.Room;

import dagger.Module;
import dagger.Provides;
import ppl.com.absensy.di.ApplicationScope;

@Module
public class RepositoryModule {

    private static final String DATABASE_NAME = "absensy";

    @Provides
    @ApplicationScope
    public AppDatabase providesAppDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, DATABASE_NAME).build();
    }

    @Provides
    @ApplicationScope
    public SettingSharedPreferences providesSettingSharedPreferences(Application application) {
        return new SettingSharedPreferencesImpl(application);
    }
}
