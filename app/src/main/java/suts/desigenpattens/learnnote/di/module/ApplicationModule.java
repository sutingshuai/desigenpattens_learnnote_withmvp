package suts.desigenpattens.learnnote.di.module;

import android.app.Application;
import android.content.Context;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import suts.desigenpattens.learnnote.BuildConfig;
import suts.desigenpattens.learnnote.R;
import suts.desigenpattens.learnnote.data.AppDataManager;
import suts.desigenpattens.learnnote.data.DataManager;
import suts.desigenpattens.learnnote.data.db.AppDbHelper;
import suts.desigenpattens.learnnote.data.db.DbHelper;
import suts.desigenpattens.learnnote.data.network.ApiHeader;
import suts.desigenpattens.learnnote.data.network.ApiHelper;
import suts.desigenpattens.learnnote.data.network.AppApiHelper;
import suts.desigenpattens.learnnote.data.pref.AppPreferenceHelper;
import suts.desigenpattens.learnnote.data.pref.PreferencesHelper;
import suts.desigenpattens.learnnote.di.ApiInfo;
import suts.desigenpattens.learnnote.di.ApplicationContext;
import suts.desigenpattens.learnnote.di.DatabaseInfo;
import suts.desigenpattens.learnnote.di.PreferenceInfo;
import suts.desigenpattens.learnnote.utils.AppConstants;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by sutingshuai on 2019-08-28
 * Describe:
 */
@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    public Context provideContext(){
        return this.application;
    }

    @Provides
    public Application provideApplication(){
        return this.application;
    }

    @Provides
    @DatabaseInfo
    public String provideDatabaseName(){
        return AppConstants.DB_NAME;
    }

    @Provides
    @ApiInfo
    public String provideApiKey(){
        return BuildConfig.API_KEY;
    }

    @Provides
    @PreferenceInfo
    public String providePreferenceName(){
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    public DataManager provideDataManager(AppDataManager appDataManager){
        return appDataManager;
    }

    @Provides
    @Singleton
    public ApiHelper provideApiHelper(AppApiHelper appApiHelper){
        return appApiHelper;
    }

    @Provides
    @Singleton
    public DbHelper provideDbHelper(AppDbHelper appDbHelper){
        return appDbHelper;
    }

    @Provides
    @Singleton
    public PreferencesHelper providePreferencesHelper(AppPreferenceHelper appPreferenceHelper){
        return appPreferenceHelper;
    }

    @Provides
    @Singleton
    public ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey, AppPreferenceHelper appPreferenceHelper){
        return new ApiHeader.ProtectedApiHeader(apiKey,
                appPreferenceHelper.getCurrentUserId(),
                appPreferenceHelper.getAccessToken());
    }

    @Provides
    @Singleton
    public CalligraphyConfig provideCalligrapthyConfig(){
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

}
