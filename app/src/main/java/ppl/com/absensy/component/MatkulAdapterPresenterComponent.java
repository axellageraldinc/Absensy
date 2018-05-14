package ppl.com.absensy.component;

import dagger.Component;
import ppl.com.absensy.module.MatkulAdapterPresenterModule;
import ppl.com.absensy.presenter.MatkulAdapterPresenterImpl;

@Component(modules = {MatkulAdapterPresenterModule.class})
public interface MatkulAdapterPresenterComponent {
    MatkulAdapterPresenterImpl provideMatkulAdapterPresenter();
}
