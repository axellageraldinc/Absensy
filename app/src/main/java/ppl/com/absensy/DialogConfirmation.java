package ppl.com.absensy;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import ppl.com.absensy.base.BaseDialog;

public class DialogConfirmation extends BaseDialog {

    private static final String TITLE_KEY = "title";
    private static final String BODY_KEY = "body";

    public static DialogConfirmation newInstance(String title, String content) {
        DialogConfirmation dialogConfirmation = new DialogConfirmation();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_KEY, title);
        bundle.putString(BODY_KEY, content);
        dialogConfirmation.setArguments(bundle);
        return dialogConfirmation;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String title = getArguments().getString(TITLE_KEY);
        String body = getArguments().getString(BODY_KEY);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(body);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Listener listener = (Listener) getActivity();
                if (listener != null) {
                    listener.onConfirm();
                    dismiss();
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }

    public interface Listener {
        void onConfirm();
    }
}
