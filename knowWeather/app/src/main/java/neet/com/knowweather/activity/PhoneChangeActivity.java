package neet.com.knowweather.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import neet.com.knowweather.R;

public class PhoneChangeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvNext;
    private TextView tvOldphone;
    private EditText etOldphone;
    private TextView tvNewphone;
    private EditText etPassowrd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_change);
        initView();
    }

    void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvNext = (TextView) findViewById(R.id.tv_next);
        tvOldphone = (TextView) findViewById(R.id.tv_oldphone);
        etOldphone = (EditText) findViewById(R.id.et_oldphone);
        tvNewphone = (TextView) findViewById(R.id.tv_newphone);
        etPassowrd = (EditText) findViewById(R.id.et_passowrd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//主键按钮能否可点击x
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回图标
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框内容
                String phone = etOldphone.getText().toString();
                String password= etPassowrd.getText().toString();
                //检查手机号与密码是否有效
                //启动异步任务

                //下一步
                Intent intent = new Intent(getApplicationContext(), PhoneChangesActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
