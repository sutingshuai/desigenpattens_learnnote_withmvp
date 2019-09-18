package suts.desigenpattens.learnnote.base;

import com.androidnetworking.error.ANError;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import suts.desigenpattens.learnnote.data.DataManager;
import suts.desigenpattens.learnnote.utils.rx.SchedulerProvider;

/**
 * Created by sutingshuai on 2019-08-26
 * Describe:
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private static final String TAG = "basePresenter";

    private DataManager mDataManager;
    private SchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;

    private V mMvpView;

    @Inject
    public BasePresenter(DataManager mDataManager, SchedulerProvider mSchedulerProvider, CompositeDisposable mCompositeDisposable) {
        this.mDataManager = mDataManager;
        this.mSchedulerProvider = mSchedulerProvider;
        this.mCompositeDisposable = mCompositeDisposable;
    }


    @Override
    public void onAttach(V mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        mMvpView = null;
    }

    @Override
    public void handleApiError(ANError error) {

    }

    @Override
    public void setUserAsLogedOut() {
        getDataManager().setAccessToken(null);
    }

    public V getMvpView() {
        return mMvpView;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public static class MvpViewNotAttachException extends RuntimeException {
        public MvpViewNotAttachException() {
            super("Please call Presenter.onAttach(MvpView) Before Request Data to the Presenter");
        }
    }
}
