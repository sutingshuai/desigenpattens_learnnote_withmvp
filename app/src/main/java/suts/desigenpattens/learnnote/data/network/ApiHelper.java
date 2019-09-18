package suts.desigenpattens.learnnote.data.network;


import io.reactivex.Single;
import suts.desigenpattens.learnnote.data.network.model.BlogResponse;
import suts.desigenpattens.learnnote.data.network.model.LogOutResonse;
import suts.desigenpattens.learnnote.data.network.model.LoginRequest;
import suts.desigenpattens.learnnote.data.network.model.LoginResponse;
import suts.desigenpattens.learnnote.data.network.model.OpenSourceResponse;

/**
 * Created by sutingshuai on 2019-08-29
 * Describe:
 */
public interface ApiHelper {

    public ApiHeader getApiHeader();

    public Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request);

    public Single<LoginResponse> doFaceBookLoginApiCall(LoginRequest.FaceBookLoginRequest request);

    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);

    public Single<LogOutResonse> doLogOutApiCall();

    public Single<BlogResponse> doBlogApiCall();

    public Single<OpenSourceResponse> doOpenSourceResponseApiCall();
}
