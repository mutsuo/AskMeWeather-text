package neet.com.knowweather.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Calendar;

import neet.com.knowweather.R;
import neet.com.knowweather.bean.OrderRecord;
import neet.com.knowweather.tool.RoundCheckBox;

public class BuyActivity extends AppCompatActivity {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private RoundCheckBox chekPay;
    private RadioButton rbWc;
    private RadioButton rbPay;
    private TextView tvMoney;
    private TextView tvTime;
    private Button btnUnlock;
    private Button btnNo;
    private Calendar instance;
    private OrderRecord orderRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        initview();
    }

    void initview(){
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        chekPay = (RoundCheckBox) findViewById(R.id.chek_pay);
        rbWc = (RadioButton) findViewById(R.id.rb_wc);
        rbPay = (RadioButton) findViewById(R.id.rb_pay);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        tvTime = (TextView) findViewById(R.id.tv_time);
        btnUnlock = (Button) findViewById(R.id.btn_unlock);
        btnNo = (Button) findViewById(R.id.btn_no);
        orderRecord=new OrderRecord();
        btnUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送请求
                Intent intent = new Intent(getApplicationContext(), PersonCenterActivity.class);
                startActivity(intent);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送请求
                Intent intent = new Intent(getApplicationContext(), PersonCenterActivity.class);
                startActivity(intent);
            }
        });
        chekPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chekPay.isChecked()){
                    //改变属性值
                }
            }
        });
        rbWc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //改变属性值
            }
        });
        rbPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //改变属性值
            }
        });
        instance = Calendar.getInstance();
        instance.add(Calendar.MONTH,1);
        int month=instance.get(Calendar.MONTH)+1;
        int day = instance.get(Calendar.DAY_OF_MONTH);
        int year = instance.get(Calendar.YEAR);
        btn1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_buy_c));
        btn1.setEnabled(false);
        tvMoney.setText("¥5");
        tvTime.setText(year+"年"+month+"月"+day+"日");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setEnabled(false);
                btn2.setEnabled(true);
                btn3.setEnabled(true);
                btn1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_buy_c));
                btn2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_buy_un));
                btn3.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_buy_un));
                instance = Calendar.getInstance();
                instance.add(Calendar.MONTH,1);
                int month=instance.get(Calendar.MONTH)+1;
                int day = instance.get(Calendar.DAY_OF_MONTH);
                int year = instance.get(Calendar.YEAR);
                tvMoney.setText("¥5");
                tvTime.setText(year+"年"+month+"月"+day+"日");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.setEnabled(false);
                btn1.setEnabled(true);
                btn3.setEnabled(true);
                btn2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_buy_c));
                btn1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_buy_un));
                btn3.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_buy_un));
                instance = Calendar.getInstance();
                instance.add(Calendar.MONTH,3);
                int month=instance.get(Calendar.MONTH)+1;
                int day = instance.get(Calendar.DAY_OF_MONTH);
                int year = instance.get(Calendar.YEAR);
                tvMoney.setText("¥12");
                tvTime.setText(year+"年"+month+"月"+day+"日");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn3.setEnabled(false);
                btn2.setEnabled(true);
                btn1.setEnabled(true);
                btn3.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_buy_c));
                btn2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_buy_un));
                btn1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bt_buy_un));
                instance = Calendar.getInstance();
                instance.add(Calendar.MONTH,12);
                int month=instance.get(Calendar.MONTH)+1;
                int day = instance.get(Calendar.DAY_OF_MONTH);
                int year = instance.get(Calendar.YEAR);
                tvMoney.setText("¥36");
                tvTime.setText(year+"年"+month+"月"+day+"日");
            }
        });
    }

}
