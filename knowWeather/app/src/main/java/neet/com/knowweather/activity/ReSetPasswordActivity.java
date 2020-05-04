package neet.com.knowweather.activity;

import android.support.v7.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import neet.com.knowweather.R;
import neet.com.knowweather.tool.ActivityCollector;

public class ReSetPasswordActivity extends AppCompatActivity {
    private CardView weCard;
    private ImageView useraccountImg;
    private EditText newpassword;
    private ImageView ivShownewpsw;
    private TextView mistakemsg1;
    private ImageView passwordImg;
    private EditText ensurepassword;
    private ImageView ivShowensurepsw;
    private TextView mistakemsg2;
    private Button btLogIn;
    private Boolean isShow1=false;
    private Boolean isShow2=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ActivityCollector.addActivity(this);
        findview();
        setListner();
    }
    void findview(){
        weCard = (CardView) findViewById(R.id.we_card);
        useraccountImg = (ImageView) findViewById(R.id.useraccount_img);
        newpassword = (EditText) findViewById(R.id.newpassword);
        ivShownewpsw = (ImageView) findViewById(R.id.iv_shownewpsw);
        mistakemsg1 = (TextView) findViewById(R.id.mistakemsg1);
        passwordImg = (ImageView) findViewById(R.id.password_img);
        ensurepassword = (EditText) findViewById(R.id.ensurepassword);
        ivShowensurepsw = (ImageView) findViewById(R.id.iv_showensurepsw);
        mistakemsg2 = (TextView) findViewById(R.id.mistakemsg2);
        btLogIn = (Button) findViewById(R.id.bt_log_in);
    }
    void setListner(){
        ivShownewpsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShow1) {
                    //设置EditText的密码为可见的
                    newpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //设置密码为隐藏的
                    newpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                isShow1=!isShow1;
            }
        });
        ivShowensurepsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShow2) {
                    //设置EditText的密码为可见的
                    ensurepassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //设置密码为隐藏的
                    ensurepassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                isShow2=!isShow2;
            }
        });
        btLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd1=newpassword.getText().toString();
                String pwd2=ensurepassword.getText().toString();
                if(TextUtils.isEmpty(pwd1)||TextUtils.isEmpty(pwd2)||!TextUtils.equals(pwd1,pwd2)){
                    mistakemsg1.setVisibility(View.VISIBLE);
                    mistakemsg1.setText("请确认密码的输入");
                }
                else{
                    //访问服务器
                    //成功
                    Intent intent = new Intent(getApplicationContext(), LoginPasswordActivity.class);
                    startActivity(intent);
                    //失败
                    //Toast错误
                }
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
}
