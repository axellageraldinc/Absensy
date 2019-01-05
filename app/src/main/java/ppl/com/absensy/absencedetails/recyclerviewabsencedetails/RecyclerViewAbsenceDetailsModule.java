package ppl.com.absensy.absencedetails.recyclerviewabsencedetails;

import dagger.Module;
import dagger.Provides;
import ppl.com.absensy.di.ActivityScope;

@Module
public class RecyclerViewAbsenceDetailsModule {
    @Provides
    @ActivityScope
    public RecyclerViewAbsenceDetailsAdapter providesRecyclerViewAbsenceDetailsAdapter() {
        return new RecyclerViewAbsenceDetailsAdapter();
    }
}
