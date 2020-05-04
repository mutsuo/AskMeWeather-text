package neet.com.knowweather.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class Message implements MultiItemEntity {

    public static final int SEND = 1;
    public static final int RECEIVE = 2;
    private int itemType;
    private String msg;

    public Message(int itemType) {
        this.itemType = itemType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
