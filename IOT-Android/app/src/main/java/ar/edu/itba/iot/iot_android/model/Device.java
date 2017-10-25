package ar.edu.itba.iot.iot_android.model;

/**
 * Created by julianrodrigueznicastro on 10/25/17.
 */

public class Device {

    private double currentTemperature = -1;

    private double targetTemperature = -1;

    private boolean willTurnOver = false;

    public Device(double currentTemperature, double targetTemperature, boolean willTurnOver) {
        this.currentTemperature = currentTemperature;
        this.targetTemperature = targetTemperature;
        this.willTurnOver = willTurnOver;
    }

    public Device(double targetTemperature, boolean willTurnOver) {
        this.targetTemperature = targetTemperature;
        this.willTurnOver = willTurnOver;
    }

    public Device(double targetTemperature) {
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
}
