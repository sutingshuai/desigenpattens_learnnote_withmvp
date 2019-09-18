package suts.desigenpattens.learnnote.base;

import android.app.Application;

import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import suts.desigenpattens.learnnote.data.DataManager;
import suts.desigenpattens.learnnote.di.component.ApplicationComponent;
import suts.desigenpattens.learnnote.di.component.DaggerApplicationComponent;
import suts.desigenpattens.learnnote.di.module.ApplicationModule;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by sutingshuai on 2019-08-30
 * Describe:
 */
public class MvpApp extends Application {

    @Inject
    DataManager dataManager;

    @Inject
    CalligraphyConfig calligraphyConfig;


    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        mApplicationComponent.inject(this);

        //TODO init NetWorking

        CalligraphyConfig.initDefault(calligraphyConfig);

        Stetho.initializeWithDefaults(this);  //facebook stetho
    }



    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        this.mApplicationComponent = applicationComponent;
    }
}
