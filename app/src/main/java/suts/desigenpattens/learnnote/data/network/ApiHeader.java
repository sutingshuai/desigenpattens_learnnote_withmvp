package suts.desigenpattens.learnnote.data.network;


import javax.inject.Inject;
import javax.inject.Singleton;

import suts.desigenpattens.learnnote.di.ApiInfo;

/**
 * Created by sutingshuai on 2019-08-29
 * Describe:
 */
@Singleton
public class ApiHeader {

    private PublicApiHeader publicApiHeader;
    private ProtectedApiHeader protectedApiHeader;

    @Inject
    public ApiHeader(PublicApiHeader publicApiHeader, ProtectedApiHeader protectedApiHeader) {
        this.publicApiHeader = publicApiHeader;
        this.protectedApiHeader = protectedApiHeader;
    }

    public PublicApiHeader getPublicApiHeader(){
        return this.publicApiHeader;
    }

    public ProtectedApiHeader getProtectedApiHeader(){
        return this.protectedApiHeader;
    }

    public static class PublicApiHeader{
        private String apiKey;

        @Inject
        public PublicApiHeader(@ApiInfo String apiKey) {
            this.apiKey = apiKey;
        }

        public String getApiKey() {
            return apiKey;
        }
        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }

    public static class ProtectedApiHeader{
        String mApiKey;
        Long mUserId;
        String mAccessToken;

        @Inject
        public ProtectedApiHeader(String mApiKey, Long mUserId, String mAccessToken) {
            this.mApiKey = mApiKey;
            this.mUserId = mUserId;
            this.mAccessToken = mAccessToken;
        }

        public String getmApiKey() {
            return mApiKey;
        }
        public void setmApiKey(String mApiKey) {
            this.mApiKey = mApiKey;
        }
        public Long getmUserId() {
            return mUserId;
        }
        public void setmUserId(Long mUserId) {
            this.mUserId = mUserId;
        }
        public String getmAccessToken() {
            return mAccessToken;
        }
        public void setmAccessToken(String mAccessToken) {
            this.mAccessToken = mAccessToken;
        }
    }
}
