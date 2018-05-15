package ppl.com.absensy.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ppl.com.absensy.contract.MatkulAdapterContract;

@Module
public class MatkulAdapterPresenterModule {
    private Context context;
    private MatkulAdapterContract.Interactor matkulAdapterInteractor;

    public MatkulAdapterPresenterModule(Context context, MatkulAdapterContract.Interactor matkulAdapterInteractor) {
        this.context = context;
        this.matkulAdapterInteractor = matkulAdapterInteractor;
    }

    @Provides
    public Context provideMatkulAdapterContext(){
        return context;
    }

    @Provides
    public MatkulAdapterContract.Interactor provideMatkulAdapterInteractor(){
        return matkulAdapterInteractor;
    }
}
