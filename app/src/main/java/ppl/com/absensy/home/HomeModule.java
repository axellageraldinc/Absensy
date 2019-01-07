package ppl.com.absensy.home;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ppl.com.absensy.di.ActivityScope;
import ppl.com.absensy.repository.AppDatabase;
import ppl.com.absensy.repository.SharedPreferencesManager;

@Module
public class HomeModule {
    private HomeContract.View view;

    public HomeModule(HomeContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public HomeContract.Presenter providesHomePresenter(AppDatabase appDatabase, SharedPreferencesManager sharedPreferencesManager) {
        return new HomePresenter(view, appDatabase.subjectDao(), appDatabase.absenceDetailDao(), sharedPreferencesManager, new CompositeDisposable());
    }
}
