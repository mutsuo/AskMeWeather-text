package neet.com.knowweather.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import neet.com.knowweather.R;

public class FullverActivity extends AppCompatActivity {
    private Button btnUnlock;
    private Button btnNo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullver);
        initView();
    }

    void initView(){
        btnUnlock = (Button) findViewById(R.id.btn_unlock);
        btnNo = (Button) findViewById(R.id.btn_no);
        btnUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入支付界面
                Intent intent = new Intent(getApplicationContext(), BuyActivity.class);
                startActivity(intent);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
