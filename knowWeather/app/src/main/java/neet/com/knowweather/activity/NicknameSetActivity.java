package neet.com.knowweather.activity;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import neet.com.knowweather.R;
import neet.com.knowweather.SettingApps.UserSettingApplication;
import neet.com.knowweather.tool.ActivityCollector;

public class NicknameSetActivity extends AppCompatActivity {
    private CardView weCard;
    private ImageView weLogo;
    private ImageView editImg;
    private Button btEnter;
    private String nickname;
    private EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname_set);
        ActivityCollector.addActivity(this);
        findview();
        setLinster();
    }
     void findview(){
         weCard = (CardView) findViewById(R.id.we_card);
         weLogo = (ImageView) findViewById(R.id.we_logo);
         editImg = (ImageView) findViewById(R.id.edit_img);
         btEnter = (Button) findViewById(R.id.bt_enter);
         name=findViewById(R.id.nicname);
     }
    void setLinster(){
        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nickname=name.getText().toString();
                if(!TextUtils.isEmpty(nickname)){
                    //设置成功访问服务器
                    UserSettingApplication.editor.putString("name",nickname);
                    UserSettingApplication.editor.apply();
                    Intent intent = new Intent(getApplicationContext(), IndexActivity.class);
                    startActivity(intent);
                    ActivityCollector.finishAll();
                }else {
                    Toast.makeText(getApplicationContext(),"请输入昵称",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
