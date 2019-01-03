package ppl.com.absensy.setting;

import ppl.com.absensy.base.BaseView;
import ppl.com.absensy.model.Setting;

public interface SettingContract {
    interface View extends BaseView {
        void showSettings(Setting setting);
    }

    interface Presenter {
        void loadSettings();

        void saveSettings(Setting setting);
    }
}
