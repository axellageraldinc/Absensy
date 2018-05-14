package ppl.com.absensy.component;

import dagger.Component;
import ppl.com.absensy.module.HomePresenterModule;
import ppl.com.absensy.presenter.HomePresenterImpl;

@Component(modules = {HomePresenterModule.class})
public interface HomePresenterComponent {
    HomePresenterImpl provideHomePresenter();
}
