package neet.com.knowweather.activity;



import android.annotation.SuppressLint;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import neet.com.knowweather.R;
import neet.com.knowweather.tool.ActivityCollector;
import neet.com.knowweather.tool.SMSToll;

public class LogupActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView weCard;
    private ImageView useraccountImg;
    private EditText useraccount;
    private TextView mistakemsg1;
    private ImageView passwordImg;
    private EditText password;
    private TextView mistakemsg2;
    private ImageView vercodImg;
    private EditText vercode;
    private Button btnGetCode;
    private TextView mistakemsg3;
    private Button btLogUp;
    private Button btBackIndex;
    private SMSToll smsToll;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);
        ActivityCollector.addActivity(this);
        findview();
        initListener();
        smsToll =new SMSToll(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case SMSToll.GET_SUCCESS:
                        Log.e("获取验证码成功", "成功");
                        Toast.makeText(getApplicationContext(),"获取验证码成功",Toast.LENGTH_SHORT).show();
                        break;
                    case SMSToll.SUBMIT_SUCCESS://验证成功处理
                        Log.e("验证成功", "成功");
                        //注册成功则将得到的用户信息进行储存并
                        Toast.makeText(getApplicationContext(),"验证成功",Toast.LENGTH_SHORT).show();
                        //成功后在这里进行登录后操作
                        Intent intent = new Intent(getApplicationContext(), NicknameSetActivity.class);
                        startActivity(intent);
                        break;
                    case SMSToll.CHECK_FAILE://服务器返回错误处理
                        Throwable data = (Throwable) msg.obj;
                        //logUpView.showFailedError("发生了我不知道的问题,请再试一次吧");
                        //Logger.d(data.getMessage());
                        Toast.makeText(getApplicationContext(),"验证失败",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }
    void findview(){
        weCard = (CardView) findViewById(R.id.we_card);
        useraccountImg = (ImageView) findViewById(R.id.useraccount_img);
        useraccount = (EditText) findViewById(R.id.useraccount);
        mistakemsg1 = (TextView) findViewById(R.id.mistakemsg1);
        passwordImg = (ImageView) findViewById(R.id.password_img);
        password = (EditText) findViewById(R.id.password);
        mistakemsg2 = (TextView) findViewById(R.id.mistakemsg2);
        vercodImg = (ImageView) findViewById(R.id.vercod_img);
        vercode = (EditText) findViewById(R.id.vercode);
        btnGetCode = (Button) findViewById(R.id.btn_get_code);
        mistakemsg3 = (TextView) findViewById(R.id.mistakemsg3);
        btLogUp = (Button) findViewById(R.id.bt_log_up);
        btBackIndex = (Button) findViewById(R.id.bt_back_index);
    }
void initListener(){
        btnGetCode.setOnClickListener(this);
    btLogUp.setOnClickListener(this);
    btBackIndex.setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_get_code:

                mistakemsg1.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(useraccount.getText().toString()) && checkPhoneValid(useraccount.getText().toString())) {
                    btnGetCode.setEnabled(false);
                    mTimer.start();
                    smsToll.sendSmsCode(useraccount.getText().toString());

                } else {
                    mistakemsg1.setVisibility(View.VISIBLE);
                    mistakemsg1.setText("请输入正确的手机号");
                }
                break;
            case R.id.bt_back_index:
                Intent intent = new Intent(getApplicationContext(), welcomeActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_log_up:
                if (!TextUtils.isEmpty(useraccount.getText().toString())&&checkPhoneValid(useraccount.getText().toString())){
                    mistakemsg1.setVisibility(View.GONE);
                    if (TextUtils.isEmpty(password.getText().toString())||password.getText().toString().length()<6){
                        mistakemsg2.setVisibility(View.VISIBLE);
                        mistakemsg2.setText("密码不符合格式");
                    }else{
                        mistakemsg2.setVisibility(View.GONE);
                        if (!TextUtils.isEmpty(vercode.getText().toString())&&vercode.getText().toString().length()==4){
                            smsToll.commint(useraccount.getText().toString(),vercode.getText().toString());//提交验证信息，结果都在EventHandler监听返回
                        }
                        else {
                            mistakemsg3.setVisibility(View.VISIBLE);
                            mistakemsg3.setText("请输入验证码");
                        }
                    }
                }else {
                    mistakemsg1.setVisibility(View.VISIBLE);
                    mistakemsg1.setText("请输入正确的手机号");
                }
                break;
        }
    }
}
