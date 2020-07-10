package com.youzhuan.demoservcie;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.socks.library.KLog;
import com.youzhuan.iot.YzIotService;
import com.youzhuan.iot.constant.SdkAction;
import com.youzhuan.iot.constant.YzAttribute;
import com.youzhuan.iot.constant.YzDevAction;
import com.youzhuan.iot.constant.YzDevType;
import com.youzhuan.iot.constant.YzRequestCode;
import com.youzhuan.iot.constant.YzResponseCode;
import com.youzhuan.iot.model.Appliance;
import com.youzhuan.iot.model.ControlRequest;
import com.youzhuan.iot.model.ControlResult;
import com.youzhuan.iot.model.SdkInfoConfig;
import com.youzhuan.iot.model.attr.IotColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 樱花满地集于我心
 * Create By 2020-07-02
 */
public class CustomService extends YzIotService {

    private static final String TAG = CustomService.class.getName();
    private List<Appliance> virtualDevice = new ArrayList<>();
    private SdkInfoConfig config;

    @Override
    public void init(Context context) {
        KLog.e("初始化init");
        config = new SdkInfoConfig();
        config.setSupportLogin(true);
        config.setManufacturerName("右转科技");
        initTheVirtualDevice();
    }

    private void initTheVirtualDevice() {
        lightOrSwitch();
        Appliance appliance = new Appliance();
        appliance.setApplianceId(4);
        appliance.setApplianceTypes(YzDevType.CURTAIN);
        appliance.setDeviceName("我的窗帘");
        appliance.setActions(YzDevAction.turnOn, YzDevAction.turnOff);
        virtualDevice.add(appliance);
        appliance = new Appliance();
        appliance.setApplianceId(5);
        appliance.setApplianceTypes(YzDevType.AIR_CONDITION);
        appliance.setDeviceName("1号空调");
        appliance.setActions(YzDevAction.turnOn,
                YzDevAction.turnOff,
                YzDevAction.setTemperature,
                YzDevAction.setMode);
        virtualDevice.add(appliance);
        appliance = new Appliance();
        appliance.setApplianceId(6);
        appliance.setApplianceTypes(YzDevType.TV_SET);
        appliance.setDeviceName("电视机");
        appliance.setActions(YzDevAction.turnOn,
                YzDevAction.turnOff,
                YzDevAction.setTemperature,
                YzDevAction.setMode);
        virtualDevice.add(appliance);
        Appliance caideng = new Appliance();
        caideng.setApplianceId(7);
        caideng.setApplianceTypes(YzDevType.LIGHT_RGB);
        caideng.setDeviceName("彩灯");
        caideng.setActions(YzDevAction.turnOn,
                YzDevAction.turnOff,
                YzDevAction.setColor);
        virtualDevice.add(caideng);
        //地暖
        Appliance floorWarm = new Appliance();
        floorWarm.setApplianceId(8);
        floorWarm.setApplianceTypes(YzDevType.FLOOR_WARM);
        floorWarm.setDeviceName("地暖");
        floorWarm.setActions(YzDevAction.turnOn,
                YzDevAction.turnOff,
                YzDevAction.setTemperature,
                YzDevAction.setMode);
        virtualDevice.add(floorWarm);
        //风扇
        Appliance fan = new Appliance();
        fan.setApplianceId(9);
        fan.setApplianceTypes(YzDevType.FAN);
        fan.setDeviceName("风扇");
        fan.setActions(YzDevAction.turnOn,
                YzDevAction.turnOff);
        virtualDevice.add(fan);
        Appliance air_fresher = new Appliance();
        air_fresher.setApplianceId(10);
        air_fresher.setApplianceTypes(YzDevType.AIR_FRESHER);
        air_fresher.setDeviceName("新风机");
        air_fresher.setActions(YzDevAction.turnOn,
                YzDevAction.turnOff);
        virtualDevice.add(air_fresher);
        Appliance air_purifier = new Appliance();
        air_purifier.setApplianceId(11);
        air_purifier.setApplianceTypes(YzDevType.AIR_PURIFIER);
        air_purifier.setDeviceName("空气净化器");
        air_purifier.setActions(YzDevAction.turnOn,
                YzDevAction.turnOff);
        virtualDevice.add(air_purifier);
        Appliance temperatureLight = new Appliance();
        temperatureLight.setApplianceId(12);
        temperatureLight.setApplianceTypes(YzDevType.LIGHT_CT);
        temperatureLight.setDeviceName("色温灯");
        temperatureLight.setActions(YzDevAction.turnOn,
                YzDevAction.turnOff);
        virtualDevice.add(temperatureLight);
        Appliance closeRack = new Appliance();
        closeRack.setApplianceId(13);
        closeRack.setApplianceTypes(YzDevType.CLOTHES_RACK);
        closeRack.setDeviceName("晾衣架");
        closeRack.setActions(YzDevAction.turnOn,
                YzDevAction.turnOff);
        virtualDevice.add(closeRack);
    }

