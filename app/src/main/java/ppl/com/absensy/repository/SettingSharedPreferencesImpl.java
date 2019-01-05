package ppl.com.absensy.repository;

import android.content.Context;
import android.content.SharedPreferences;

import ppl.com.absensy.R;
import ppl.com.absensy.model.Setting;

public class SettingSharedPreferencesImpl implements SettingSharedPreferences {

    private static final String MAX_ABSENCE_AMOUNT_KEY = "maxAbsenceAmount";
    private static final String SUBJECT_REMINDER_KEY = "subjectReminder";

    private SharedPreferences sharedPreferences;

    public SettingSharedPreferencesImpl(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.setting), Context.MODE_PRIVATE);
    }

    @Override
    public boolean save(Setting setting) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(MAX_ABSENCE_AMOUNT_KEY, setting.getMaxAbsenceAmount());
        editor.putBoolean(SUBJECT_REMINDER_KEY, setting.isSubjectReminder());
        return editor.commit();
    }

    @Override
    public Setting findAll() {
        int maxAbsenceAmount = sharedPreferences.getInt(MAX_ABSENCE_AMOUNT_KEY, 3);
        boolean isSubjectReminder = sharedPreferences.getBoolean(SUBJECT_REMINDER_KEY, true);
        return Setting.builder()
                .maxAbsenceAmount(maxAbsenceAmount)
                .subjectReminder(isSubjectReminder)
                .build();
    }
}
