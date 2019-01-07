package ppl.com.absensy.repository;

import ppl.com.absensy.model.Setting;

public interface SharedPreferencesManager {
    boolean saveSetting(Setting setting);

    Setting findAllSettings();

    void saveFirstLaunch();

    boolean isFirstLaunch();
}
