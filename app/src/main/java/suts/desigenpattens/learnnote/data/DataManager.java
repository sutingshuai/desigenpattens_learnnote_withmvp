package suts.desigenpattens.learnnote.data;


import io.reactivex.Observable;
import suts.desigenpattens.learnnote.data.db.DbHelper;
import suts.desigenpattens.learnnote.data.network.ApiHelper;
import suts.desigenpattens.learnnote.data.pref.PreferencesHelper;


/**
 * Created by sutingshuai on 2019-08-26
 * Describe:
 */
public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {

    public void updateApiHeader(Long userId, String accessToken);

    public void setUserAsLoggedOut();

    Observable<Boolean> seedDatabaseQuestions();

    Observable<Boolean> seedDatabaseOptions();

    void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath
    );

    public enum LoggedInMode{
        Logged_IN_MODE_LOGGED_OUT(0),
        Logged_IN_MODE_LOGGED_GOOGLE(1),
        Logged_IN_MODE_LOGGED_FB(2),
        Logged_IN_MODE_LOGGED_SERVER(3);

        int type;
        LoggedInMode(int type) {
            this.type = type;
        }
        public int getType() { return type; }

        public void setType(int type) { this.type = type; }
    }

}
