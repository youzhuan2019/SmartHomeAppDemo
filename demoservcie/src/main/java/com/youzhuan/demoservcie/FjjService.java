package com.youzhuan.demoservcie;

import android.content.Context;

import com.youzhuan.iot.YzIotService;
import com.youzhuan.iot.model.ControlRequest;
import com.youzhuan.iot.model.SdkInfoConfig;

/**
 * @author 樱花满地集于我心
 * Create By 2020-07-13
 * 富家居Iot服务
 */
public class FjjService extends YzIotService {

    private static final String TAG = FjjService.class.getName();
    

    private SdkInfoConfig sdkInfoConfig;
    private FjjManager fjjManager;
    @Override
    public void onCreate() {
        super.onCreate();
        fjjManager = FjjManager.getInstance();
        NotifyManager.getInstance().setYzIotService(this);
    }

    @Override
    public void discoverAppliance(String s) {
        //获取网关列表
        fjjManager.queryDevices();
    }

    @Override
    public void applianceControl(ControlRequest controlRequest) {
        fjjManager.ctrl(controlRequest);
    }

    @Override
    public void login(String user, String pwd) {
        //用户登录成功之后会返回网关列表广播，有时候可能会慢一点
        fjjManager.userLogin("17680440422","ljx19960723");
    }

    @Override
    public void init(Context context) {
        fjjManager.init();
        sdkInfoConfig = new SdkInfoConfig();
        sdkInfoConfig.setManufacturerName("富家居智能家居");
        sdkInfoConfig.setSupportLogin(true);
    }

    @Override
    public SdkInfoConfig getSdkConfig() {
        return sdkInfoConfig;
    }
}
