package ar.edu.itba.iot.iot_android.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;


public class User extends Observable{

    private String token = null;

    private List<Device> devices =  new LinkedList<>();

    private String userName;

    private String password;

    private Long id = Long.valueOf(-1);

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
        this.setChanged();
        this.notifyObservers();
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
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
