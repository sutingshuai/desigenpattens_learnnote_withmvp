package suts.desigenpattens.learnnote.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import suts.desigenpattens.learnnote.data.db.DbHelper;
import suts.desigenpattens.learnnote.data.db.model.Option;
import suts.desigenpattens.learnnote.data.db.model.Question;
import suts.desigenpattens.learnnote.data.db.model.User;
import suts.desigenpattens.learnnote.data.network.ApiHeader;
import suts.desigenpattens.learnnote.data.network.ApiHelper;
import suts.desigenpattens.learnnote.data.network.model.BlogResponse;
import suts.desigenpattens.learnnote.data.network.model.LogOutResonse;
import suts.desigenpattens.learnnote.data.network.model.LoginRequest;
import suts.desigenpattens.learnnote.data.network.model.LoginResponse;
import suts.desigenpattens.learnnote.data.network.model.OpenSourceResponse;
import suts.desigenpattens.learnnote.data.pref.PreferencesHelper;
import suts.desigenpattens.learnnote.di.ApplicationContext;
import suts.desigenpattens.learnnote.utils.AppConstants;
import suts.desigenpattens.learnnote.utils.CommonUtils;

/**
 * Created by sutingshuai on 2019-08-29
 * Describe:
 */
@Singleton
public class AppDataManager implements DataManager{

    private Context mContext;
    private DbHelper mDbHelper;
    private PreferencesHelper mPreferencesHelper;
    private ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context mContext, DbHelper mDbHelper, PreferencesHelper mPreferencesHelper, ApiHelper mApiHelper) {
        this.mContext = mContext;
        this.mDbHelper = mDbHelper;
        this.mPreferencesHelper = mPreferencesHelper;
        this.mApiHelper = mApiHelper;
    }

    @Override
    public void updateApiHeader(Long userId, String accessToken) {
        mApiHelper.getApiHeader().getProtectedApiHeader().setmUserId(userId);
        mApiHelper.getApiHeader().getProtectedApiHeader().setmAccessToken(accessToken);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(null,
                null,
                LoggedInMode.Logged_IN_MODE_LOGGED_OUT,
                null,
                null,
                null);
    }

    @Override
    public Observable<Boolean> seedDatabaseQuestions() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return mDbHelper.isQustionEmpty()
                .concatMap(isEmpty -> {
                    if (isEmpty){
                        Type type = $Gson$Types.
                                newParameterizedTypeWithOwner(null, List.class,
                                        Question.class);
                        List<Question> questionList = gson.fromJson(
                                CommonUtils.loadJSONFromAsset(mContext,
                                        AppConstants.SEED_DATABASE_QUESTIONS),
                                type);
                        return saveAllQuestions(questionList);
                    }
                    return Observable.just(false);
                });

    }

    @Override
    public Observable<Boolean> seedDatabaseOptions() {
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();

        return mDbHelper.isOptionEmpty()
                .concatMap(isEmpty -> {
                    if (isEmpty){
                        Type type = $Gson$Types
                                .newParameterizedTypeWithOwner(null, List.class,
                                        Option.class);
                        List<Option> optionList = gson.fromJson(
                                CommonUtils.loadJSONFromAsset(mContext,
                                        AppConstants.SEED_DATABASE_OPTIONS),
                                type);
                        return saveAllOptions(optionList);
                    }
                    return Observable.just(false);
                });
    }

    @Override
    public void updateUserInfo(String accessToken,
                               Long userId,
                               LoggedInMode loggedInMode,
                               String userName,
                               String email,
                               String profilePicPath) {
        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentUserEmail(email);
        setCurrentUserProfilePicUrl(profilePicPath);
    }

    @Override
    public Observable<Long> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return mDbHelper.getAllUsers();
    }

    @Override
    public Observable<List<Question>> getAllQuestions() {
        return mDbHelper.getAllQuestions();
    }

    @Override
    public Observable<Boolean> isOptionEmpty() {
        return mDbHelper.isOptionEmpty();
    }

    @Override
    public Observable<Boolean> isQustionEmpty() {
        return mDbHelper.isQustionEmpty();
    }

    @Override
    public Observable<Boolean> saveOption(Option option) {
        return mDbHelper.saveOption(option);
    }

    @Override
    public Observable<Boolean> saveQuestion(Question question) {
        return mDbHelper.saveQuestion(question);
    }

    @Override
    public Observable<Boolean> saveAllOptions(List<Option> options) {
        return mDbHelper.saveAllOptions(options);
    }

    @Override
    public Observable<Boolean> saveAllQuestions(List<Question> questions) {
        return mDbHelper.saveAllQuestions(questions);
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request) {
        return mApiHelper.doGoogleLoginApiCall(request);
    }

    @Override
    public Single<LoginResponse> doFaceBookLoginApiCall(LoginRequest.FaceBookLoginRequest request) {
        return mApiHelper.doFaceBookLoginApiCall(request);
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return mApiHelper.doServerLoginApiCall(request);
    }

    @Override
    public Single<LogOutResonse> doLogOutApiCall() {
        return mApiHelper.doLogOutApiCall();
    }

    @Override
    public Single<BlogResponse> doBlogApiCall() {
        return mApiHelper.doBlogApiCall();
    }

    @Override
    public Single<OpenSourceResponse> doOpenSourceResponseApiCall() {
        return mApiHelper.doOpenSourceResponseApiCall();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setmAccessToken(accessToken);
    }
}
