package suts.desigenpattens.learnnote.di.component;

import dagger.Component;
import suts.desigenpattens.learnnote.di.PerActivity;
import suts.desigenpattens.learnnote.di.module.ActivityModule;
import suts.desigenpattens.learnnote.ui.login.LoginActivity;
import suts.desigenpattens.learnnote.ui.splash.SplashActivity;

/**
 * Created by sutingshuai on 2019-09-10
 * Describe:
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivity loginActivity);

    void inject(SplashActivity splashActivity);
}
