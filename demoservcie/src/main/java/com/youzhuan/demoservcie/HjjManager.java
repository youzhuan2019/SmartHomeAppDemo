package com.youzhuan.demoservcie;

import android.content.Context;
import android.os.Environment;

import com.smarthome.main.HwVoiceHandle;
import com.smarthome.main.constant.HwConstantType;
import com.smarthome.main.model.bean.HwElectricInfo;
import com.smarthome.main.model.bean.HwGatewayInfo;

import java.util.List;

/**
 * @author 樱花满地集于我心
 * Create By 2020-07-22
 * 富家居
 */
public class HjjManager {
    private static final String TAG = HjjManager.class.getName();
    //单例对象
    private static HjjManager instance;
    private HwVoiceHandle hwVoiceHandle;
    private boolean isLogin;
    //私有构造方法
    private HjjManager(){
        hwVoiceHandle = new HwVoiceHandle(App.getInstance());
    }
    //获取实例
    public static HjjManager getInstance(){
        if(instance == null){
            synchronized(HjjManager.class){
                if(instance == null){
                    instance = new HjjManager();
                }
            }
        }
        return instance;
    }

    public HwVoiceHandle getHwVoiceHandle() {
        return hwVoiceHandle;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public HwElectricInfo getDeviceById(String id){
        for (HwElectricInfo hwElectricInfo : hwVoiceHandle.getDeviceInfo()) {
            String idHex = Hex.bytesToHex(hwElectricInfo.getDeviceId());
            if(idHex.equals(id)){
                return hwElectricInfo;
            }
        }
        return null;
    }

    /**
     * 查询设备
     */
    public void queryDevices() {
        //先获取到网关信息
        List<HwGatewayInfo> listGateway = hwVoiceHandle.getGatewayInfo();
        for (HwGatewayInfo hwGatewayInfo : listGateway) {
            if(hwGatewayInfo.isOnLine()){
                //登录网关
                hwVoiceHandle.loginGateway(hwGatewayInfo);
            }else{
                //提示网关未在线
                ToastUtils.showToast(hwGatewayInfo.getGatewayName() + "未在线");
            }
        }
    }

    public void userLogin(String user, String pwd) {
        hwVoiceHandle.userLogin(App.getInstance(),user,pwd);
    }
    private String fileAddress = String.valueOf(Environment.getExternalStorageDirectory());

    public void init() {
        hwVoiceHandle.init(fileAddress,"47.104.128.130");
    }

    /**
     * 设备控制
     * @param devId
     */
    public void ctrl(String devId) {
        HwElectricInfo info = getDeviceById(devId);
        hwVoiceHandle.ctlDevice(HwConstantType.VOICE_CLOSE_TYPE,info);
    }
}
