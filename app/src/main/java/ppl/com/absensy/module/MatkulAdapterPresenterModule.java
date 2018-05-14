package ppl.com.absensy.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class MatkulAdapterPresenterModule {
    private Context context;

    public MatkulAdapterPresenterModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideMatkulAdapterContext(){
        return context;
    }
}
