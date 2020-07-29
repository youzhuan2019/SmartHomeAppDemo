package com.youzhuan.demoservcie;

import android.app.Service;
import android.content.Context;
import android.os.Environment;

import com.alibaba.fastjson.JSONObject;
import com.smarthome.main.HwVoiceHandle;
import com.smarthome.main.constant.HwConstantType;
import com.smarthome.main.model.bean.HwGatewayInfo;
import com.youzhuan.iot.YzIotService;
import com.youzhuan.iot.constant.SdkAction;
import com.youzhuan.iot.model.ControlRequest;
import com.youzhuan.iot.model.SdkInfoConfig;

import java.util.List;

/**
 * @author 樱花满地集于我心
 * Create By 2020-07-13
 * 富家居Iot服务
 */
public class FjjService extends YzIotService {

    private static final String TAG = FjjService.class.getName();
    

    private SdkInfoConfig sdkInfoConfig;
    private Context context;
    private HjjManager hjjManager;
    @Override
    public void onCreate() {
        super.onCreate();
        hjjManager = HjjManager.getInstance();
        NotifyManager.getInstance().setYzIotService(this);
    }

    @Override
    public void discoverAppliance(String s) {
        //获取网关列表
        hjjManager.queryDevices();
    }

    @Override
    public void applianceControl(ControlRequest controlRequest) {
        hjjManager.ctrl();
    }

    @Override
    public void login(String user, String pwd) {
        //用户登录成功之后会返回网关列表广播，有时候可能会慢一点
        if(!hjjManager.isLogin()){
            hjjManager.userLogin("17680440422","ljx19960723");
        }else{
            JSONObject result = new JSONObject();
            result.put("isSuccess",true);
            result.put("info","登录成功");
            notifyHost(SdkAction.SDK_LOGIN,result.toJSONString());
        }
    }

    @Override
    public void init(Context context) {
        this.context = context;
        hjjManager.init();
        sdkInfoConfig = new SdkInfoConfig();
        sdkInfoConfig.setManufacturerName("富家居智能家居");
        sdkInfoConfig.setSupportLogin(true);
    }

    @Override
    public SdkInfoConfig getSdkConfig() {
        return sdkInfoConfig;
    }
}
