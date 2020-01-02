package com.youzhuan.smarthomeappdemo;

import android.util.Log;

import com.youzhuan.constant.NameSpace;
import com.youzhuan.device.Controller;
import com.youzhuan.device.MainController;
import com.youzhuan.protocol.ConfirmPayload;
import com.youzhuan.protocol.Header;
import com.youzhuan.protocol.Protocol;
import com.youzhuaniot.action.ISmartDeviceAction;
import com.youzhuaniot.action.ISmartHostAction;
import com.youzhuaniot.action.ISmartLoginAction;
import com.youzhuaniot.action.ISmartSceneAction;
import com.youzhuaniot.callback.IResultCallBack;
import com.youzhuaniot.common.utils.JsonUtil;
import com.youzhuaniot.constatnt.Status;
import com.youzhuaniot.constatnt.YzAction;
import com.youzhuaniot.constatnt.YzDevType;
import com.youzhuaniot.entity.YzSmartHomeInfo;
import com.youzhuaniot.entity.v2.Appliance;

import java.util.ArrayList;
import java.util.List;

/**
 * Action  具体实现类
 */
public class SmartHomeActionImp implements ISmartHostAction,
		ISmartLoginAction, ISmartDeviceAction, ISmartSceneAction {

	private static final String TAG = SmartHomeActionImp.class.getName();
	/**
	 * 回调对象
	 */
	private IResultCallBack mResult;
	/**
	 * 设备列表集合（Demo为虚拟数据,正式开放请请求网关服务获取真实设备数据,并转换为此类对象）
	 */
	private List<Appliance> devices;
	private List<Appliance> scenes;
	/**
	 * 设备控制具体实现对象,是一个接口
	 * 由于本身智能家居协议控制逻辑比较复杂,所以通过接口来控制比较方便
	 * 通过Controller 中的 execute方法在里面进行扩展
	 */
	private Controller mController = new MainController();
	private Protocol protocol;

	/**
	 * 获取智能家居服务信息
	 */
	@Override
	public YzSmartHomeInfo getSmartHomeInfo() {
		return null;
	}

	/**
	 * 暂时不需要实现
	 */
	@Override
	public void findHost() {

	}
	/**
	 * 暂时不需要实现
	 */
	@Override
	public void unBindForHost() {

	}
	/**
	 * 初始化服务,用户第一次上电开机启动程序时会调用此方法
	 */
	@Override
	public void init() {
		/**
		 * 需要进行初始化,或者前置处理 在这里进行
		 */
		protocol = new Protocol();
		//添加虚拟设备
		initVirtualDevices();
		//添加虚拟场景
		initVirtualScenes();
		//初始化完成后通知播放器初始化完成
		mResult.onResult(YzAction.ACTION_INIT, Status.INIT_SUCCESS.name());
	}




	/**
	 * 添加通知主机层的回调接口
	 */
	@Override
	public void addResultCallBack(IResultCallBack iResultCallBack) {
		Log.d(TAG, "addResultCallBack: [添加通知主机层的回调接口]");
		mResult = iResultCallBack;
	}

	/**
	 * 登录  由客户实现自己的登录逻辑，用户上层UI触发登录事件时调用
	 */
	@Override
	public void login() {
		//如果不需要登录直接在getLoginState  返回LOGIN_SUCCESS 即可
		//登录的方式由客户自行实现，OAuth2  账户密码登录
		// 播放器不提供登录类型的窗口，只提供登录的按钮，需要登录时用户点击登录触发此方法
		//窗口由此App 实现
	}
	/**
	 * 登出
	 */
	@Override
	public void logout() {

	}

	/**
	 * 获取登录状态
	 */
	@Override
	public void getLoginState() {
		//上层App 会根据当前登录的状态判断是否需要登录
		//需要会调用login   不需要则会直接去调用getAllDev
		mResult.onResult(YzAction.ACTION_GET_LOGIN_STATE, Status.LOGIN_SUCCESS.name());
	}

	/**
	 * 获取所有设备
	 */
	@Override
	public void getAllDevice() {
		//组装响应协议
		Header header = new Header();
		header.setNamespace(NameSpace.Discovery);
		header.setName("DiscoverAppliancesResponse");
		protocol.setHeader(header);
		ConfirmPayload payload = new ConfirmPayload();
		payload.setDiscoveredAppliances(devices);
		protocol.setPayload(payload);
		//数据返回给上层App更新数据
		mResult.onResult(YzAction.ACTION_GET_ALL_DEV, JsonUtil.toJson(protocol));
	}


	/**
	 *设备控制
	 */
	@Override
	public void ctrlDevice(String s) {
		Log.d(TAG, "设备控制消息: "+s);
		//解析协议
		Protocol controlProtocol = JsonUtil.fromJson(s,Protocol.class);
		if(controlProtocol!=null){
			//通过设备类型,获取设备控制对象
			mController.execute(controlProtocol,mResult);
		}
	}

	/**
	 *语音目前暂时不支持
	 */
	@Override
	public void voiceCtrlDevice(String s) {

	}

	/**
	 * 暂时不支持
	 */
	@Override
	public void getSensorRecord(String s) {

	}

	/**
	 * 获取所有场景模式
	 */
	@Override
	public void getAllScene() {
		//组装响应协议
		Header header = new Header();
		header.setNamespace(NameSpace.Discovery);
		header.setName("DiscoverAppliancesResponse");
		protocol.setHeader(header);
		ConfirmPayload payload = new ConfirmPayload();
		payload.setDiscoveredAppliances(devices);
		protocol.setPayload(payload);
		//数据返回给上层App更新数据
		mResult.onResult(YzAction.ACTION_GET_SCENE_LIST, JsonUtil.toJson(protocol));
	}

	@Override
	public void execScene(String scene) {
		Log.d(TAG, "execScene: [scene]"+scene);
	}

	/**
	 * 添加虚拟设备数据
	 */
	private void initVirtualDevices() {
		devices = new ArrayList<>();
		//添加虚拟设备数据
		for(int i = 1;i<=15;i++){
			//appliance  具体说明参考智能家居协议中的发现设备说明
			Appliance appliance = new Appliance();
			if(i < 2){
				appliance.setApplianceId("switch"+i);
				appliance.setFriendlyName("智能开关"+i);
				appliance.setApplianceTypes(YzDevType.SWITCH);
			}else if(i < 4){
				appliance.setApplianceId("socket"+i);
				appliance.setFriendlyName("插座"+i);
				appliance.setApplianceTypes(YzDevType.SOCKET);
			}else if(i < 6){
				appliance.setApplianceId("curtain"+i);
				appliance.setFriendlyName("窗帘"+i);
				appliance.setApplianceTypes(YzDevType.CURTAIN);
			}else if(i < 9){
				appliance.setApplianceId("air_condition"+i);
				appliance.setFriendlyName("空调"+i);
				appliance.setApplianceTypes(YzDevType.AIR_CONDITION);
			}else{
				appliance.setApplianceId("light"+i);
				appliance.setFriendlyName("灯"+i);
				appliance.setApplianceTypes(YzDevType.LIGHT);
				if(i == 11){
					//如果你的灯具有RGB属性可以通过设置Action来告诉主机，这样可以进入具体的操作页面
					appliance.setActions("setColor");
				}
			}
			devices.add(appliance);
		}
	}


	/**
	 * 添加虚拟场景
	 */
	private void initVirtualScenes() {
		for(int i = 0; i <10;i++){
			Appliance appliance = new Appliance();
			//SCENE_TRIGGER 代表场景
			appliance.setApplianceId("scene"+i);
			appliance.setFriendlyName("虚拟场景:"+i);
			appliance.setApplianceTypes(YzDevType.SCENE_TRIGGER);
			devices.add(appliance);
		}
	}
}
