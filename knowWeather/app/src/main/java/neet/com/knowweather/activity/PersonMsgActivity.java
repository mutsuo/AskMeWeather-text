package neet.com.knowweather.activity;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Date;


import neet.com.knowweather.MainActivity;
import neet.com.knowweather.R;
import neet.com.knowweather.SettingApps.UserSettingApplication;

public class PersonMsgActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RelativeLayout rv1;
    private TextView tvNickname;
    private TextView tvBirthday;
    private TextView tvBirthday2;
    private TextView tvPhone;
    private TextView phone;
    private ImageView ivPhone;
    private ImageView ivPassowrd;
    private TimePickerView pvTime;
    private RelativeLayout rvPhone;
    private RelativeLayout rvpsw;
    private EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_msg);
        findview();
        initTimePicker();
        setListener();
    }

    void findview(){
        toolbar = findViewById(R.id.toolbar);
        rv1 = (RelativeLayout) findViewById(R.id.rv_1);
        tvNickname = (TextView) findViewById(R.id.tv_nickname);
        tvBirthday = (TextView) findViewById(R.id.tv_birthday);
        tvBirthday2 = (TextView) findViewById(R.id.tv_birthday2);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        phone = (TextView) findViewById(R.id.phone);
        ivPhone = (ImageView) findViewById(R.id.iv_phone);
        ivPassowrd = (ImageView) findViewById(R.id.iv_passowrd);
        rvPhone=findViewById(R.id.rv_phone);
        rvpsw=findViewById(R.id.rv_psw);
        name=findViewById(R.id.et_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//主键按钮能否可点击x
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回图标
        tvBirthday2.setText(UserSettingApplication.sharedPreferences.getString("birthady"," "));
        tvNickname.setText(UserSettingApplication.sharedPreferences.getString("name","默认"));
        phone.setText(UserSettingApplication.sharedPreferences.getString("phone","6666666666"));
    }

    void setListener(){
        rvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhoneChangeActivity.class);
                startActivity(intent);
            }
        });
        rvpsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PasswordChangeActivity.class);
                startActivity(intent);
            }
        });
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){

                }else {
                    if (!TextUtils.isEmpty(name.getText().toString())){
                        UserSettingApplication.editor.putString("name",name.getText().toString());
                        UserSettingApplication.editor.apply();
                    }
                }
            }
        });
    }
/**
 * 时间选择器
 */
void initTimePicker(){
     pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date, View v) {
            //Toast.makeText(getApplicationContext(), getTime(date), Toast.LENGTH_SHORT).show();
            tvBirthday2.setText(getTime(date));
            //访问服务器
            UserSettingApplication.editor.putString("birthady",getTime(date));
            UserSettingApplication.editor.apply();
        }
    }).build();
     tvBirthday2.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             pvTime.show();
         }
     });
}
    /**
     * 解析时间为字符串
     * @param date
     * @return
     */
    private String getTime(Date date) {//可根据需要自行截取数据显示
        //Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }
    /**
     * 点击空白处使EditText失去焦点
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_DOWN){
            View view =getCurrentFocus();
            if(isHideInput(view,ev)){
                HideSoftInput(view.getWindowToken());
                view.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    public boolean isHideInput(View v, MotionEvent ev){
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left) || !(ev.getX() < right) || !(ev.getY() > top) || !(ev.getY() < bottom);
        }
        return false;
    }
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
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

    @Override
    protected void onRestart() {
        super.onRestart();
        tvBirthday2.setText(UserSettingApplication.sharedPreferences.getString("birthady"," "));
        tvNickname.setText(UserSettingApplication.sharedPreferences.getString("name","默认"));
        phone.setText(UserSettingApplication.sharedPreferences.getString("phone","6666666666"));
    }
}
