package com.youzhuan.demoservcie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smarthome.main.HwVoiceHandle;
import com.smarthome.main.constant.HwConstantSendDeviceCmd;
import com.smarthome.main.constant.HwConstantType;
import com.smarthome.main.model.bean.HwElectricInfo;
import com.smarthome.main.model.bean.HwGatewayInfo;
import com.socks.library.KLog;
import com.youzhuan.iot.constant.SdkAction;
import com.youzhuan.iot.constant.YzAttribute;
import com.youzhuan.iot.model.Appliance;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 樱花满地集于我心
 * Create By 2020-07-13
 * 富家居广播接收对象
 */
public class HwBroadcastReceiver extends BroadcastReceiver {

    private NotifyManager notifyManager;
    private ShareUtil mShare;

    private static final String TAG = HwBroadcastReceiver.class.getName();
    private HwVoiceHandle hwVoiceHandle;
    private FjjManager mFjjManager;

    public HwBroadcastReceiver() {
        mFjjManager =  FjjManager.getInstance();
        hwVoiceHandle = mFjjManager.getHwVoiceHandle();
        this.notifyManager = NotifyManager.getInstance();
        mShare = ShareUtil.getInstance(App.getInstance());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent == null || intent.getAction() == null){
            return;
        }
        KLog.e(TAG,"收到的广播:"+intent.getAction());
        switch (intent.getAction()){
            case HwConstantType.ACTION_INIT_SUCCESS:
                KLog.e(TAG,"初始化成功");
                if(mShare.getBoolean(ShareUtil.IS_LOGIN)){
                    mFjjManager.userLogin(mShare.getString(ShareUtil.USER),mShare.getString(ShareUtil.PWD));
                }
                break;
            case HwConstantType.ACTION_INIT_FAILED:
                KLog.e(TAG,"初始化失败");
                break;
            case HwConstantType.ACTION_USER_LOGIN_SUCCESS:
                KLog.e(TAG,"用户登录成功");
                JSONObject result = new JSONObject();
                result.put("isSuccess",true);
                result.put("info","登录成功");
                mShare.put(ShareUtil.IS_LOGIN,true);
                notifyManager.notifyHost(SdkAction.SDK_LOGIN,result.toJSONString());
                break;
            case HwConstantType.ACTION_USER_LOGIN_ERROR:
                KLog.e(TAG,"用户登录失败");
                mShare.put(ShareUtil.IS_LOGIN,false);
                result = new JSONObject();
                result.put("isSuccess",false);
                result.put("info","登录失败");
                notifyManager.notifyHost(SdkAction.SDK_LOGIN,result.toJSONString());
                break;
            case HwConstantType.ACTION_GATEWAY_LIST_ERROR:
                KLog.e(TAG,"网关列表返回失败");
                break;
            case HwConstantType.ACTION_GATEWAY_LIST_SUCCESS:
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
                break;
            case HwConstantType.ACTION_GATEWAY_LOGIN_SUCCESS:
                ToastUtils.showToast("网关登录成功");
                KLog.e(TAG,"网关登录成功");
                List<HwElectricInfo> devicesInfo = hwVoiceHandle.getDeviceInfo();
                List<Appliance> appliances = new ArrayList<>();
                for (HwElectricInfo hwElectricInfo : devicesInfo) {
                    String type;
                    if((type = App.getInstance().getType(""+hwElectricInfo.getDeviceType()))!=null){
                        Appliance appliance = new Appliance();
                        appliance.setApplianceId(Hex.bytesToHex(hwElectricInfo.getDeviceId()));
                        appliance.setDeviceName(hwElectricInfo.getDeviceNames());
                        appliance.setApplianceTypes(type);
                        appliances.add(appliance);
                    }
                }
                notifyManager.notifyHost(SdkAction.GET_DEVICE_SUCCESS, JSON.toJSONString(appliances));
                break;
            case HwConstantType.ACTION_GATEWAY_LOGIN_ERROR:
                KLog.e(TAG,"网关登录失败");
                break;
            case HwConstantType.ACTION_DEVICE_STATE_CHANGE:
                int devCode =  intent.getIntExtra("dev_code", 0);
                byte devStatus =  intent.getByteExtra("dev_status", (byte) 0);
                for (HwElectricInfo info : hwVoiceHandle.getDeviceInfo()) {
                    if(devCode == info.getDeviceCode()){
                        ReportAttributes reportAttributes = new ReportAttributes();
                        reportAttributes.setApplianceId(Hex.bytesToHex(info.getDeviceId()));
                        switch (devStatus) {
                            case HwConstantSendDeviceCmd.ON_CMD:
                                reportAttributes.put(YzAttribute.turnOnState,"ON");
                                break;
                            case HwConstantSendDeviceCmd.OFF_CMD:
                                reportAttributes.put(YzAttribute.turnOnState,"OFF");
                                break;
                        }
                        notifyManager.notifyHost(SdkAction.NOTIFY_APPLIANCE_CHANGE,JSON.toJSONString(reportAttributes));
                        return;
                    }
                }
                break;
        }
    }

}
