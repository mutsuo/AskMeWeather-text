package neet.com.knowweather.SettingApps;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import neet.com.knowweather.activity.WaitingActivity;
import neet.com.knowweather.activity.welcomeActivity;
import neet.com.knowweather.bean.User;

/**
 * 保存全部设置信息
 * 登录信息
 */
public class UserSettingApplication extends Application {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5cbec438");
        editor = sharedPreferences.edit();
//        editor.putBoolean("isLogin",false);
//        editor.putBoolean("isFirst",true);
//        //editor.apply();
//        editor.commit();
//        boolean isLogin = sharedPreferences.getBoolean("isLogin",false);
//        boolean isFirst = sharedPreferences.getBoolean("isFirst",true);
//        if (isFirst||!isLogin){
//            Intent intent = new Intent(getApplicationContext(), welcomeActivity.class);
//            startActivity(intent);
//        }else{
//            Intent intent1 = new Intent(getApplicationContext(), WaitingActivity.class);
//            startActivity(intent1);
//        }
    }
    public void setIsLogin(Boolean isLogin){
        editor.putBoolean("isLogin",isLogin);
        //editor.apply();
        editor.commit();
    }
    public void setIsFirst(Boolean ifFirst){
        editor.putBoolean("isFirst",ifFirst);
        //editor.apply();
        editor.commit();
    }
    public void setCity(String city){
        editor.putString("city",city);
        //editor.apply();
        editor.commit();
    }
    public void setUser(User user){
        editor.putInt("id",user.getId());
        editor.putInt("vip",user.getVip());
        editor.putString("phone",user.getPhone());
        editor.putString("birthady",user.getBirthady());
        editor.putString("city",user.getCity());
        editor.putString("name",user.getNickName());
        //editor.apply();
        editor.commit();
    }

}
