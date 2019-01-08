package ppl.com.absensy.home.dialogchooseoption;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import ppl.com.absensy.di.DialogScope;

@Module
public class DialogChooseOptionModule {

    private Context context;
    private RecyclerViewOptionsAdapter.Listener listener;

    public DialogChooseOptionModule(Context context, RecyclerViewOptionsAdapter.Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Provides
    @DialogScope
    public RecyclerViewOptionsAdapter providesRecyclerViewOptionsAdapter() {
        return new RecyclerViewOptionsAdapter(listener);
    }

    @Provides
    @DialogScope
    public BottomLineRecyclerViewDecoration providesDividerItemDecoration() {
        return new BottomLineRecyclerViewDecoration(context, LinearLayoutManager.VERTICAL);
    }
}
