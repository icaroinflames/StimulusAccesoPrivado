package mobi.stimulus.accesoprivado.network.models;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    private String usuario;
    private String pass;

    public LoginRequest(String usuario, String pass) {
        this.usuario = usuario;
        this.pass = pass;
    }

}
