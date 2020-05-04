package neet.com.knowweather;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import neet.com.knowweather.SettingApps.UserSettingApplication;
import neet.com.knowweather.activity.PersonCenterActivity;
import neet.com.knowweather.activity.WaitingActivity;
import neet.com.knowweather.activity.welcomeActivity;
import neet.com.knowweather.bean.OrderRecord;
import neet.com.knowweather.tool.ActivityCollector;

public class MainActivity extends AppCompatActivity {
    private Button btn1;
    private Button btn2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCollector.addActivity(this);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        boolean isLogin = UserSettingApplication.sharedPreferences.getBoolean("isLogin",false);
        boolean isFirst = UserSettingApplication.sharedPreferences.getBoolean("isFirst",true);
        if (isFirst||!isLogin){
            Intent intent = new Intent(getApplicationContext(), welcomeActivity.class);
            startActivity(intent);
        }else{
            Intent intent1 = new Intent(getApplicationContext(), WaitingActivity.class);
            startActivity(intent1);
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), welcomeActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), PersonCenterActivity.class);
                startActivity(intent2);
            }
        });
    }
}
