package neet.com.knowweather.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import neet.com.knowweather.R;
import neet.com.knowweather.SettingApps.UserSettingApplication;
import neet.com.knowweather.bean.User;
import neet.com.knowweather.tool.ActivityCollector;

public class LoginPasswordActivity extends AppCompatActivity {
    private CardView weCard;
    private ImageView useraccountImg;
    private EditText useraccount;
    private TextView mistakemsg1;
    private ImageView passwordImg;
    private EditText password;
    private TextView mistakemsg2;
    private Button btLogIn;
    private Button btLogInChange;
    private String userphone;
    private String upassowrd;
    private TextView forget;
    private UserSettingApplication userSettingApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);
        userSettingApplication= (UserSettingApplication) getApplication();
        ActivityCollector.addActivity(this);
        findview();
        setListener();

    }

    private void findview(){
        weCard = (CardView) findViewById(R.id.we_card);
        useraccountImg = (ImageView) findViewById(R.id.useraccount_img);
        useraccount = (EditText) findViewById(R.id.useraccount);
        mistakemsg1 = (TextView) findViewById(R.id.mistakemsg1);
        passwordImg = (ImageView) findViewById(R.id.password_img);
        password = (EditText) findViewById(R.id.password);
        mistakemsg2 = (TextView) findViewById(R.id.mistakemsg2);
        btLogIn = (Button) findViewById(R.id.bt_log_in);
        btLogInChange = (Button) findViewById(R.id.bt_log_in_change);
        forget=findViewById(R.id.tv_forget);

    }
    public void setListener(){
        btLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userphone=useraccount.getText().toString();
                upassowrd=password.getText().toString();
                if(!userphone.isEmpty()&&checkPhoneValid(userphone)){
                    mistakemsg1.setVisibility(View.GONE);
                    mistakemsg1.setText("  ");
                    if(!upassowrd.isEmpty()||upassowrd.equals("959783192")){
                     mistakemsg2.setVisibility(View.GONE);
                     //登录操作，网络请求
                        //返回状态码，确定登陆成功失败
                        //成功则在app中记录完整用户信息
                        //否则显示提示信息
                        userSettingApplication.setIsFirst(false);
                        userSettingApplication.setIsLogin(true);
                        User user = new User();
                        user.setBirthady("2020年5月1日");
                        user.setCity("石家庄");
                        user.setId(11111111);
                        user.setNickName("测试员");
                        user.setVip(0);
                        userSettingApplication.setUser(user);
                        Intent intent = new Intent(getApplicationContext(), WaitingActivity.class);
                        startActivity(intent);
                        ActivityCollector.finishAll();
                    }
                    else {
                        mistakemsg2.setVisibility(View.VISIBLE);
                        mistakemsg2.setText("请输入正确的密码");
                    }
                }
                else{
                    mistakemsg1.setVisibility(View.VISIBLE);
                    mistakemsg1.setText("请输入正确的手机号");
                }
            }
        });
        btLogInChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login_vercode.class);
                startActivity(intent);
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgetPassowrdActivity.class);
                startActivity(intent);
            }
        });
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
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
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
    /**
     * 检查手机号码
     */
    private boolean checkPhoneValid(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    private void toast(String  msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
