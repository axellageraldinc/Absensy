package ppl.com.absensy.home.dialogchooseoption;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import ppl.com.absensy.R;
import ppl.com.absensy.base.BaseDialog;
import ppl.com.absensy.model.Subject;

public class DialogChooseOption extends BaseDialog {

    private static final String SUBJECT_KEY = "subject";

    private Context context;

    private Subject subject;

    public static DialogChooseOption newInstance(Subject subject) {
        DialogChooseOption dialogChooseOption = new DialogChooseOption();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SUBJECT_KEY, subject);
        dialogChooseOption.setArguments(bundle);
        return dialogChooseOption;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setCancelable(true);

        if (getArguments() != null) {
            this.subject = (Subject) getArguments().getSerializable(SUBJECT_KEY);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Mau ngapain?");
        builder.setItems(context.getResources().getStringArray(R.array.options), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Listener listener = (Listener) getActivity();
                switch (which) {
                    case 0: // view absence details
                        if (listener != null) {
                            listener.OnOptionClick(subject, Option.VIEW_ABSENCE_DETAILS);
                        }
                        break;
                    case 1: // edit
                        if (listener != null) {
                            listener.OnOptionClick(subject, Option.EDIT);
                        }
                        break;
                    case 2: // absen
                        if (listener != null) {
                            listener.OnOptionClick(subject, Option.ABSENCE);
                        }
                        break;
                    default:
                        break;
                }
                dismiss();
            }
        });
        return builder.create();
    }

    public enum Option {
        VIEW_ABSENCE_DETAILS,
        EDIT,
        ABSENCE
    }

    public interface Listener {
        void OnOptionClick(Subject subject, Option option);
    }
}
