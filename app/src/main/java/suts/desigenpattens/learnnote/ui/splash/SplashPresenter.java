package suts.desigenpattens.learnnote.ui.splash;

import suts.desigenpattens.learnnote.base.BasePresenter;
import suts.desigenpattens.learnnote.data.DataManager;
import suts.desigenpattens.learnnote.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by sutingshuai on 2019-09-11
 * Describe:
 */
public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    @Inject
    public SplashPresenter(DataManager mDataManager, SchedulerProvider mSchedulerProvider, CompositeDisposable mCompositeDisposable) {
        super(mDataManager, mSchedulerProvider, mCompositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

        getMvpView().startSyncService();

        getCompositeDisposable().add(getDataManager()
                .seedDatabaseQuestions()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .concatMap(new Function<Boolean, ObservableSource<? extends Boolean>>() {
                    @Override
                    public ObservableSource<? extends Boolean> apply(Boolean aBoolean) throws Exception {
                        return getDataManager().seedDatabaseOptions();
                    }
                }).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }
                        decideNextActivity();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }
                        getMvpView().onError("Some Error Occurred！");
                        decideNextActivity();
                    }
                }));

    }

    private void decideNextActivity() {
        if (getDataManager().getCurrentUserLoggedInMode() ==
                DataManager.LoggedInMode.Logged_IN_MODE_LOGGED_OUT.getType()){
            getMvpView().openLoginActivity();
        } else {
            getMvpView().openMainActivity();
        }
    }
}
