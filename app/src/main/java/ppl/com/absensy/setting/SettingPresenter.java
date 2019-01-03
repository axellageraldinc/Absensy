package ppl.com.absensy.setting;

import ppl.com.absensy.model.Setting;
import ppl.com.absensy.repository.SettingSharedPreferences;

public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View view;
    private SettingSharedPreferences settingSharedPreferences;

    public SettingPresenter(SettingContract.View view, SettingSharedPreferences settingSharedPreferences) {
        this.view = view;
        this.settingSharedPreferences = settingSharedPreferences;
    }

    @Override
    public void loadSettings() {
        view.showSettings(settingSharedPreferences.findAll());
    }

    @Override
    public void saveSettings(Setting setting) {
        boolean isSavingSettingsSucceeded = settingSharedPreferences.save(setting);
        if (isSavingSettingsSucceeded)
            view.showToast("Pengaturan berhasil disimpan!");
        else
            view.showToast("Oops, pengaturan gagal disimpan");
    }
}
