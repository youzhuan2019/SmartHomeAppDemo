package com.youzhuan.demoservcie;

import com.socks.library.KLog;
import com.youzhuan.iot.YzIotService;

/**
 * @author 樱花满地集于我心
 * Create By 2020-07-13
 * 全局的通知管理对象
 */
public class NotifyManager {

    private static final String TAG = NotifyManager.class.getName();
    
    private NotifyManager(){}

    private static final NotifyManager INSTANCE = new NotifyManager();
    private YzIotService service;
    public static NotifyManager getInstance() {
        return INSTANCE;
    }

    void setYzIotService(YzIotService service){
        this.service = service;
    }

    public void notifyHost(int action,String json){
        if(service!=null){
            KLog.e(TAG,"通知主机更新数据action:"+action+",data:"+json);
            service.notifyHost(action,json);
        }
    }

}
