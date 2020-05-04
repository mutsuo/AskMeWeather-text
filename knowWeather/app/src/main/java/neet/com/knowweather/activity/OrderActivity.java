package neet.com.knowweather.activity;


import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import android.widget.TextView;
import android.widget.Toast;

import neet.com.knowweather.R;
import neet.com.knowweather.bean.OrderRecord;

public class OrderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvTime;
    private TextView tvCost;
    private TextView paytype;
    private TextView payid;
    private TextView paytime;
    private TextView type;
    private TextView endtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initview();

    }
    void initview(){
        paytime = (TextView) findViewById(R.id.paytime);
        type = (TextView) findViewById(R.id.type);
        endtime = (TextView) findViewById(R.id.endtime);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvCost = (TextView) findViewById(R.id.tv_cost);
        paytype = (TextView) findViewById(R.id.paytype);
        payid = (TextView) findViewById(R.id.payid);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//主键按钮能否可点击x
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回图标
        OrderRecord orderRecord = (OrderRecord) getIntent().getSerializableExtra("order");
        if (orderRecord.getMid()==1){
            type.setText("¥5*1个月");
            tvTime.setText("会员一个月");
            tvCost.setText("¥5");
        }else if(orderRecord.getMid()==2){
            type.setText("¥4*3个月");
            tvTime.setText("会员三个月");
            tvCost.setText("¥12");
        }else {
            type.setText("¥3*12个月");
            tvTime.setText("会员一年");
            tvCost.setText("¥36");
        }
        if (orderRecord.getPayWay()==1){
            paytype.setText("支付宝");
        }else {
            paytype.setText("微信");
        }
        payid.setText(orderRecord.getId()+"");
        paytime.setText(orderRecord.getCreateTime());
        endtime.setText(orderRecord.getEndTime());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
