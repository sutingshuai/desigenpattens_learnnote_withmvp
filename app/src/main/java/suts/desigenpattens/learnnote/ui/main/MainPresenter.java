package suts.desigenpattens.learnnote.ui.main;

import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import suts.desigenpattens.learnnote.base.BasePresenter;
import suts.desigenpattens.learnnote.data.DataManager;
import suts.desigenpattens.learnnote.utils.AESUtils;
import suts.desigenpattens.learnnote.utils.rx.SchedulerProvider;

/**
 * Created by sutingshuai on 2019-09-20
 * Describe:
 */
public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    @Inject
    public MainPresenter(DataManager mDataManager,
                         SchedulerProvider mSchedulerProvider,
                         CompositeDisposable mCompositeDisposable) {
        super(mDataManager, mSchedulerProvider, mCompositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        String sOri = "123456";
        Log.e("MainPresenter", "onAttach:ori str is: " + sOri);
        String aesBefore = AESUtils.encrypt(sOri);
        Log.e("MainPresenter", "onAttach:ori aesBefore str is: " + aesBefore);
        String aesAfter = AESUtils.decrypt(aesBefore);
        Log.e("MainPresenter", "onAttach:ori aesAfter str is: " + aesAfter);
    }

    @Override
    public void onDrawerOptionAboutClick() {
        getMvpView().showMessage("About");
    }

    @Override
    public void onDrawerOptionLogoutClick() {
        getMvpView().showMessage("Logout");
    }

    @Override
    public void onDrawerRateUsClick() {
        getMvpView().showMessage("RateUs");
    }

    @Override
    public void onDrawerMyFeedClick() {
        getMvpView().showMessage("Feed");
    }

    @Override
    public void onViewInitialized() {

    }

    @Override
    public void onCardExhausted() {

    }

    @Override
    public void onNavMenuCreated() {

    }
}
