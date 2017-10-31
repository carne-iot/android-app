package ar.edu.itba.iot.iot_android.model;

import java.util.Collection;

/**
 * Created by julianrodrigueznicastro on 10/31/17.
 */

public class User {

    private String token = null;

    private Collection<Device> devices;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Collection<Device> getDevices() {
        return devices;
    }

    public void setDevices(Collection<Device> devices) {
        this.devices = devices;
    }


}
