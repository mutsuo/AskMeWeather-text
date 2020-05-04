package neet.com.knowweather.TempTestActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import neet.com.knowweather.Adapter.ChatAdapter;
import neet.com.knowweather.R;
import neet.com.knowweather.bean.Message;

public class ChatActivity extends AppCompatActivity {
    private ChatAdapter chatAdapter;
    private ImageView iv;
    private RecyclerView recyclerView;
    private EditText etContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        iv=findViewById(R.id.iv_send);
        chatAdapter=new ChatAdapter(new ArrayList<Message>()
        );
        recyclerView=findViewById(R.id.rv_chat_list);
        recyclerView.setAdapter(chatAdapter);
        etContent = (EditText) findViewById(R.id.et_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etContent.getText().toString();
                Log.e("123",text+"123");
                if (TextUtils.isEmpty(text)||TextUtils.equals(text," ")){
                    Message message = new Message(Message.RECEIVE);
                    message.setMsg("123");
                    chatAdapter.addData(message);
                }
                else {
                    Message message = new Message(Message.SEND);
                    message.setMsg(text);
                    chatAdapter.addData(message);
                }
                chatAdapter.notifyDataSetChanged();
            }
        });
    }
}
