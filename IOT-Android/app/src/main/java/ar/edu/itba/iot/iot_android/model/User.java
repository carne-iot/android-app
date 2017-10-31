package ar.edu.itba.iot.iot_android.model;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by julianrodrigueznicastro on 10/31/17.
 */

public class User {

    private String token = null;

    private Collection<Device> devices =  new HashSet<>();

    private String userName;

    private String password;

    private Long id = new Long(-1);

    private String logoutURL = null;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password, Long id) {
        this.userName = userName;
        this.password = password;
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogoutURL(String logoutURL) {
        this.logoutURL = logoutURL;
    }


}
