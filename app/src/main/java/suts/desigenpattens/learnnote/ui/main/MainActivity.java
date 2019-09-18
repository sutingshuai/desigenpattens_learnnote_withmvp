package suts.desigenpattens.learnnote.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import suts.desigenpattens.learnnote.R;
import suts.desigenpattens.learnnote.base.BaseActivity;

import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    public static Intent getStartIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void setUp() {

    }

}
