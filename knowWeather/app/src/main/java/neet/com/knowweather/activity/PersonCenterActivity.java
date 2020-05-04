package neet.com.knowweather.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import neet.com.knowweather.R;
import neet.com.knowweather.SettingApps.UserSettingApplication;

public class PersonCenterActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView username;
    private TextView userid;
    private CardView cvOnclock;
    private TextView tvOnclock;
    private CardView cvButdata;
    private TextView tvBydata;
    private ImageButton ibUserMsg;
    private RelativeLayout rvpcmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
        initView();
        setListener();
    }

    void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        username = (TextView) findViewById(R.id.username);
        userid = (TextView) findViewById(R.id.userid);
        cvOnclock = (CardView) findViewById(R.id.cv_onclock);
        tvOnclock = (TextView) findViewById(R.id.tv_onclock);
        cvButdata = (CardView) findViewById(R.id.cv_butdata);
        tvBydata = (TextView) findViewById(R.id.tv_bydata);
        rvpcmsg=findViewById(R.id.rv_pmsg);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//主键按钮能否可点击x
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回图标
        ibUserMsg=findViewById(R.id.ib_usermsg);
        username.setText(UserSettingApplication.sharedPreferences.getString("name","默认"));
        userid.setText("uid:"+UserSettingApplication.sharedPreferences.getInt("id",0));
    }

    void setListener(){
        rvpcmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonMsgActivity.class);
                startActivity(intent);
            }
        });
        cvOnclock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FullverActivity.class);
                startActivity(intent);
            }
        });
        cvButdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        username.setText(UserSettingApplication.sharedPreferences.getString("name","默认"));
    }
}
