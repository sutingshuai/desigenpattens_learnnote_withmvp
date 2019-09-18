package suts.desigenpattens.learnnote.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suts.desigenpattens.learnnote.R;
import suts.desigenpattens.learnnote.base.BaseActivity;
import suts.desigenpattens.learnnote.ui.main.MainActivity;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginMvpPresenter<LoginMvpView> loginPresenter;

    @BindView(R.id.btn_server_login)
    Button btnServerLogin;

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUnbinder(ButterKnife.bind(this));

        getActivityComponent().inject(this);

        loginPresenter.onAttach(LoginActivity.this);
    }

    @Override
    protected void setUp() {

    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }


    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_server_login)
    public void onServerLoginClicked() {
        loginPresenter.onServerLoginClick(etEmail.getText().toString(), etPassword.getText().toString());
    }

    @OnClick(R.id.ib_google_login)
    public void onGoogleLoginClicked() {
        loginPresenter.onGoogleLoginClick();
    }

    @OnClick(R.id.ib_fb_login)
    public void onFbLoginClicked() {
        loginPresenter.onFacebookLoginClick();
    }
}
