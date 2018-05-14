package ppl.com.absensy.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeInteractorModule {

    private Context context;

    public HomeInteractorModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideHomeContext(){
        return context;
    }

}
