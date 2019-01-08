package ppl.com.absensy.home.dialogchooseoption;

import dagger.Component;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.di.DialogScope;

@DialogScope
@Component(dependencies = {AbsensyAppComponent.class},
            modules = {DialogChooseOptionModule.class})
public interface DialogChooseOptionComponent {
    void inject(DialogChooseOption dialogChooseOption);
}
