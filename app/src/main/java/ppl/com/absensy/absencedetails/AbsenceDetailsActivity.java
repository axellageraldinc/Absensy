package ppl.com.absensy.absencedetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import ppl.com.absensy.R;
import ppl.com.absensy.absencedetails.recyclerviewabsencedetails.RecyclerViewAbsenceDetailsAdapter;
import ppl.com.absensy.absencedetails.recyclerviewabsencedetails.RecyclerViewAbsenceDetailsModule;
import ppl.com.absensy.app.AbsensyApp;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.base.BaseActivity;
import ppl.com.absensy.model.AbsenceDetail;
import ppl.com.absensy.model.Subject;

public class AbsenceDetailsActivity extends BaseActivity implements AbsenceDetailsContract.View {

    @Inject
    AbsenceDetailsContract.Presenter presenter;
    @Inject
    RecyclerViewAbsenceDetailsAdapter recyclerViewAbsenceDetailsAdapter;

    private ConstraintLayout constraintLayoutNeverAbsence;
    private RecyclerView rvAbsenceDetails;
    private Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence_details);

        AbsensyAppComponent absensyAppComponent = ((AbsensyApp) getApplication()).getAbsensyAppComponent();
        DaggerAbsenceDetailsComponent.builder()
                .absensyAppComponent(absensyAppComponent)
                .absenceDetailsModule(new AbsenceDetailsModule(this))
                .recyclerViewAbsenceDetailsModule(new RecyclerViewAbsenceDetailsModule())
                .build()
                .inject(this);

        Intent intent = getIntent();
        subject = intent.getParcelableExtra(getResources().getString(R.string.subject));

        rvAbsenceDetails = findViewById(R.id.rvAbsenceDetails);
        rvAbsenceDetails.setLayoutManager(new LinearLayoutManager(this));
        rvAbsenceDetails.setAdapter(recyclerViewAbsenceDetailsAdapter);
        constraintLayoutNeverAbsence = findViewById(R.id.constraintLayoutNeverAbsence);

        setToolbarTitle(subject.getName(), true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadAbsenceDetails(subject.getId());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showAbsenceDetails(List<AbsenceDetail> absenceDetailList) {
        if (absenceDetailList.isEmpty()) {
            constraintLayoutNeverAbsence.setVisibility(View.VISIBLE);
            rvAbsenceDetails.setVisibility(View.GONE);
        } else {
            constraintLayoutNeverAbsence.setVisibility(View.GONE);
            rvAbsenceDetails.setVisibility(View.VISIBLE);
            recyclerViewAbsenceDetailsAdapter.updateData(absenceDetailList);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
