package ppl.com.absensy.base;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import ppl.com.absensy.R;

public class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setToolbarTitle(String title, boolean showBackButton) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showBackButton);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView tvToolbarTitle = toolbar.findViewById(R.id.toolbarTitle);
        tvToolbarTitle.setText(title);
    }
}
