package neet.com.knowweather.Adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import neet.com.knowweather.R;
import neet.com.knowweather.bean.Message;

public class ChatAdapter extends BaseMultiItemQuickAdapter<Message,BaseViewHolder> {



    public ChatAdapter(@Nullable List<Message> data) {
        super(data);
        addItemType(Message.SEND, R.layout.item_text_send);
        addItemType(Message.RECEIVE,R.layout.item_text_receive);
    }



    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        switch (helper.getItemViewType()) {
            case Message.SEND:
                helper.setText(R.id.chat_item_content_text,item.getMsg());
                break;
            case Message.RECEIVE:
                helper.setText(R.id.chat_item_content_text,item.getMsg());
                break;
        }
    }
}