    private void lightOrSwitch() {
        Appliance appliance = new Appliance();
        appliance.setApplianceId(1);
        appliance.setDeviceName(1+"灯");
        appliance.setApplianceTypes(YzDevType.LIGHT);
        appliance.setActions(YzDevAction.turnOn,YzDevAction.turnOff);
        virtualDevice.add(appliance);
        appliance = appliance.clone();
        appliance.setApplianceId(2);
        appliance.setApplianceTypes(YzDevType.SWITCH);
        appliance.setDeviceName(2+"开关");
        virtualDevice.add(appliance);
        appliance = appliance.clone();
        appliance.setApplianceId(3);
        appliance.setApplianceTypes(YzDevType.SOCKET);
        appliance.setDeviceName(3+"插座");
        virtualDevice.add(appliance);
    }

    @Override
    public void discoverAppliance(String param) {
        //实例化一个设备对象
        Appliance appliance = new Appliance();
        //设置设备的唯一Id值
        appliance.setApplianceId(1);
        //设置设备名称
        appliance.setDeviceName(1+"灯");
        //设置设备类型
        appliance.setApplianceTypes(YzDevType.LIGHT);
        //设置支持的操作
        appliance.setActions(YzDevAction.turnOn,YzDevAction.turnOff);
        //添加到集合中
        virtualDevice.add(appliance);
        //获取设备完成后调用notifyHost 通知Action为 GET_DEVICE_SUCCESS,返回设备信息
        notifyHost(SdkAction.GET_DEVICE_SUCCESS, JSON.toJSONString(virtualDevice));
    }

    @Override
    public void applianceControl(ControlRequest controlRequest) {
        Appliance appliance = controlRequest.getAppliance();
        ControlResult result = new ControlResult();
        JSONObject jsonObject = new JSONObject();
        switch (controlRequest.getType()){
            case YzRequestCode.TurnOnRequest:
                //设置响应的Code
                result.setResponse(YzResponseCode.TurnOnConfirmation);
                //设置设备属性值为打开
                jsonObject.put(YzAttribute.turnOnState,"ON");
                appliance.setAttributes(jsonObject);
                result.setAppliance(appliance);
                break;
            case YzRequestCode.TurnOffRequest:
                result.setResponse(YzResponseCode.TurnOffConfirmation);
                jsonObject.put(YzAttribute.turnOnState,"OFF");
                appliance.setAttributes(jsonObject);
                result.setAppliance(appliance);
                break;
            case YzRequestCode.PauseRequest:
                result.setResponse(YzResponseCode.PauseConfirmation);
                jsonObject.put(YzAttribute.turnOnState,"OFF");
                appliance.setAttributes(jsonObject);
                result.setAppliance(appliance);
                break;
            case YzRequestCode.SetTemperatureRequest:
                //获取请求的温度值
                int temperature = controlRequest.getValues().getIntValue(YzAttribute.temperature);
                //设置响应的Code
                result.setResponse(YzResponseCode.SetTemperatureConfirmation);
                //设置设备属性值
                jsonObject.put(YzAttribute.temperature,temperature);
                appliance.setAttributes(jsonObject);
                result.setAppliance(appliance);
                break;
            case YzRequestCode.SetModeRequest:
                result.setResponse(YzResponseCode.SetModeConfirmation);
                String mode = controlRequest.getValues().getString(YzAttribute.mode);
                jsonObject.put(YzAttribute.mode,mode);
                appliance.setAttributes(jsonObject);
                result.setAppliance(appliance);
                break;
            case YzRequestCode.SetFanSpeedRequest:
                result.setResponse(YzResponseCode.SetFanSpeedConfirmation);
                if(controlRequest.getValues().containsKey(YzAttribute.fanSpeedLevel)){
                    //设置的是档位值
                    String fanSpeedLevel = controlRequest.getValues().getString(YzAttribute.fanSpeedLevel);
                    jsonObject.put(YzAttribute.fanSpeedLevel,fanSpeedLevel);
                }else{
                    //设置的是具体值
                    String fanSpeedValue = controlRequest.getValues().getString(YzAttribute.fanSpeedValue);
                    jsonObject.put(YzAttribute.fanSpeedLevel,fanSpeedValue);
                }
                appliance.setAttributes(jsonObject);
                result.setAppliance(appliance);
                break;
            case YzRequestCode.SetColorRequest:
                result.setResponse(YzResponseCode.SetColorConfirmation);
                IotColor color = controlRequest.getValues().getObject(YzAttribute.color,IotColor.class);
                jsonObject.put(YzAttribute.color,color);
                appliance.setAttributes(jsonObject);
                result.setAppliance(appliance);
                break;
        }
        notifyHost(SdkAction.CONTROLLER_SUCCESS, JSON.toJSONString(result));
    }

    @Override
    public SdkInfoConfig getSdkConfig() {
        KLog.e("获取配置 getSdkConfig");
        return config;
    }

    @Override
    public void login(String user, String pwd) {
        JSONObject result = new JSONObject();
        if("admin".equals(user) && "123456".equals(pwd)){
            result.put("isSuccess",true);
            result.put("info","登录成功");
        }else{
            result.put("isSuccess",false);
            result.put("info","登录失败");
        }
        notifyHost(SdkAction.SDK_LOGIN,result.toJSONString());
    }
}
