package ppl.com.absensy.absencedetails;

import dagger.Component;
import ppl.com.absensy.absencedetails.recyclerviewabsencedetails.RecyclerViewAbsenceDetailsModule;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.di.ActivityScope;

@ActivityScope
@Component(dependencies = {AbsensyAppComponent.class},
        modules = {AbsenceDetailsModule.class, RecyclerViewAbsenceDetailsModule.class})
public interface AbsenceDetailsComponent {
    void inject(AbsenceDetailsActivity absenceDetailsActivity);
}
