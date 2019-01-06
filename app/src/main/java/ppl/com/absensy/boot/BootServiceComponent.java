package ppl.com.absensy.boot;

import dagger.Component;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.di.ServiceScope;

@ServiceScope
@Component(dependencies = {AbsensyAppComponent.class})
public interface BootServiceComponent {
    void inject(BootService bootService);
}
