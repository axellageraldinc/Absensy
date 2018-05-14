package ppl.com.absensy.component;

import dagger.Component;
import ppl.com.absensy.interactor.MatkulAdapterInteractorImpl;
import ppl.com.absensy.module.MatkulAdapterInteractorModule;

@Component(modules = {MatkulAdapterInteractorModule.class})
public interface MatkulAdapterInteractorComponent {
    MatkulAdapterInteractorImpl provideMatkulAdapterInteractor();
}
