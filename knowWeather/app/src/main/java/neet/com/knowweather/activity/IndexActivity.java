package neet.com.knowweather.activity;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Calendar;

import neet.com.knowweather.R;
import neet.com.knowweather.SettingApps.UserSettingApplication;
import neet.com.knowweather.tool.ActivityCollector;
import neet.com.knowweather.tool.MyLocationListener;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout indexBg;
    private ImageButton ibtMunu;
    private TextView tvTime;
    private TextView city;
    private RelativeLayout rvTemperature;
    private TextView tvTemperature;
    private TextView tvTempdiff;
    private ImageView ivToask;
    private String temperature;
    private String tempdiff;
    private String weather;
    private int bgcolor;
    private int fColor;
    private TextView wea;
    private TextView signel;
    private SmartRefreshLayout smartRefreshLayout;
    public LocationClient mLocationClient =null;
    private BDAbstractLocationListener myListener;
    private String tweather;
    private String mcity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        findview();
        getweather();
    }
    void findview(){
        indexBg = (RelativeLayout) findViewById(R.id.index_bg);
        ibtMunu = (ImageButton) findViewById(R.id.ibt_munu);
        tvTime = (TextView) findViewById(R.id.tv_time);
        city = (TextView) findViewById(R.id.city);
        rvTemperature = (RelativeLayout) findViewById(R.id.rv_temperature);
        tvTemperature = (TextView) findViewById(R.id.tv_temperature);
        tvTempdiff = (TextView) findViewById(R.id.tv_tempdiff);
        ivToask = (ImageView) findViewById(R.id.iv_toask);
        wea=findViewById(R.id.weather);
        signel=findViewById(R.id.signel);
        smartRefreshLayout=findViewById(R.id.refreshLayout);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                reflush();
            }
        });
        ibtMunu.setOnClickListener(this);
        ivToask.setOnClickListener(this);
        myListener=new BDAbstractLocationListener(){
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                mcity = bdLocation.getCity();

                mLocationClient.stop();
            }
        };

    }

    void reflush(){
        //从服务器获取天气信息，从新更新UI
        smartRefreshLayout.finishRefresh(2000/*,false*/);
    }

    /**
     * 该方法为无服务器时的测试用方法
     */
    void getweather(){
        Intent intent = getIntent();
        //后续封装成天气对象
        temperature = intent.getStringExtra("temperature");
        tempdiff = intent.getStringExtra("tempdiff");
        tweather = intent.getStringExtra("weather");
        setIndex();
    }

    /**
     * 从服务器得到数据方法
     */
    void getWeatherByServer(){

    }
    void setIndex(){
        int weatherId = getResources().getIdentifier( tweather, "string", "neet.com.knowweather");
        int bgColorId =getResources().getIdentifier( tweather, "color", "neet.com.knowweather");
        int tColorId =getResources().getIdentifier( tweather+"_f", "color", "neet.com.knowweather");
        if(weatherId!=0&&bgColorId!=0&&tColorId!=0){
            weather=getResources().getString(weatherId);
            bgcolor=getResources().getColor(bgColorId);
            fColor=getResources().getColor(tColorId);
            indexBg.setBackgroundColor(bgcolor);
            tvTime.setTextColor(fColor);
            city.setTextColor(fColor);
            tvTemperature.setTextColor(fColor);
            tvTemperature.setText(temperature);
            tvTempdiff.setText(tempdiff);
            tvTempdiff.setTextColor(fColor);
            wea.setTextColor(fColor);
            wea.setText(weather);
            signel.setTextColor(fColor);
            Calendar instance = Calendar.getInstance();
            int month=instance.get(Calendar.MONTH)+1;
            int day = instance.get(Calendar.DAY_OF_MONTH);
            int week=instance.get(Calendar.DAY_OF_WEEK);
            tvTime.setText(month+"月"+day+"日"+"("+week+")");
            //city.setText(getLocation());
            getLocation();
            city.setText(mcity);
            Toast.makeText(getApplicationContext(),mcity,Toast.LENGTH_LONG).show();
            //mLocationClient.stop();
        }else {
            Intent intent1 = new Intent(getApplicationContext(), IndexNA.class);
            startActivity(intent1);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibt_munu:
                //跳转到设置菜单
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_toask:
                //跳转到聊天界面
                Intent intent2 = new Intent(getApplicationContext(), IMWeatherAskActivity.class);
                startActivity(intent2);
                break;
        }
    }
    //获得定位信息
    void getLocation(){
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    public void onBackPressed() {
        //如果登录就直接退出，否则回到欢迎页
        if(!UserSettingApplication.sharedPreferences.getBoolean("isLogin",false)){
            Intent intent = new Intent(getApplicationContext(), welcomeActivity.class);
            startActivity(intent);
        }
        else {
            System.exit(0);
        }
    }
}
