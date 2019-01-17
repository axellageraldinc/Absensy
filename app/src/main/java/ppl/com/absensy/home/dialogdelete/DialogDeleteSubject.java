package ppl.com.absensy.home.dialogdelete;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import ppl.com.absensy.R;
import ppl.com.absensy.base.BaseDialog;

public class DialogDeleteSubject extends BaseDialog {
    private Listener listener;

    public static DialogDeleteSubject newInstance() {
        return new DialogDeleteSubject();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        listener = (Listener) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(getResources().getStringArray(R.array.long_click_subject_options), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // delete subject
                        if (listener != null) {
                            listener.onDelete();
                            dismiss();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        return builder.create();
    }

    public interface Listener {
        void onDelete();
    }
}
