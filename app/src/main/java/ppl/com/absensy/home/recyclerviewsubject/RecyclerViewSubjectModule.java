package ppl.com.absensy.home.recyclerviewsubject;

import dagger.Module;
import dagger.Provides;
import ppl.com.absensy.di.ActivityScope;

@Module
public class RecyclerViewSubjectModule {

    private RecyclerViewSubjectAdapter.Listener listener;

    public RecyclerViewSubjectModule(RecyclerViewSubjectAdapter.Listener listener) {
        this.listener = listener;
    }

    @Provides
    @ActivityScope
    public RecyclerViewSubjectAdapter providesRecyclerViewSubjectAdapter() {
        return new RecyclerViewSubjectAdapter(listener);
    }
}
