package nettyx.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class CommonUser implements Serializable {
    private static final long serialVersionUID = 9028323926560318958L;
    public static final String USER_INFO = "user_info";

    private String id;
    private String username;
    private String token;
    private final Date loginDate = new Date(); // 登录时间
    private final AtomicInteger sendCount = new AtomicInteger(0);
    private final AtomicInteger receiveCount = new AtomicInteger(0);

    public CommonUser(String id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLoginDate() {
        return loginDate;
    }
}