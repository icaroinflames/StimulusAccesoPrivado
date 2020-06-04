package mobi.stimulus.accesoprivado.model;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private int userId;
    private int userIdDispositivo;
    private String idStimulus;

    public User(String id, int userId, int userIdDispositivo, String idStimulus) {
        this.id = id;
        this.userId = userId;
        this.userIdDispositivo = userIdDispositivo;
        this.idStimulus = idStimulus;
    }

    public String getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getUserIdDispositivo() {
        return userIdDispositivo;
    }

    public String getIdStimulus() {
        return idStimulus;
    }
}
