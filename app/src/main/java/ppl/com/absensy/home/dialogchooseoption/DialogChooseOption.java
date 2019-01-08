package ppl.com.absensy.home.dialogchooseoption;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import javax.inject.Inject;

import ppl.com.absensy.R;
import ppl.com.absensy.app.AbsensyApp;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.base.BaseDialog;
import ppl.com.absensy.model.Subject;

public class DialogChooseOption extends BaseDialog implements RecyclerViewOptionsAdapter.Listener {

    private static final String SUBJECT_KEY = "subject";

    @Inject
    RecyclerViewOptionsAdapter recyclerViewOptionsAdapter;
    @Inject
    BottomLineRecyclerViewDecoration bottomLineRecyclerViewDecoration;
    private Context context;
    private Subject subject;

    public static DialogChooseOption newInstance(Subject subject) {
        DialogChooseOption dialogChooseOption = new DialogChooseOption();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SUBJECT_KEY, subject);
        dialogChooseOption.setArguments(bundle);
        return dialogChooseOption;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AbsensyAppComponent absensyAppComponent = ((AbsensyApp) getContext().getApplicationContext()).getAbsensyAppComponent();
        DaggerDialogChooseOptionComponent.builder()
                .absensyAppComponent(absensyAppComponent)
                .dialogChooseOptionModule(new DialogChooseOptionModule(context, this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_choose_option, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(true);

        if (getArguments() != null) {
            this.subject = getArguments().getParcelable(SUBJECT_KEY);
        }

        RecyclerView rvOptions = view.findViewById(R.id.rvOptions);
        rvOptions.setLayoutManager(new LinearLayoutManager(context));
        rvOptions.addItemDecoration(bottomLineRecyclerViewDecoration);
        rvOptions.setAdapter(recyclerViewOptionsAdapter);
        recyclerViewOptionsAdapter.updateData(Arrays.asList(context.getResources().getStringArray(R.array.options)));
    }

    @Override
    public void onItemClick(int chosenOptionIndex) {
        Listener listener = (Listener) getActivity();
        if (listener != null) {
            listener.onOptionClick(subject, Option.values()[chosenOptionIndex]);
            dismiss();
        }
    }

    public enum Option {
        VIEW_ABSENCE_DETAILS,
        EDIT,
        ABSENCE
    }

    public interface Listener {
        void onOptionClick(Subject subject, Option option);
    }
}
