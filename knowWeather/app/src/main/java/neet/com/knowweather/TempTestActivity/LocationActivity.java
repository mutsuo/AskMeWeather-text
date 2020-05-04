package neet.com.knowweather.TempTestActivity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import neet.com.knowweather.R;
import neet.com.knowweather.tool.MyLocationListener;

public class LocationActivity extends AppCompatActivity {
    private Button btn1;
    public LocationClient mLocationClient =null;
    private MyLocationListener myListener = new MyLocationListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        btn1 = (Button) findViewById(R.id.btn1);
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        final BDLocation[] lastKnownLocation = new BDLocation[1];
        mLocationClient.setLocOption(option);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationClient.start();
                 btn1.setText(myListener.getCity());
            }
        });

    }
}
