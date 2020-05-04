package neet.com.knowweather.bean;

/**
 * 用户实体类
 */
public class Person {
    private int id;
    private String userName;
    private int tId;
    private String birthday;
    private String city;

    public Person() {
    }

    public Person(int id, String userName, int tId, String birthday, String city) {
        this.id = id;
        this.userName = userName;
        this.tId = tId;
        this.birthday = birthday;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
