package neet.com.knowweather.bean;

public class Order {
    private Messages messages;
    private OrderRecord orderRecord;

    public Order() {
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public OrderRecord getOrderRecord() {
        return orderRecord;
    }

    public void setOrderRecord(OrderRecord orderRecord) {
        this.orderRecord = orderRecord;
    }
}
