package ppl.com.absensy.app;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import ppl.com.absensy.di.ApplicationScope;

@Module
public class AbsensyAppModule {
    private Application application;

    public AbsensyAppModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    public Application providesApplication() {
        return application;
    }
}
