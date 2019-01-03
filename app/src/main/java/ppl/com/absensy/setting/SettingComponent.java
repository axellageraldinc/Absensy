package ppl.com.absensy.setting;

import dagger.Component;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.di.ActivityScope;

@ActivityScope
@Component(dependencies = {AbsensyAppComponent.class},
        modules = {SettingModule.class})
public interface SettingComponent {
    void inject(SettingActivity settingActivity);
}
