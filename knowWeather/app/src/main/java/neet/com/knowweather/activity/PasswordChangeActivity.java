package neet.com.knowweather.activity;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import neet.com.knowweather.R;

public class PasswordChangeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvNext;
    private TextView tvOldpassword;
    private EditText etOldpassword;
    private TextView tvNewpassword;
    private EditText etNewpassowrd;
    private TextView tvEnnewpassword;
    private EditText etEnnewpassowrd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        initview();
    }

    void initview(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvNext = (TextView) findViewById(R.id.tv_next);
        tvOldpassword = (TextView) findViewById(R.id.tv_oldpassword);
        etOldpassword = (EditText) findViewById(R.id.et_oldpassword);
        tvNewpassword = (TextView) findViewById(R.id.tv_newpassword);
        etNewpassowrd = (EditText) findViewById(R.id.et_newpassowrd);
        tvEnnewpassword = (TextView) findViewById(R.id.tv_ennewpassword);
        etEnnewpassowrd = (EditText) findViewById(R.id.et_ennewpassowrd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//主键按钮能否可点击x
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回图标
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old = tvOldpassword.getText().toString();
                String new1=tvNewpassword.getText().toString();
                String new2=tvEnnewpassword.getText().toString();
                if(TextUtils.isEmpty(old)||TextUtils.isEmpty(new1)||TextUtils.isEmpty(new2)){
                    Toast.makeText(getApplication(),"请填写没有输入的内容",Toast.LENGTH_SHORT).show();
                }else if(!TextUtils.equals(new1,new2)){
                    Toast.makeText(getApplication(),"请保证两次输入密码相同",Toast.LENGTH_SHORT).show();
                }else{
                    //访问服务器
                    Toast.makeText(getApplication(),"修改成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), PersonCenterActivity.class);
                    startActivity(intent);
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

        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
