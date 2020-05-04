package neet.com.knowweather.activity;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import neet.com.knowweather.R;
import neet.com.knowweather.tool.ActivityCollector;

public class welcomeActivity extends AppCompatActivity {
    private CardView weCard;
    private ImageView weLogo;
    private Button btLogIn;
    private Button btLogUp;
    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ActivityCollector.addActivity(this);
        findView();
        setClickListener();
    }

    private void findView(){
        weCard = (CardView) findViewById(R.id.we_card);
        weLogo = (ImageView) findViewById(R.id.we_logo);
        btLogIn = (Button) findViewById(R.id.bt_log_in);
        btLogUp = (Button) findViewById(R.id.bt_log_up);
        tvTest = (TextView) findViewById(R.id.tv_test);
    }
    private void setClickListener(){
        btLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(welcomeActivity.this, LoginPasswordActivity.class);
                startActivity(intent);
            }
        });
        btLogUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(welcomeActivity.this, LogupActivity.class);
                startActivity(intent);
            }
        });
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(welcomeActivity.this, WaitingActivity.class);
                startActivity(intent);
            }
        });
    }
}
