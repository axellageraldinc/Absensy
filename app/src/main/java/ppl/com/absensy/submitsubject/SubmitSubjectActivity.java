package ppl.com.absensy.submitsubject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import ppl.com.absensy.R;
import ppl.com.absensy.app.AbsensyApp;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.base.BaseActivity;
import ppl.com.absensy.helper.ValidationConstant;
import ppl.com.absensy.model.Subject;

public class SubmitSubjectActivity
        extends BaseActivity
        implements SubmitSubjectContract.View, View.OnClickListener, DialogTimePicker.Listener, AdapterView.OnItemSelectedListener {

    private static final String DIALOG_TIME_PICKER_TAG = "dialogTimePicker";
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat classDayFormat = new SimpleDateFormat("u"); // Day number of week (1 = Monday, ..., 7 = Sunday)
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat classTimeFormat = new SimpleDateFormat("HH:mm");
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
    private Calendar calendar = Calendar.getInstance();

    @Inject
    SubmitSubjectContract.Presenter presenter;
    private EditText etSubjectName;
    private Spinner spinnerClassDay;
    private TextView tvClassTime;
    private Subject subject;

    private int classDay = 0;
    private int classHour = 7;
    private int classMinute = 15;
    private boolean isClassTimeChosen = false;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_subject);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        AbsensyAppComponent absensyAppComponent = ((AbsensyApp) getApplication()).getAbsensyAppComponent();

        DaggerSubmitSubjectComponent.builder()
                .absensyAppComponent(absensyAppComponent)
                .submitSubjectModule(new SubmitSubjectModule(this))
                .build()
                .inject(this);

        etSubjectName = findViewById(R.id.etSubjectName);
        awesomeValidation.addValidation(etSubjectName, ValidationConstant.NOT_EMPTY_PATTERN, ValidationConstant.NOT_EMPTY_MESSAGE);
        spinnerClassDay = findViewById(R.id.spinnerClassDay);
        spinnerClassDay.setOnItemSelectedListener(this);
        setSpinnerClassDayContents();
        tvClassTime = findViewById(R.id.tvClassTime);
        tvClassTime.setOnClickListener(this);
        Button btnSubmitSubject = findViewById(R.id.btnSubmitSubject);
        btnSubmitSubject.setOnClickListener(this);

        Intent intent = getIntent();
        subject = intent.getParcelableExtra(getResources().getString(R.string.subject));
        if (subject != null) {
            etSubjectName.setText(subject.getName());
            etSubjectName.setSelection(subject.getName().length());
            classDay = Integer.parseInt(classDayFormat.format(subject.getClassSchedule())) - 1;
            tvClassTime.setText(classTimeFormat.format(subject.getClassSchedule()));
            classHour = Integer.parseInt(hourFormat.format(subject.getClassSchedule()));
            classMinute = Integer.parseInt(minuteFormat.format(subject.getClassSchedule()));
            setToolbarTitle(subject.getName(), true);
            isClassTimeChosen = true;
        } else
            setToolbarTitle("Tambah mata kuliah baru", true);
        spinnerClassDay.setSelection(classDay);
    }

    private void setSpinnerClassDayContents() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClassDay.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (subject != null) {
            getMenuInflater().inflate(R.menu.delete_menu, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                presenter.deleteSubject(subject);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void moveToPreviousPage() {
        onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmitSubject:
                if (!isClassTimeChosen) {
                    showToast("Harap pilih jadwal kelas");
                    return;
                }
                if (awesomeValidation.validate()) {
                    setClassSchedule();
                    if (subject == null) {
                        subject = Subject.builder()
                                .id(UUID.randomUUID().toString())
                                .name(etSubjectName.getText().toString())
                                .absenceAmount(0) // default is 0
                                .classSchedule(calendar.getTime())
                                .build();
                        presenter.saveSubject(subject);
                    } else {
                        Subject editedsubject = Subject.builder()
                                .id(subject.getId())
                                .name(etSubjectName.getText().toString())
                                .absenceAmount(subject.getAbsenceAmount())
                                .classSchedule(calendar.getTime())
                                .build();
                        presenter.updateSubject(subject, editedsubject);
                    }
                }
                break;
            case R.id.tvClassTime:
                DialogFragment dialogTimePicker = DialogTimePicker.newInstance(classHour, classMinute);
                dialogTimePicker.show(getSupportFragmentManager(), DIALOG_TIME_PICKER_TAG);
                break;
            default:
                break;
        }
    }

    private void setClassSchedule() {
        calendar.set(Calendar.DAY_OF_WEEK, classDay);
        String classTime = tvClassTime.getText().toString();
        String[] classTimeHourAndMinute = classTime.split(":");
        String classTimeHour = classTimeHourAndMinute[0];
        String classTimeMinute = classTimeHourAndMinute[1];
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(classTimeHour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(classTimeMinute));
    }

    @Override
    public void OnTimeSet(long classTime) {
        Date classTimeDate = new Date(classTime);
        tvClassTime.setText(classTimeFormat.format(classTimeDate));
        classHour = Integer.parseInt(hourFormat.format(classTimeDate));
        classMinute = Integer.parseInt(minuteFormat.format(classTimeDate));
        isClassTimeChosen = true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerClassDay:
                classDay = parent.getSelectedItemPosition() + 2; // get selected item index + 2 (because every single day in Calendar is 2 indexes ahead of selected item index)
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // do nothing
    }
}
