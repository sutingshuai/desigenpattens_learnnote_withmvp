package suts.desigenpattens.learnnote.base;

import androidx.annotation.StringRes;

/**
 * Created by sutingshuai on 2019-08-26
 * Describe: Mvp Base View
 */
public interface MvpView {
    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(@StringRes int resId);

    void showMessage(String message);

    boolean isNetworkConnected();

    void hidKeyBoard();
}
