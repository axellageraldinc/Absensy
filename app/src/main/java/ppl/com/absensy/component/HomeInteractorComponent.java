package ppl.com.absensy.component;

import dagger.Component;
import ppl.com.absensy.interactor.HomeInteractorImpl;
import ppl.com.absensy.module.HomeInteractorModule;

@Component(modules = {HomeInteractorModule.class})
public interface HomeInteractorComponent {
    HomeInteractorImpl provideHomeInteractor();
}
