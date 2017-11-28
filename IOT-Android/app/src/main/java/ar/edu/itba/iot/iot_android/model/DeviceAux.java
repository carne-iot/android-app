package ar.edu.itba.iot.iot_android.model;

public class DeviceAux {

    private String id;
    private Double temperature;
    private Double targetTemperature;
    private String lastTemperatureUpdate;
    private String state;
    private String locationUrl;
    private String nickname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getLastTemperatureUpdate() {
        return lastTemperatureUpdate;
    }

    public void setLastTemperatureUpdate(String lastTemperatureUpdate) {
        this.lastTemperatureUpdate = lastTemperatureUpdate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(Double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", temperature=" + temperature +
                ", lastTemperatureUpdate='" + lastTemperatureUpdate + '\'' +
                ", state='" + state + '\'' +
                ", locationUrl='" + locationUrl + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}

