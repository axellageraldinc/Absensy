package ppl.com.absensy.app;

import android.app.Application;

import ppl.com.absensy.repository.RepositoryModule;

public class AbsensyApp extends Application {

    private AbsensyAppComponent absensyAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        absensyAppComponent = DaggerAbsensyAppComponent.builder()
                .absensyAppModule(new AbsensyAppModule(this))
                .repositoryModule(new RepositoryModule())
                .build();
    }

    public AbsensyAppComponent getAbsensyAppComponent() {
        return absensyAppComponent;
    }
}
