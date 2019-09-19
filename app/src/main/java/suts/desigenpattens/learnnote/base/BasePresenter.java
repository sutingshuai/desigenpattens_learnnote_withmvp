package suts.desigenpattens.learnnote.base;

import android.util.Log;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import suts.desigenpattens.learnnote.R;
import suts.desigenpattens.learnnote.data.DataManager;
import suts.desigenpattens.learnnote.data.network.model.ApiError;
import suts.desigenpattens.learnnote.utils.AppConstants;
import suts.desigenpattens.learnnote.utils.rx.SchedulerProvider;

/**
 * Created by sutingshuai on 2019-08-26
 * Describe:Presenter的基类：BasePresenter
 * need：DataManager SchedulerProvider CompositeDisposable
 * method：void onAttach(V mvpView) <<<<<implements>>>>>
 *         void onDetach() <<<<<implements>>>>>
 *         void isViewAttached()
 *         V getMvpView()
 *         void checkViewAttached()
 *         DataManager getDataManager()
 *         SchedulerProvider getSchedulerProvider()
 *         CompositeDisposable getCompositeDisposable()
 *         void handleApiError(ANError error) <<<<<implements>>>>>
 *         void setUserAsLoggedOut() <<<<<implements>>>>>
 * static inner class：static class MvpViewNotAttachedException
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

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
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

    @Override
    public void handleApiError(ANError error) {
        if (error == null || error.getErrorBody() == null) {
            getMvpView().onError(R.string.api_default_error);
            return;
        }

        if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
                && error.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
            getMvpView().onError(R.string.connection_error);
            return;
        }

        if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
                && error.getErrorDetail().equals(ANConstants.REQUEST_CANCELLED_ERROR)) {
            getMvpView().onError(R.string.api_retry_error);
            return;
        }

        final GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = gsonBuilder.create();

        try {
            ApiError apiError = gson.fromJson(error.getErrorBody(), ApiError.class);

            if (apiError == null || apiError.getMessage() == null){
                getMvpView().onError(R.string.api_default_error);
                return;
            }

            switch (apiError.getErrorCode()) {
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                case HttpURLConnection.HTTP_FORBIDDEN:
                    setUserAsLoggedOut();
                    getMvpView().openActivityOnTokenExpire();
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                case HttpURLConnection.HTTP_NOT_FOUND:
                default:
                    getMvpView().onError(apiError.getMessage());
            }

        } catch (JsonSyntaxException | NullPointerException e) {
            Log.e(TAG, "handleApiError", e);
            getMvpView().onError(R.string.api_default_error);
        }
    }

    @Override
    public void setUserAsLoggedOut() {
        getDataManager().setAccessToken(null);
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
