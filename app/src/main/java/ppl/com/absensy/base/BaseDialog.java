package ppl.com.absensy.base;

import android.support.v4.app.DialogFragment;
import android.view.ViewGroup;

import java.util.Objects;

public class BaseDialog extends DialogFragment {
    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = Objects.requireNonNull(getDialog().getWindow()).getAttributes();
        params.width = (getResources().getDisplayMetrics().widthPixels);
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
}
