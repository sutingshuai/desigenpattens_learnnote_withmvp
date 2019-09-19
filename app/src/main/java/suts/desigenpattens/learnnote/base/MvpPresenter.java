package suts.desigenpattens.learnnote.base;

import com.androidnetworking.error.ANError;

/**
 * Created by sutingshuai on 2019-08-26
 * Describe:
 */
public interface MvpPresenter<V extends MvpView> {
    void onAttach(V mvpView);
    void onDetach();
    void handleApiError(ANError error);
    void setUserAsLoggedOut();
}
