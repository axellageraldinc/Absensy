package ppl.com.absensy.intro;

import dagger.Component;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.di.ActivityScope;

@ActivityScope
@Component(dependencies = {AbsensyAppComponent.class},
            modules = {IntroModule.class})
public interface IntroComponent {
    void inject(IntroActivity introActivity);
}
