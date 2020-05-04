package neet.com.knowweather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import neet.com.knowweather.R;
import neet.com.knowweather.SettingApps.UserSettingApplication;

public class PhoneChangesActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvNext;
    private TextView tvNewphone;
    private EditText etNewphone;
    private TextView tvVercode;
    private EditText etVercode;
    private Button btnGetCode;
    private EventHandler eventHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_changes);
        intiview();
        SMSSDK.registerEventHandler(eventHandler);
    }

    void intiview(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvNext = (TextView) findViewById(R.id.tv_next);
        tvNewphone = (TextView) findViewById(R.id.tv_newphone);
        etNewphone = (EditText) findViewById(R.id.et_newphone);
        tvVercode = (TextView) findViewById(R.id.tv_vercode);
        etVercode = (EditText) findViewById(R.id.et_vercode);
        btnGetCode = (Button) findViewById(R.id.btn_get_code);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//主键按钮能否可点击x
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回图标
        eventHandler=new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.i("EventHandler", "提交验证码成功");
                        //修改成功
                        Toast.makeText(getApplication(),"修改成功",Toast.LENGTH_SHORT).show();
                        UserSettingApplication.editor.putString("phone",etNewphone.getText().toString());
                        UserSettingApplication.editor.apply();
                        Intent intent = new Intent(getApplicationContext(), PersonCenterActivity.class);
                        startActivity(intent);
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
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etNewphone.getText().toString())&& checkPhoneValid(etNewphone.getText().toString()) ) {
                    btnGetCode.setEnabled(false);
                    mTimer.start();
                    SMSSDK.getVerificationCode("86", etNewphone.getText().toString());

                } else {
                    Toast.makeText(getApplication(),"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etNewphone.getText().toString()))
                {
                    Toast.makeText(getApplication(),"请输入手机号",Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty( etVercode.getText().toString())){
                    Toast.makeText(getApplication(),"请输入验证码",Toast.LENGTH_LONG).show();
                }else{
                    SMSSDK.submitVerificationCode("86", etNewphone.getText().toString(), etVercode.getText().toString());
                }

            }
        });
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
