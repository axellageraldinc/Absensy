package ppl.com.absensy.home;

import dagger.Component;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.di.ActivityScope;
import ppl.com.absensy.home.recyclerviewsubject.RecyclerViewSubjectModule;

@ActivityScope
@Component(dependencies = {AbsensyAppComponent.class},
        modules = {HomeModule.class, RecyclerViewSubjectModule.class})
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
}
