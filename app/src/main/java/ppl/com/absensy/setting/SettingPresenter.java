package ppl.com.absensy.setting;

import ppl.com.absensy.model.Setting;
import ppl.com.absensy.repository.SharedPreferencesManager;

public class SettingPresenter implements SettingContract.Presenter {

    private static final String SETTINGS_SAVED_SUCCESS_MESSAGE = "Pengaturan berhasil disimpan!";
    private static final String CANNOT_SAVE_SETTINGS_ERROR_MESSAGE = "Oops, pengaturan gagal disimpan";

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
            view.showToast(SETTINGS_SAVED_SUCCESS_MESSAGE);
        else
            view.showToast(CANNOT_SAVE_SETTINGS_ERROR_MESSAGE);
    }
}
