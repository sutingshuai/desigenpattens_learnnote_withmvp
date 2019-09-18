package suts.desigenpattens.learnnote.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import butterknife.Unbinder;
import suts.desigenpattens.learnnote.R;
import suts.desigenpattens.learnnote.di.component.ActivityComponent;
import suts.desigenpattens.learnnote.di.component.DaggerActivityComponent;
import suts.desigenpattens.learnnote.di.module.ActivityModule;
import suts.desigenpattens.learnnote.ui.login.LoginActivity;
import suts.desigenpattens.learnnote.utils.CommonUtils;
import suts.desigenpattens.learnnote.utils.NetworkUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by sutingshuai on 2019-08-30
 * Describe:
 */
public abstract class BaseActivity extends AppCompatActivity implements MvpView {  //TODO BaseFragment.Callback

    private ProgressDialog mProgressDialog;

    private ActivityComponent mActivityComponent;

    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((MvpApp)getApplication()).getComponent())
                .build();
    }

    public ActivityComponent getActivityComponent(){
        return mActivityComponent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showProgressDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog!=null&&mProgressDialog.isShowing()){
            mProgressDialog.cancel();
        }
    }

    @Override
    public void onError(int resId) {
        showSnackBar(getString(resId));
    }

    @Override
    public void onError(String message) {
        if (message!=null){
            showSnackBar(message);
        } else {
            showSnackBar("Some Error Occurred！");   //TODO string文件
        }
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Some Error Occurred！", Toast.LENGTH_SHORT).show();  //TODO string文件
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void openActivityOnTokenExpire() {
        startActivity(LoginActivity.getStartIntent(this));
        finish();
    }

    @Override
    public void hidKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showSnackBar(String message){
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),  // TODO androidx找不到 android.support.design.R.id.snackbar_text 直接用 snackbar.getView().findViewById(R.id.snackbar_text);
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        snackbar.show();
    }

    public void setUnbinder(Unbinder unbinder) {
        this.mUnBinder = unbinder;
    }

    @Override
    protected void onDestroy() {

        if (mUnBinder != null){
            mUnBinder.unbind();
        }

        super.onDestroy();
    }

    protected abstract void setUp();
}
