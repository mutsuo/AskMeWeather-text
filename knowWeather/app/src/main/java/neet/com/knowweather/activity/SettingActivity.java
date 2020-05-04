package neet.com.knowweather.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import neet.com.knowweather.R;
import neet.com.knowweather.SettingApps.UserSettingApplication;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton ibtMunu;
    private LinearLayout logined;
    private TextView personcenter;
    private TextView setting;
    private TextView unlock;
    private LinearLayout nologin;
    private TextView login;
    private TextView settingS;
    private boolean isLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initivew();
        showui();
    }

    void initivew(){
        ibtMunu = (ImageButton) findViewById(R.id.ibt_munu);
        logined = (LinearLayout) findViewById(R.id.logined);
        personcenter = (TextView) findViewById(R.id.personcenter);
        setting = (TextView) findViewById(R.id.setting);
        unlock = (TextView) findViewById(R.id.unlock);
        nologin = (LinearLayout) findViewById(R.id.nologin);
        login = (TextView) findViewById(R.id.login);
        settingS = (TextView) findViewById(R.id.setting_s);

        login.setOnClickListener(this);
        ibtMunu.setOnClickListener(this);
        personcenter.setOnClickListener(this);
        setting.setOnClickListener(this);
        unlock.setOnClickListener(this);
        settingS.setOnClickListener(this);
        isLogin=UserSettingApplication.sharedPreferences.getBoolean("isLogin",false);
    }
    //根据是否登录显示不同UI
    void showui(){
        if (isLogin){
            nologin.setVisibility(View.GONE);
            logined.setVisibility(View.VISIBLE);
        }else {
            nologin.setVisibility(View.VISIBLE);
            logined.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibt_munu:
                finish();
                break;
            case R.id.personcenter:
                Intent intent1 = new Intent(getApplicationContext(), PersonCenterActivity.class);
                startActivity(intent1);
                break;
            case R.id.setting:
                Intent intent2 = new Intent(getApplicationContext(), SettingIndexActivity.class);
                startActivity(intent2);
                break;
            case R.id.unlock:
                Intent intent3 = new Intent(getApplicationContext(), FullverActivity.class);
                startActivity(intent3);
                break;
            case R.id.login:
                Intent intent = new Intent(getApplicationContext(), LoginPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_s:
                Intent intent6 = new Intent(getApplicationContext(), SettingIndexActivity.class);
                startActivity(intent6);
                break;
        }
    }
}
