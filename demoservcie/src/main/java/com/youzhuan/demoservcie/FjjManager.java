package com.youzhuan.demoservcie;

import android.os.Environment;

import com.alibaba.fastjson.JSON;
import com.smarthome.main.HwVoiceHandle;
import com.smarthome.main.constant.HwConstantType;
import com.smarthome.main.model.bean.HwElectricInfo;
import com.smarthome.main.model.bean.HwGatewayInfo;
import com.socks.library.KLog;
import com.youzhuan.iot.constant.SdkAction;
import com.youzhuan.iot.constant.YzRequestCode;
import com.youzhuan.iot.model.ControlRequest;

import java.util.List;

/**
 * @author 樱花满地集于我心
 * Create By 2020-07-22
 * 富家居
 */
public class FjjManager {
    private static final String TAG = FjjManager.class.getName();
    //单例对象
    private static FjjManager instance;
    private HwVoiceHandle hwVoiceHandle;
    private NotifyManager mNotifyManager;
    //私有构造方法
    private FjjManager(){
        hwVoiceHandle = new HwVoiceHandle(App.getInstance());
        mNotifyManager = NotifyManager.getInstance();
    }
    //获取实例
    public static FjjManager getInstance(){
        if(instance == null){
            synchronized(FjjManager.class){
                if(instance == null){
                    instance = new FjjManager();
                }
            }
        }
        return instance;
    }

    public HwVoiceHandle getHwVoiceHandle() {
        return hwVoiceHandle;
    }


    public HwElectricInfo getDeviceById(String id){
        for (HwElectricInfo hwElectricInfo : hwVoiceHandle.getDeviceInfo()) {
            String idHex = Hex.bytesToHex(hwElectricInfo.getDeviceId());
            if(idHex.equals(id)){
                return hwElectricInfo;
            }
        }
        KLog.e(TAG,"富家居设备数量:"+hwVoiceHandle.getDeviceInfo().size());
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
     * @param request
     */
    public void ctrl(ControlRequest request) {
        HwElectricInfo info = getDeviceById(request.getAppliance().getApplianceId());
        if(info!=null){
            KLog.e("设备控制："+info.getDeviceNames());
            switch (request.getType()){
                case YzRequestCode.TurnOnRequest:
                    hwVoiceHandle.ctlDevice(HwConstantType.VOICE_OPEN_TYPE,info);
                    break;
                case YzRequestCode.TurnOffRequest:
                    hwVoiceHandle.ctlDevice(HwConstantType.VOICE_CLOSE_TYPE,info);
                    break;
                default:
                    mNotifyManager.notifyHost(SdkAction.CONTROLLER_FAIL, JSON.toJSONString(request.getAppliance()));
            }
        }else{
            queryDevices();
            KLog.e("未找到设备");
        }
    }



}
