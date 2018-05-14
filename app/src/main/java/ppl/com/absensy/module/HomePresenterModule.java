package ppl.com.absensy.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class HomePresenterModule {

    private Context context;

    public HomePresenterModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideHomeContext(){
        return context;
    }

}
