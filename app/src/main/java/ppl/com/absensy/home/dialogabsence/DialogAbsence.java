package ppl.com.absensy.home.dialogabsence;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ppl.com.absensy.R;
import ppl.com.absensy.base.BaseDialog;
import ppl.com.absensy.model.Subject;

public class DialogAbsence extends BaseDialog implements View.OnClickListener {

    private static final String SUBJECT_KEY = "subject";

    private Subject subject;

    public DialogAbsence() {
        // required
    }

    public static DialogAbsence newInstance(Subject subject) {
        DialogAbsence dialogAbsence = new DialogAbsence();
        dialogAbsence.setStyle(STYLE_NO_TITLE, 0);
        Bundle bundle = new Bundle();
        bundle.putParcelable(SUBJECT_KEY, subject);
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
            subject = getArguments().getParcelable(SUBJECT_KEY);
            tvSubjectName.setText(String.format("Kuliah : %s", subject.getName()));
            tvSubjectAbsenceAmount.setText(String.format("Kosong %s kali", String.valueOf(subject.getAbsenceAmount())));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAbsence:
                Listener listener = (Listener) getActivity();
                if (listener != null) {
                    listener.onAbsence(subject);
                }
                break;
            default:
                break;
        }
    }

    public interface Listener {
        void onAbsence(Subject subject);
    }
}
