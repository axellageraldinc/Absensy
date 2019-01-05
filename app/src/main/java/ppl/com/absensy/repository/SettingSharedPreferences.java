package ppl.com.absensy.repository;

import ppl.com.absensy.model.Setting;

public interface SettingSharedPreferences {
    boolean save(Setting setting);

    Setting findAll();
}
