package ppl.com.absensy.intro;

import ppl.com.absensy.repository.SharedPreferencesManager;

public class IntroPresenter implements IntroContract.Presenter {

    private IntroContract.View view;
    private SharedPreferencesManager sharedPreferencesManager;

    public IntroPresenter(IntroContract.View view, SharedPreferencesManager sharedPreferencesManager) {
        this.view = view;
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    @Override
    public void checkIfFirstLaunch() {
        boolean isFirstLaunch = sharedPreferencesManager.isFirstLaunch();
        if (isFirstLaunch) {
            view.showIntroSlides();
            sharedPreferencesManager.saveFirstLaunch();
        } else
            view.goToHomePage();
    }
}
