package ar.edu.itba.iot.iot_android.utils;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.edu.itba.iot.iot_android.model.Device;
import ar.edu.itba.iot.iot_android.model.DeviceAux;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.model.UserAux;

//TODO no usar pojo auxiliar
public class JSONManager {

    final private static ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;

    public static List<Device> parseDevices(String json){
        List<DeviceAux> devicesAux = new ArrayList<>();
        try {
            devicesAux =
                    objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, DeviceAux.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Device> devices = new ArrayList<>();
        for(DeviceAux dAux : devicesAux){
            devices.add(new Device(dAux));
        }
        return devices;
    }


    public static Device parseDevice(String json) {

        DeviceAux deviceAux = null;
        try {
            deviceAux = objectMapper.readValue(json, DeviceAux.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Device device = new Device(deviceAux);
        return device;
    }

    public static String jsifyDevice(Device device){
        try {
            return objectMapper.writeValueAsString(device);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User parseUser(String json){

        UserAux userAux;
        try {
            userAux = objectMapper.readValue(json, UserAux.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return new User(userAux);
    }

    public static String jsifyUser(User user){
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
