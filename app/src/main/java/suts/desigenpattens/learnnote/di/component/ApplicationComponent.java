package suts.desigenpattens.learnnote.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import suts.desigenpattens.learnnote.base.MvpApp;
import suts.desigenpattens.learnnote.data.DataManager;
import suts.desigenpattens.learnnote.di.ApplicationContext;
import suts.desigenpattens.learnnote.di.module.ApplicationModule;

/**
 * Created by sutingshuai on 2019-08-30
 * Describe:
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MvpApp mvpApp);

    @ApplicationContext
    Context context();

    Application application();
    DataManager getDatamanager();
}
