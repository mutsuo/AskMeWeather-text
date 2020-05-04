package neet.com.knowweather.activity;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;

import neet.com.knowweather.R;

public class VersionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CardView cvRenew;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        initView();
    }
    void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cvRenew = (CardView) findViewById(R.id.cv_renew);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//主键按钮能否可点击x
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回图标
    }

}
