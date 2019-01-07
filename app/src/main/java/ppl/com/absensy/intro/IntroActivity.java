package ppl.com.absensy.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.inject.Inject;

import ppl.com.absensy.R;
import ppl.com.absensy.app.AbsensyApp;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.base.BaseActivity;
import ppl.com.absensy.home.HomeActivity;

public class IntroActivity extends BaseActivity
        implements IntroContract.View, ViewPager.OnPageChangeListener, View.OnClickListener {

    @Inject
    IntroContract.Presenter presenter;

    private int[] introSlides;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    private ViewPager vpIntro;
    private TextView tvNext;
    private TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        AbsensyAppComponent absensyAppComponent = ((AbsensyApp) getApplication()).getAbsensyAppComponent();
        DaggerIntroComponent.builder()
                .absensyAppComponent(absensyAppComponent)
                .introModule(new IntroModule(this))
                .build()
                .inject(this);

        introSlides = new int[]{
                R.layout.intro_slide_1_save_subject,
                R.layout.intro_slide_2_do_absence,
                R.layout.intro_slide_3_notification
        };
        vpIntro = findViewById(R.id.vpIntro);
        vpIntro.setAdapter(new IntroViewPagerAdapter(introSlides));
        vpIntro.addOnPageChangeListener(this);
        tvNext = findViewById(R.id.tvNext);
        tvNext.setOnClickListener(this);
        tvSkip = findViewById(R.id.tvSkip);
        tvSkip.setOnClickListener(this);
        dotsLayout = findViewById(R.id.layoutDots);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.checkIfFirstLaunch();
    }

    @Override
    public void showIntroSlides() {
        addSlideDots(0);
    }

    private void addSlideDots(int currentPage) {
        dots = new TextView[introSlides.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return vpIntro.getCurrentItem() + i;
    }

    @Override
    public void goToHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        // do nothing
    }

    @Override
    public void onPageSelected(int i) {
        addSlideDots(i);
        if (i == dots.length - 1) {
            // last page. make button text to FINISH
            tvNext.setText("FINISH");
            tvSkip.setVisibility(View.GONE);
        } else {
            // still pages are left
            tvNext.setText("NEXT");
            tvSkip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        // do nothing
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNext:
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < dots.length) {
                    // move to next screen
                    vpIntro.setCurrentItem(current);
                } else {
                    goToHomePage();
                }
                break;
            case R.id.tvSkip:
                goToHomePage();
                break;
            default:
                break;
        }
    }
}
