package ppl.com.absensy.setting;

import ppl.com.absensy.model.Setting;
import ppl.com.absensy.repository.SharedPreferencesManager;

public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View view;
    private SharedPreferencesManager sharedPreferencesManager;

    public SettingPresenter(SettingContract.View view, SharedPreferencesManager sharedPreferencesManager) {
        this.view = view;
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    @Override
    public void loadSettings() {
        view.showSettings(sharedPreferencesManager.findAllSettings());
    }

    @Override
    public void saveSettings(Setting setting) {
        boolean isSavingSettingsSucceeded = sharedPreferencesManager.saveSetting(setting);
        if (isSavingSettingsSucceeded)
            view.showToast("Pengaturan berhasil disimpan!");
        else
            view.showToast("Oops, pengaturan gagal disimpan");
    }
}
