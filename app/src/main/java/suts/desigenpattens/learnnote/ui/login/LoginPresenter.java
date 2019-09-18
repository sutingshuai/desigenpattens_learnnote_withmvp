package suts.desigenpattens.learnnote.ui.login;

import com.androidnetworking.error.ANError;
import suts.desigenpattens.learnnote.base.BasePresenter;
import suts.desigenpattens.learnnote.data.DataManager;
import suts.desigenpattens.learnnote.data.network.model.LoginRequest;
import suts.desigenpattens.learnnote.data.network.model.LoginResponse;
import suts.desigenpattens.learnnote.utils.CommonUtils;
import suts.desigenpattens.learnnote.utils.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by sutingshuai on 2019-09-06
 * Describe:
 */
public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {
    private static final String TAG = "LoginPresenter";

    @Inject
    public LoginPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onServerLoginClick(String email, String password) {
        if (email == null || email.isEmpty()) {
            getMvpView().onError("Please provide a non empty email.");
            return;
        }
        if (!CommonUtils.isEmailValid(email)) {
            getMvpView().onError("Please provide a valid email.");
            return;
        }
        if (password == null || password.isEmpty()) {
            getMvpView().onError("Please provide a non empty password.");
            return;
        }

        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .doServerLoginApiCall(new LoginRequest.ServerLoginRequest(email, password))
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Exception {
                        getDataManager().updateUserInfo(
                                loginResponse.getAccessToken(),
                                loginResponse.getUserId(),
                                DataManager.LoggedInMode.Logged_IN_MODE_LOGGED_SERVER,
                                loginResponse.getUserName(),
                                loginResponse.getUserEmail(),
                                loginResponse.getServerProfilePicUrl());

                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();
                        getMvpView().openMainActivity();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }

    @Override
    public void onGoogleLoginClick() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .doGoogleLoginApiCall(new LoginRequest.GoogleLoginRequest("test1", "test2"))
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Exception {
                        getDataManager().updateUserInfo(
                                loginResponse.getAccessToken(),
                                loginResponse.getUserId(),
                                DataManager.LoggedInMode.Logged_IN_MODE_LOGGED_GOOGLE,
                                loginResponse.getUserName(),
                                loginResponse.getUserEmail(),
                                loginResponse.getServerProfilePicUrl());

                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();
                        getMvpView().openMainActivity();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }

    @Override
    public void onFacebookLoginClick() {
        getMvpView().showMessage("onFacebookLoginClick");
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .doFaceBookLoginApiCall(new LoginRequest.FaceBookLoginRequest("test3", "test4"))
                .delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(getSchedulerProvider().ui())
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Exception {
                        getDataManager().updateUserInfo(
                                loginResponse.getAccessToken(),
                                loginResponse.getUserId(),
                                DataManager.LoggedInMode.Logged_IN_MODE_LOGGED_FB,
                                loginResponse.getUserName(),
                                loginResponse.getUserEmail(),
                                loginResponse.getFbProfilePicUrl());

                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();
                        getMvpView().openMainActivity();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }
}
