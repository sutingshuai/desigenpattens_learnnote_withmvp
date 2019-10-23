package suts.desigenpattens.learnnote.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import suts.desigenpattens.learnnote.di.ActivityContext;
import suts.desigenpattens.learnnote.di.PerActivity;
import suts.desigenpattens.learnnote.ui.login.LoginMvpPresenter;
import suts.desigenpattens.learnnote.ui.login.LoginMvpView;
import suts.desigenpattens.learnnote.ui.login.LoginPresenter;
import suts.desigenpattens.learnnote.ui.main.MainActivity;
import suts.desigenpattens.learnnote.ui.main.MainMvpPresenter;
import suts.desigenpattens.learnnote.ui.main.MainMvpView;
import suts.desigenpattens.learnnote.ui.main.MainPresenter;
import suts.desigenpattens.learnnote.ui.splash.SplashMvpPresenter;
import suts.desigenpattens.learnnote.ui.splash.SplashMvpView;
import suts.desigenpattens.learnnote.ui.splash.SplashPresenter;
import suts.desigenpattens.learnnote.utils.rx.AppSchedulerProvider;
import suts.desigenpattens.learnnote.utils.rx.SchedulerProvider;

/**
 * Created by sutingshuai on 2019-09-06
 * Describe:
 */
@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext(){
        return mActivity;
    }

    @Provides
    AppCompatActivity provideAppCompatActivity(){
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideScheduleProvider(){
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(LoginPresenter<LoginMvpView> loginPresenter){
        return loginPresenter;
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(SplashPresenter<SplashMvpView> splashPresenter){
        return splashPresenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> mainPresenter){
        return mainPresenter;
    }


}
