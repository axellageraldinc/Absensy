package ppl.com.absensy.setting;

import dagger.Module;
import dagger.Provides;
import ppl.com.absensy.di.ActivityScope;
import ppl.com.absensy.repository.SettingSharedPreferences;

@Module
public class SettingModule {
    private SettingContract.View view;

    public SettingModule(SettingContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public SettingContract.Presenter providesSettingPresenter(SettingSharedPreferences settingSharedPreferences) {
        return new SettingPresenter(view, settingSharedPreferences);
    }
}
