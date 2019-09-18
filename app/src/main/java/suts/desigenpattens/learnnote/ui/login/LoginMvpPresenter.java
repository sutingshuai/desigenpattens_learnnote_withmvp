package suts.desigenpattens.learnnote.ui.login;
import suts.desigenpattens.learnnote.base.MvpPresenter;
import suts.desigenpattens.learnnote.base.MvpView;
import suts.desigenpattens.learnnote.di.PerActivity;

/**
 * Created by sutingshuai on 2019-09-06
 * Describe:
 */
@PerActivity
public interface LoginMvpPresenter<V extends MvpView> extends MvpPresenter<V> {

    void onServerLoginClick(String email, String password);

    void onGoogleLoginClick();

    void onFacebookLoginClick();

}
