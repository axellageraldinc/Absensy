package ppl.com.absensy.intro;

import ppl.com.absensy.base.BaseView;

public interface IntroContract {
    interface View extends BaseView {
        void showIntroSlides();
        void goToHomePage();
    }
    interface Presenter {
        void checkIfFirstLaunch();
    }
}
