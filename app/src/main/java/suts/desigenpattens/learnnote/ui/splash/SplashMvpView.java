package suts.desigenpattens.learnnote.ui.splash;

import suts.desigenpattens.learnnote.base.MvpView;

/**
 * Created by sutingshuai on 2019-09-11
 * Describe:
 */
public interface SplashMvpView extends MvpView {

    void openMainActivity();

    void openLoginActivity();

    void startSyncService();
}
