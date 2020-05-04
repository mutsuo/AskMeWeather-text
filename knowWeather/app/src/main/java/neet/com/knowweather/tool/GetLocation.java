package neet.com.knowweather.tool;

import android.content.Context;

import com.baidu.location.LocationClient;

public class GetLocation {
    private  LocationClient mLocationClient =null;
    private  MyLocationListener myListener = new MyLocationListener();
    public GetLocation(Context context){
        mLocationClient = new LocationClient(context);
    }
}
