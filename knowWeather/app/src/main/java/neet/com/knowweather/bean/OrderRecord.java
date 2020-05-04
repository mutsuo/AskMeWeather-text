package neet.com.knowweather.bean;

import java.io.Serializable;

/**
 * 订单详情实体类
 */
public class OrderRecord  implements Serializable {
    private int id;
    private int Mid;
    private int Tid;
    private String createTime;
    private String endTime;
    private int status;
    private int payWay;

    public OrderRecord() {
    }

    public OrderRecord(int id, int mid, int tid, String createTime, String endTime, int status, int payWay) {
        this.id = id;
        Mid = mid;
        Tid = tid;
        this.createTime = createTime;
        this.endTime = endTime;
        this.status = status;
        this.payWay = payWay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMid() {
        return Mid;
    }

    public void setMid(int mid) {
        Mid = mid;
    }

    public int getTid() {
        return Tid;
    }

    public void setTid(int tid) {
        Tid = tid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }
}
