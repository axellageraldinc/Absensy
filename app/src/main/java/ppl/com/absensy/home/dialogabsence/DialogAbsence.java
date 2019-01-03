package ppl.com.absensy.home.dialogabsence;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ppl.com.absensy.R;
import ppl.com.absensy.base.BaseDialog;
import ppl.com.absensy.model.Subject;

public class DialogAbsence extends BaseDialog implements View.OnClickListener {

    private static final String TAG = DialogAbsence.class.getName();

    private static final String SUBJECT_KEY = "subject";

    private Subject subject;

    public DialogAbsence() {
        // required
    }

    public static DialogAbsence newInstance(Subject subject) {
        DialogAbsence dialogAbsence = new DialogAbsence();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SUBJECT_KEY, subject);
        dialogAbsence.setArguments(bundle);
        return dialogAbsence;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_absence, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(true);
        TextView tvSubjectName = view.findViewById(R.id.tvSubjectName);
        TextView tvSubjectAbsenceAmount = view.findViewById(R.id.tvAbsenceAmount);
        Button btnAbsence = view.findViewById(R.id.btnAbsence);
        btnAbsence.setOnClickListener(this);

        if (getArguments() != null) {
            Subject subject = (Subject) getArguments().getSerializable(SUBJECT_KEY);
            this.subject = subject;
            tvSubjectName.setText(String.format("Kuliah : %s", subject.getName()));
            tvSubjectAbsenceAmount.setText(String.format("Kosong %s kali", String.valueOf(subject.getAbsenceAmount())));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAbsence:
                // TODO : Instead of directly absence, give a challenge such as simple math (the difficulty level can be adjusted from settings later on) --> Alarmy app
                Listener listener = (Listener) getActivity();
                if (listener != null) {
                    Log.d(TAG, "onAbsence OK");
                    listener.OnAbsence(subject);
                }
                break;
            default:
                break;
        }
    }

    public interface Listener {
        void OnAbsence(Subject subject);
    }
}
