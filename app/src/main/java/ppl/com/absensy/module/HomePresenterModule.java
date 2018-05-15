package ppl.com.absensy.module;

import dagger.Module;
import dagger.Provides;
import ppl.com.absensy.contract.HomeContract;

@Module
public class HomePresenterModule {

    private HomeContract.Interactor homeInteractor;

    public HomePresenterModule(HomeContract.Interactor homeInteractor) {
        this.homeInteractor = homeInteractor;
    }

    @Provides
    public HomeContract.Interactor provideHomeInteractor(){
        return homeInteractor;
    }

}
