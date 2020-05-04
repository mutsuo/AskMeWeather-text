package neet.com.knowweather.activity;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import neet.com.knowweather.Adapter.ChatAdapter;
import neet.com.knowweather.R;
import neet.com.knowweather.bean.Message;

public class IMWeatherAskActivity extends AppCompatActivity {
    private RelativeLayout commonToolbar;
    private RelativeLayout commonToolbarBack;
    private TextView commonToolbarTitle;
    private LinearLayout llContent;
    private SwipeRefreshLayout swipeChat;
    private RecyclerView rvChatList;
    private ImageView ivAudio;
    private EditText etContent;
    private ImageView ivsend;
    private LinearLayout llBottom;
    private RelativeLayout rlRecord;
    private ChatAdapter chatAdapter;
    private ImageView ivEditext;
    private Button btnSpeak;
    private SpeechRecognizer recognizer;
    private StringBuilder sentence = null;
    private SpeechRecognizer speaker;
    private mRecognizerListener mRecognizerListener=new mRecognizerListener();
    private InitListener mInitListener =new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d("TAG", "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(getApplicationContext(),"初始化失败，错误码：" + code, Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imweather_ask);
        initView();
        setClick();
        initMSC();

    }

    void initView(){
        commonToolbar = (RelativeLayout) findViewById(R.id.common_toolbar);
        commonToolbarBack = (RelativeLayout) findViewById(R.id.common_toolbar_back);
        //commonToolbarTitle = (TextView) findViewById(R.id.common_toolbar_title);
        llContent = (LinearLayout) findViewById(R.id.llContent);
        swipeChat = (SwipeRefreshLayout) findViewById(R.id.swipe_chat);
        rvChatList = (RecyclerView) findViewById(R.id.rv_chat_list);
        ivAudio = (ImageView) findViewById(R.id.ivAudio);
        etContent = (EditText) findViewById(R.id.et_content);
        ivsend = (ImageView) findViewById(R.id.iv_send);
        llBottom=findViewById(R.id.ll_bottom);
        rlRecord=findViewById(R.id.rlrecord);
        ivEditext=findViewById(R.id.iv_editext);
        chatAdapter=new ChatAdapter(new ArrayList<Message>());
        rvChatList.setLayoutManager(new LinearLayoutManager(this));
        rvChatList.setAdapter(chatAdapter);
        btnSpeak = (Button) findViewById(R.id.btn_speak);

    }

    void setClick(){
        ivsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etContent.getText().toString();
                etContent.clearFocus();

                if (TextUtils.isEmpty(text)){
                    remsg("输入为空");
                }
                else {
                    sendmessage(text);
                }
                etContent.setText("");

            }

        });
        ivAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.clearFocus();
                llBottom.setVisibility(View.GONE);
                rlRecord.setVisibility(View.VISIBLE);
            }
        });
        ivEditext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llBottom.setVisibility(View.VISIBLE);
                rlRecord.setVisibility(View.GONE);
            }
        });

//        btnSpeak.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                recognizer.startListening(mRecognizerListener);
//                return false;
//            }
//        });
        btnSpeak.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    recognizer.startListening(mRecognizerListener);
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    Toast.makeText(getApplicationContext(),"结束",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        commonToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    void sendmessage(String text){
        Message message = new Message(Message.SEND);
        message.setMsg(text);
        chatAdapter.addData(message);
    }
    void remsg(String text){
        Message message = new Message(Message.RECEIVE);
        message.setMsg(text);
        chatAdapter.addData(message);
    }
    void initMSC(){
        recognizer= SpeechRecognizer.createRecognizer(this,mInitListener);
        speaker = SpeechRecognizer.createRecognizer(this,mInitListener);
        //设置听写参数
        recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        //设置为中文
        recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //设置为普通话
        recognizer.setParameter(SpeechConstant.ACCENT, "mandarin ");
        recognizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        recognizer.setParameter(SpeechConstant.VAD_BOS, "4000");
        recognizer.setParameter(SpeechConstant.VAD_EOS, "1000");
        recognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");
    }
    private class mRecognizerListener implements RecognizerListener{

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        @Override
        public void onBeginOfSpeech() {
            Toast.makeText(getApplicationContext(),"开始录音",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEndOfSpeech() {
            sendmessage(sentence.toString());
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            String result = recognizerResult.getResultString();
            try {
                //处理json结果
                JSONObject jsonObject = new JSONObject(result);
                JSONArray words = jsonObject.getJSONArray("ws");
                //拼成句子
                sentence = new StringBuilder("");
                for( int i = 0 ; i < words.length() ; i ++ ){
                    JSONObject word = words.getJSONObject(i);
                    JSONArray subArray = word.getJSONArray("cw");
                    JSONObject subWord = subArray.getJSONObject(0);
                    String character = subWord.getString("w");
                    sentence.append(character);
                }
                //打印
                Log.e("TAG", sentence.toString());
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),"声音太小了，请再说一遍",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            Toast.makeText(getApplicationContext(),"声音太小了，请再说一遍",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    }

}
