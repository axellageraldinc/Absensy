package ppl.com.absensy.setting;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;

import javax.inject.Inject;

import ppl.com.absensy.R;
import ppl.com.absensy.app.AbsensyApp;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.base.BaseActivity;
import ppl.com.absensy.model.Setting;

public class SettingActivity extends BaseActivity implements SettingContract.View {

    @Inject
    SettingContract.Presenter presenter;
    private EditText etMaxAbsenceAmount;
    private Switch swSubjectReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        AbsensyAppComponent absensyAppComponent = ((AbsensyApp) getApplication()).getAbsensyAppComponent();

        DaggerSettingComponent.builder()
                .absensyAppComponent(absensyAppComponent)
                .settingModule(new SettingModule(this))
                .build()
                .inject(this);

        etMaxAbsenceAmount = findViewById(R.id.etMaxAbsenceAmount);
        swSubjectReminder = findViewById(R.id.swSubjectReminder);

        setToolbarTitle(getResources().getString(R.string.settings), true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadSettings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveSettings:
                Setting setting = Setting.builder()
                        .maxAbsenceAmount(Integer.parseInt(etMaxAbsenceAmount.getText().toString()))
                        .subjectReminder(swSubjectReminder.isChecked())
                        .build();
                presenter.saveSettings(setting);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showSettings(Setting setting) {
        etMaxAbsenceAmount.setText(String.valueOf(setting.getMaxAbsenceAmount()));
        etMaxAbsenceAmount.setSelection(String.valueOf(setting.getMaxAbsenceAmount()).length());
        swSubjectReminder.setChecked(setting.isSubjectReminder());
    }
}
