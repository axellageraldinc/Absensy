package ppl.com.absensy.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import javax.inject.Inject;

import ppl.com.absensy.R;
import ppl.com.absensy.absencedetails.AbsenceDetailsActivity;
import ppl.com.absensy.app.AbsensyApp;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.base.BaseActivity;
import ppl.com.absensy.home.dialogabsence.DialogAbsence;
import ppl.com.absensy.home.dialogchooseoption.DialogChooseOption;
import ppl.com.absensy.home.recyclerviewsubject.RecyclerViewSubjectAdapter;
import ppl.com.absensy.home.recyclerviewsubject.RecyclerViewSubjectModule;
import ppl.com.absensy.model.Subject;
import ppl.com.absensy.setting.SettingActivity;
import ppl.com.absensy.submitsubject.SubmitSubjectActivity;

public class HomeActivity
        extends BaseActivity
        implements HomeContract.View, Button.OnClickListener, RecyclerViewSubjectAdapter.Listener, DialogChooseOption.Listener, DialogAbsence.Listener {

    private static final String DIALOG_CHOOSE_OPTION_TAG = "dialogChooseOption";
    private static final String DIALOG_ABSENCE_TAG = "dialogAbsence";

    @Inject
    HomeContract.Presenter presenter;
    @Inject
    RecyclerViewSubjectAdapter recyclerViewSubjectAdapter;

    private DialogFragment dialogAbsence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AbsensyAppComponent absensyAppComponent = ((AbsensyApp) getApplication()).getAbsensyAppComponent();

        DaggerHomeComponent.builder()
                .absensyAppComponent(absensyAppComponent)
                .homeModule(new HomeModule(this))
                .recyclerViewSubjectModule(new RecyclerViewSubjectModule(this))
                .build()
                .inject(this);

        FloatingActionButton btnAddSubject = findViewById(R.id.btnAddSubject);
        btnAddSubject.setOnClickListener(this);

        RecyclerView rvSubjects = findViewById(R.id.rvSubjects);
        rvSubjects.setLayoutManager(new LinearLayoutManager(this));
        rvSubjects.setAdapter(recyclerViewSubjectAdapter);

        setToolbarTitle("Home", false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getAllSubjects();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showSubjectList(List<Subject> subjectList) {
        recyclerViewSubjectAdapter.updateData(subjectList);
    }

    @Override
    public void dismissDialogAbsence() {
        if (dialogAbsence != null)
            dialogAbsence.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddSubject:
                Intent intent = new Intent(this, SubmitSubjectActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void OnItemClick(Subject subject) {
        DialogFragment dialogChooseOption = DialogChooseOption.newInstance(subject);
        dialogChooseOption.show(getSupportFragmentManager(), DIALOG_CHOOSE_OPTION_TAG);
    }

    @Override
    public void OnOptionClick(Subject subject, DialogChooseOption.Option option) {
        Intent intent;
        switch (option) {
            case VIEW_ABSENCE_DETAILS:
                intent = new Intent(this, AbsenceDetailsActivity.class);
                intent.putExtra(getResources().getString(R.string.subject), subject);
                startActivity(intent);
                break;
            case EDIT:
                intent = new Intent(this, SubmitSubjectActivity.class);
                intent.putExtra(getResources().getString(R.string.subject), subject);
                startActivity(intent);
                break;
            case ABSENCE:
                dialogAbsence = DialogAbsence.newInstance(subject);
                dialogAbsence.show(getSupportFragmentManager(), DIALOG_ABSENCE_TAG);
                break;
            default:
                break;
        }
    }

    @Override
    public void OnAbsence(Subject subject) {
        presenter.absenceSubject(subject);
    }
}
