package ar.edu.itba.iot.iot_android.model;

import java.util.Collection;

/**
 * Created by julianrodrigueznicastro on 10/31/17.
 */

public class User {

    private String token = null;

    private Collection<Device> devices;

    private String userName;

    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Collection<Device> getDevices() {
        return devices;
    }

    public void setDevices(Collection<Device> devices) {
        this.devices = devices;
    }


}
