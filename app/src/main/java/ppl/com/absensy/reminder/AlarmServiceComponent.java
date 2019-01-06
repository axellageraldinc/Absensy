package ppl.com.absensy.reminder;

import dagger.Component;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.di.ServiceScope;

@ServiceScope
@Component(dependencies = {AbsensyAppComponent.class})
public interface AlarmServiceComponent {
    void inject(AlarmService alarmService);
}
