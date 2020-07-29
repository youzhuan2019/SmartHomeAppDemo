package com.youzhuan.demoservcie;

import com.smarthome.main.model.bean.HwElectricInfo;

/**
 * @author 樱花满地集于我心
 * Create By 2020-07-23
 * 用于快速搜索HwElectricInfo
 */
public class HashHwElectricInfo {

    private HwElectricInfo hwElectricInfo;

    public HwElectricInfo getHwElectricInfo() {
        return hwElectricInfo;
    }

    public void setHwElectricInfo(HwElectricInfo hwElectricInfo) {
        this.hwElectricInfo = hwElectricInfo;
    }

    @Override
    public int hashCode() {
        if(hwElectricInfo == null){
            return -1;
        }
        byte[] id = hwElectricInfo.getDeviceId();
        if(id == null){
            return -1;
        }
        return Hex.bytesToHex(id).hashCode();
    }
}
