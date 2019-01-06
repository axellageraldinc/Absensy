package ppl.com.absensy.submitsubject;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

public class DialogTimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static final String HOUR_KEY = "hour";
    private static final String MINUTE_KEY = "minute";

    public static DialogTimePicker newInstance(int hour, int minute) {
        DialogTimePicker dialogTimePicker = new DialogTimePicker();
        Bundle bundle = new Bundle();
        bundle.putInt(HOUR_KEY, hour);
        bundle.putInt(MINUTE_KEY, minute);
        dialogTimePicker.setArguments(bundle);
        return dialogTimePicker;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int hour = 7;
        int minute = 15;
        if (bundle != null) {
            hour = bundle.getInt(HOUR_KEY);
            minute = bundle.getInt(MINUTE_KEY);
        }

        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Listener listener = (Listener) getActivity();
        if (listener != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            long classTime = calendar.getTimeInMillis();
            listener.onTimeSet(classTime);
        }
    }

    public interface Listener {
        void onTimeSet(long classTime);
    }
}
