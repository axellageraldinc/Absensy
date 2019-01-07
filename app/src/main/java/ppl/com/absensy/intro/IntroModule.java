package ppl.com.absensy.intro;

import dagger.Module;
import dagger.Provides;
import ppl.com.absensy.di.ActivityScope;
import ppl.com.absensy.repository.SharedPreferencesManager;

@Module
public class IntroModule {

    private IntroContract.View view;

    public IntroModule(IntroContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public IntroContract.Presenter providesIntroPresenter(SharedPreferencesManager sharedPreferencesManager) {
        return new IntroPresenter(view, sharedPreferencesManager);
    }
}
