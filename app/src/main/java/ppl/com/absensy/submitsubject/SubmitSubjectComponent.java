package ppl.com.absensy.submitsubject;

import dagger.Component;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.di.ActivityScope;

@ActivityScope
@Component(dependencies = {AbsensyAppComponent.class},
        modules = {SubmitSubjectModule.class})
public interface SubmitSubjectComponent {
    void inject(SubmitSubjectActivity submitSubjectActivity);
}
