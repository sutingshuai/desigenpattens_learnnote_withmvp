package suts.desigenpattens.learnnote.ui.main;

import java.util.List;

import suts.desigenpattens.learnnote.base.MvpView;
import suts.desigenpattens.learnnote.data.db.model.Question;

/**
 * Created by sutingshuai on 2019-09-20
 * Describe:
 */
public interface MainMvpView extends MvpView {

    void openLoginActivity();

    void showAboutFragment();

    void refreshQuestionnaire(List<Question> questionList);

    void reloadQuestionnaire(List<Question> questionList);

    void updateUserName(String currentUserName);

    void updateUserEmail(String currentUserEmail);

    void updateUserProfilePic(String currentUserProfilePicUrl);

    void updateAppVersion();

    void showRateUsDialog();

    void openMyFeedActivity();

    void closeNavigationDrawer();

    void lockDrawer();

    void unlockDrawer();
}
