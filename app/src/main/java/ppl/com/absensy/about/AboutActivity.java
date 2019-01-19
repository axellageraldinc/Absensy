package ppl.com.absensy.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ppl.com.absensy.R;
import ppl.com.absensy.base.BaseActivity;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView tvGithubLink = findViewById(R.id.tvGithubLink);
        tvGithubLink.setOnClickListener(this);

        setToolbarTitle(getString(R.string.tentang_absensy), true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvGithubLink:
                openInBrowser(getString(R.string.github_link));
                break;
            default:
                break;
        }
    }

    private void openInBrowser(String link) {
        Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(implicit);
    }
}
