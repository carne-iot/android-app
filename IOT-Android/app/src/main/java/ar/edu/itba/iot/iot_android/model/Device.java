package ar.edu.itba.iot.iot_android.model;

public class Device {

    private double currentTemperature = -1;

    private double targetTemperature = -1;

    private boolean willTurnOver = false;

    private final String id;

    public Device(String id, double currentTemperature, double targetTemperature, boolean willTurnOver) {
        this.id = id;
        this.currentTemperature = currentTemperature;
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

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public double getTargetTemperature() {
        return targetTemperature;
    }

    public boolean isWillTurnOver() {
        return willTurnOver;
    }

    public void setTargetTemperature(double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public String getId() {
        return id;
    }

    public void setWillTurnOver(boolean willTurnOver) {
        this.willTurnOver = willTurnOver;
    }
}
