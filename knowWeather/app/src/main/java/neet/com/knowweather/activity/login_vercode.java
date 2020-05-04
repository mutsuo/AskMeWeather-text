package neet.com.knowweather.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import neet.com.knowweather.R;
import neet.com.knowweather.tool.ActivityCollector;
import neet.com.knowweather.tool.SMSToll;

public class login_vercode extends AppCompatActivity implements View.OnClickListener{
    private CardView weCard;
    private ImageView useraccountImg;
    private EditText useraccount;
    private TextView mistakemsg1;
    private ImageView vercodImg;
    private EditText vercode;
    private Button btnGetCode;
    private TextView mistakemsg2;
    private TextView tvForget;
    private Button btLogIn;
    private Button btLogInChange;
    private SMSToll smsToll;
    private EventHandler eventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_vercode);
        ActivityCollector.addActivity(this);
        MobSDK.init(this);
        findview();
        eventHandler=new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.i("EventHandler", "提交验证码成功");
                        //登录成功，将完整用户信息保存，然后跳转至首页
                        Intent intent = new Intent(getApplicationContext(), WaitingActivity.class);
                        startActivity(intent);
                        ActivityCollector.finishAll();
                    } else if
                    (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) { //获取验证码成功
                        Log.i("EventHandler", "获取验证码成功");
                    } else if
                    (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        Log.i("EventHandler", "返回支持发送验证码的国家列表");
                    }
                } else {
                    // TODO 处理错误的结果
                    Log.i("EventHandler", "未知错误");
                    Log.i("EventHandler", data.toString());
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
        initListener();
    }
    void findview(){
        weCard = (CardView) findViewById(R.id.we_card);
        useraccountImg = (ImageView) findViewById(R.id.useraccount_img);
        useraccount = (EditText) findViewById(R.id.useraccount);
        mistakemsg1 = (TextView) findViewById(R.id.mistakemsg1);
        vercodImg = (ImageView) findViewById(R.id.vercod_img);
        vercode = (EditText) findViewById(R.id.vercode);
        btnGetCode = (Button) findViewById(R.id.btn_get_code);
        mistakemsg2 = (TextView) findViewById(R.id.mistakemsg2);
        tvForget = (TextView) findViewById(R.id.tv_forget);
        btLogIn = (Button) findViewById(R.id.bt_log_in);
        btLogInChange = (Button) findViewById(R.id.bt_log_in_change);
    }

    void initListener(){
        btnGetCode.setOnClickListener(this);
        btLogIn.setOnClickListener(this);
        btLogInChange.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_forget:
                Intent intent = new Intent(getApplicationContext(), ForgetPassowrdActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_log_in_change:
                Intent intent2 = new Intent(getApplicationContext(), LoginPasswordActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_get_code:
                mistakemsg1.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(useraccount.getText().toString()) && checkPhoneValid(useraccount.getText().toString())) {
                    btnGetCode.setEnabled(false);
                    mTimer.start();
                    SMSSDK.getVerificationCode("86", useraccount.getText().toString());

                } else {
                    mistakemsg1.setVisibility(View.VISIBLE);
                    mistakemsg1.setText("请输入正确的手机号");
                }
                break;
            case R.id.bt_log_in:
                if (!TextUtils.isEmpty(vercode.getText().toString()) && !TextUtils.isEmpty(useraccount.getText().toString())) {
                    mistakemsg1.setVisibility(View.GONE);
                    if (checkPhoneValid(useraccount.getText().toString())){
                        mistakemsg2.setVisibility(View.GONE);
                        if (vercode.getText().toString().length()==4)
                        SMSSDK.submitVerificationCode("86", useraccount.getText().toString(), vercode.getText().toString());
                        else {
                            mistakemsg2.setVisibility(View.VISIBLE);
                            mistakemsg2.setText("请输入手机号和验证码");
                        }
                    }
                    else{
                        mistakemsg1.setVisibility(View.VISIBLE);
                        mistakemsg1.setText("请输入正确的手机号");
                    }
                } else {
                    mistakemsg2.setVisibility(View.VISIBLE);
                    mistakemsg2.setText("请输入手机号和验证码");
                }
        }
    }

    /**
     * 检查手机号码
     */
    private boolean checkPhoneValid(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    private CountDownTimer mTimer = new CountDownTimer(60000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            //时间间隔固定回调该方法

            btnGetCode.setText(millisUntilFinished/1000+"s重新获取");
        }
        @Override
        public void onFinish() {
            //倒计时结束时，回调该方法
            btnGetCode.setText("重新获取");
            btnGetCode.setEnabled(true);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
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


}
