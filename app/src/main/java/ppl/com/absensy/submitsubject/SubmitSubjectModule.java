package ppl.com.absensy.submitsubject;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ppl.com.absensy.di.ActivityScope;
import ppl.com.absensy.reminder.ClassReminder;
import ppl.com.absensy.repository.AppDatabase;

@Module
public class SubmitSubjectModule {
    private SubmitSubjectContract.View view;

    public SubmitSubjectModule(SubmitSubjectContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public SubmitSubjectContract.Presenter providesSubmitSubjectPresenter(AppDatabase appDatabase, ClassReminder classReminder) {
        return new SubmitSubjectPresenter(new CompositeDisposable(), view, appDatabase.subjectDao(), classReminder);
    }
}
