package neet.com.knowweather.bean;

/**
 * 订单列表实体类
 * 展示订单概要信息
 */
public class OrderMenu {
    private int id;
    private int orderNumber;
    private String orderType;
    private double price;
    private int month;
    //订单状态信息1.生效   2.废弃
    private int status;

    public OrderMenu() {
    }

    public OrderMenu(int id, int orderNumber, String orderType, double price, int month, int status) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderType = orderType;
        this.price = price;
        this.month = month;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
