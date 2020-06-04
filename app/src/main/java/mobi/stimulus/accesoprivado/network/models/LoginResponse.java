package mobi.stimulus.accesoprivado.network.models;

import java.io.Serializable;

import mobi.stimulus.accesoprivado.model.User;

public class LoginResponse implements Serializable {
    private Boolean auth;
    private String token;
    private int code;
    private User user;

    public LoginResponse(Boolean auth, String token, int code, User user) {
        this.auth = auth;
        this.token = token;
        this.code = code;
        this.user = user;
    }

    public Boolean getAuth() {
        return auth;
    }

    public String getToken() {
        return token;
    }

    public int getCode() {
        return code;
    }

    public User getUser() {
        return user;
    }
}

