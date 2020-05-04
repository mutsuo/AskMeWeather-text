package neet.com.knowweather.activity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import neet.com.knowweather.R;
import neet.com.knowweather.SettingApps.UserSettingApplication;
import neet.com.knowweather.tool.ActivityCollector;

public class WaitingActivity extends AppCompatActivity {
    private UserSettingApplication userSettingApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        ActivityCollector.addActivity(this);
        userSettingApplication = (UserSettingApplication) getApplication();
        //进入检查网络状态

        if(isNetworkConnected(getApplicationContext())){
            //访问服务器成功进入主页，失败进入失败主页
            //后期添加
            Intent intent2 = new Intent(getApplicationContext(), IndexActivity.class);
            intent2.putExtra("temperature","26");
            intent2.putExtra("tempdiff","26-30");
            intent2.putExtra("weather","PartlyCloudy");
            startActivity(intent2);
            ActivityCollector.finishAll();
        }else{
            Intent intent = new Intent(getApplicationContext(), IndexNA.class);
            startActivity(intent);
            ActivityCollector.finishAll();
        }
    }
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
