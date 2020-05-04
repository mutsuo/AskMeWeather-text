package neet.com.knowweather.bean;

public class Messages {
    int code;
    String message;

    public Messages(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Messages() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
