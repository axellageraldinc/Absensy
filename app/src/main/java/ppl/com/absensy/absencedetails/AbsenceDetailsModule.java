package ppl.com.absensy.absencedetails;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ppl.com.absensy.di.ActivityScope;
import ppl.com.absensy.repository.AppDatabase;

@Module
public class AbsenceDetailsModule {
    private AbsenceDetailsContract.View view;

    public AbsenceDetailsModule(AbsenceDetailsContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public AbsenceDetailsContract.Presenter providesAbsenceDetailsPresenter(AppDatabase appDatabase) {
        return new AbsenceDetailsPresenter(view, appDatabase.absenceDetailDao(), new CompositeDisposable());
    }
}
