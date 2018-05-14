package ppl.com.absensy.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class MatkulAdapterInteractorModule {
    private Context context;

    public MatkulAdapterInteractorModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideMatkulAdapterContext(){
        return context;
    }
}
