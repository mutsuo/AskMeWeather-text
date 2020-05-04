package neet.com.knowweather.activity;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import neet.com.knowweather.R;
import neet.com.knowweather.SettingApps.UserSettingApplication;

public class SettingIndexActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvCity;
    private TextView phone;
    private ImageView ivCity;
    private RelativeLayout version;
    private RelativeLayout suggestion;
    private UserSettingApplication userSettingApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_index);
        initView();
        setListener();
    }
    void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvCity = (TextView) findViewById(R.id.tv_city);
        phone = (TextView) findViewById(R.id.phone);
        ivCity = (ImageView) findViewById(R.id.iv_city);
        version = (RelativeLayout) findViewById(R.id.version);
        suggestion = (RelativeLayout) findViewById(R.id.suggestion);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//主键按钮能否可点击x
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回图标
        //进入页页面有从用户信息中获取城市信息
        userSettingApplication=(UserSettingApplication) getApplication();
    }

    void setListener(){
        ivCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CitySelectActivity.class);
                startActivity(intent);
            }
        });
        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VersionActivity.class);
                startActivity(intent);
            }
        });
        suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String city = UserSettingApplication.sharedPreferences.getString("city","默认");
        phone.setText(city);
    }
}
