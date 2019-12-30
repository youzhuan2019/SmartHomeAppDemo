package com.youzhuan.smarthomeappdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.youzhuaniot.action.ISmartAction;
import com.youzhuaniot.action.ISmartDeviceAction;
import com.youzhuaniot.action.ISmartHostAction;
import com.youzhuaniot.action.ISmartLoginAction;
import com.youzhuaniot.action.ISmartSceneAction;
import com.youzhuaniot.aidl.AidlCallBack;
import com.youzhuaniot.aidl.YzActionAidl;
import com.youzhuaniot.callback.IResultCallBack;
import com.youzhuaniot.common.utils.JsonUtil;
import com.youzhuaniot.constatnt.YzAction;

import androidx.annotation.Nullable;

public class SmartHomeService extends Service {

	private static final String TAG = "SmartHomeService";

	private ISmartAction mAction;
	private AidlCallBack mAidlCallBack;

	@Override
	public void onCreate() {
		super.onCreate();
		mAction = new SmartHomeActionImp();
		mAction.addResultCallBack(new IResultCallBack() {
			@Override
			public void onResult(int action, String json) {
				Log.d(TAG, "回调到上层服务：[action]"+action);
				Log.d(TAG, "数据：[json]"+json);
				if(mAidlCallBack!=null){
					try {
						mAidlCallBack.onResult(action,json);
					} catch (RemoteException e) {
						Log.d(TAG, "回调失败,AIDL 远程通信异常");
						e.printStackTrace();
					}
					return;
				}
				Log.d(TAG, "回调失败,mAidlCallBack is null");
			}
		});
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return new YzActionAidl.Stub() {
			@Override
			public void doAction(int action, String json) throws RemoteException {
				switch (action){
					//初始化服务
					case YzAction.ACTION_INIT:
						((ISmartHostAction) mAction).init();
						break;
					//获取服务相关信息
					case YzAction.ACTION_GET_SMART_INFO:
						mAidlCallBack.onResult(YzAction.ACTION_GET_SMART_INFO,
								JsonUtil.toJson(((ISmartHostAction) mAction).getSmartHomeInfo()));
						break;
					//获取登录状态
					case YzAction.ACTION_GET_LOGIN_STATE:
						((ISmartLoginAction) mAction).getLoginState();
						break;
					//登录事件
					case YzAction.ACTION_LOGIN:
						((ISmartLoginAction) mAction).login();
						break;
					//登出事件
					case YzAction.ACTION_LOGOUT:
						((ISmartLoginAction) mAction).logout();
						break;
					//获取所有的设备
					case YzAction.ACTION_GET_ALL_DEV:
						((ISmartDeviceAction) mAction).getAllDevice();
						break;
					//控制设备
					case YzAction.ACTION_CTRL_DEV:
						((ISmartDeviceAction) mAction).ctrlDevice(json);
						break;
					//获取场景列表
					case YzAction.ACTION_GET_SCENE_LIST:
						((ISmartSceneAction) mAction).getAllScene();
						break;
					//执行场景
					case YzAction.ACTION_EXEC_SCENE_:
						((ISmartSceneAction) mAction).execScene(json);
						break;
				}
			}

			@Override
			public void addCallBack(AidlCallBack aidlCallBack) throws RemoteException {
				mAidlCallBack = aidlCallBack;
			}
		};
	}
}
