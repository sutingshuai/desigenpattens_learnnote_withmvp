package suts.desigenpattens.learnnote.ui.main;

import com.androidnetworking.error.ANError;

import io.reactivex.disposables.CompositeDisposable;
import suts.desigenpattens.learnnote.base.BasePresenter;
import suts.desigenpattens.learnnote.base.MvpPresenter;
import suts.desigenpattens.learnnote.data.DataManager;
import suts.desigenpattens.learnnote.di.PerActivity;
import suts.desigenpattens.learnnote.utils.rx.SchedulerProvider;

/**
 * Created by sutingshuai on 2019-09-20
 * Describe:
 */
@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    void onDrawerOptionAboutClick();

    void onDrawerOptionLogoutClick();

    void onDrawerRateUsClick();

    void onDrawerMyFeedClick();

    void onViewInitialized();

    void onCardExhausted();

    void onNavMenuCreated();

}
