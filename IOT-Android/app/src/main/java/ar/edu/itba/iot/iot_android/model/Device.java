package ar.edu.itba.iot.iot_android.model;

import java.util.Observable;

public class Device extends Observable{

    private double temperature = -1;

    private double targetTemperature = -1;

    private boolean willTurnOver = false;

    private String id;

    private String nickname;

    public Device(DeviceAux dev) {
        temperature = dev.getTemperature();
        id = dev.getId();
        nickname = dev.getNickname();
    }

    public Device(String id, double temperature, double targetTemperature, boolean willTurnOver) {
        this.id = id;
        this.temperature = temperature;
        this.targetTemperature = targetTemperature;
        this.willTurnOver = willTurnOver;
    }

    public Device(String id, double targetTemperature, boolean willTurnOver) {
        this.id = id;
        this.targetTemperature = targetTemperature;
        this.willTurnOver = willTurnOver;
    }

    public Device(String id, double targetTemperature) {
        this.id = id;
        this.targetTemperature = targetTemperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getTargetTemperature() {
        return targetTemperature;
    }

    public boolean isWillTurnOver() {
        return willTurnOver;
    }

    public String getId() {
        return id;
    }

    public void setWillTurnOver(boolean willTurnOver) {
        this.willTurnOver = willTurnOver;
    }

    public void setTargetTemperature(double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        if (Double.compare(device.temperature, temperature) != 0) return false;
        if (Double.compare(device.targetTemperature, targetTemperature) != 0) return false;
        if (willTurnOver != device.willTurnOver) return false;
        if (id != null ? !id.equals(device.id) : device.id != null) return false;
        return nickname != null ? nickname.equals(device.nickname) : device.nickname == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(temperature);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(targetTemperature);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (willTurnOver ? 1 : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        return result;
    }
}

