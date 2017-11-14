package ar.edu.itba.iot.iot_android.controller;

import ar.edu.itba.iot.iot_android.activities.MainActivity;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.service.DeviceService;

/**
 * Created by julianrodrigueznicastro on 11/14/17.
 */

public class DeviceController {

    private DeviceService deviceService = new DeviceService();
    private final User user;
    private final MainActivity mainActivity;

    public DeviceController(MainActivity mainActivity, User user) {
        this.user = user;
        this.mainActivity = mainActivity;
    }



}
