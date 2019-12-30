package com.youzhuan.device;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.youzhuan.constant.NameSpace;
import com.youzhuan.constant.RequestName;
import com.youzhuan.protocol.ConfirmPayload;
import com.youzhuan.protocol.Header;
import com.youzhuan.protocol.Payload;
import com.youzhuan.protocol.Protocol;
import com.youzhuaniot.callback.IResultCallBack;
import com.youzhuaniot.common.utils.JsonUtil;
import com.youzhuaniot.constatnt.YzAction;
import com.youzhuaniot.constatnt.YzAttribute;
import com.youzhuaniot.entity.v2.Appliance;
import com.youzhuaniot.entity.v2.Attribute;

/**
 * 控制实现类
 */
public class MainController implements Controller {

	private static final String TAG = MainController.class.getName();
	private IResultCallBack resultCallBack;
	private Protocol protocol;
	private Appliance appliance;
	@Override
	public void execute(Protocol protocol, IResultCallBack resultCallBack) {
		this.protocol = protocol;
		appliance = JSON.parseObject(protocol.getPayload().toString())
				.getJSONObject("appliance")
				.toJavaObject(Appliance.class);
		this.resultCallBack = resultCallBack;
		RequestName name = RequestName.valueOf(protocol.getHeader().getName());
		switch (name){
			case TurnOnRequest:
				turnOn();
				break;
			case TurnOffRequest:
				turnOff();
				break;
			case PauseRequest:
				pause();
				break;
			case SetTemperatureRequest:
				setTemperature();
				break;
			case SetFanSpeedRequest:
				setFanSpeed();
				break;
			case SetModeRequest:
				setModeRequest();
				break;
			default:
				break;
		}
	}

	private void setModeRequest() {
		//收到打开命令后,控制设备打开,返回信息成功后,对应上报信息
		//组装协议
		Header header = new Header();
		header.setName(RequestName.SetModeConfirmation.name());
		header.setNamespace(NameSpace.Control);
		JSONObject mode = JSON.parseObject(protocol.getPayload().toString())
				.getJSONObject("mode");
		//获取温度信息
		String modeValue = mode.getString("value");
		String deviceType = mode.getString("deviceType");
		ConfirmPayload confirmPayload = new ConfirmPayload();
		confirmPayload.setMode("deviceType",deviceType);
		confirmPayload.setMode("value",modeValue);
		confirmPayload.setApplianceId(appliance.getApplianceId());
		protocol.setHeader(header);
		protocol.setPayload(confirmPayload);
		//上报属性
		resultCallBack.onResult(YzAction.ACTION_REPORT_DEVICE_STATUS, JsonUtil.toJson(protocol));
	}

	private void setFanSpeed() {
		//收到打开命令后,控制设备打开,返回信息成功后,对应上报信息
		//组装协议
		Header header = new Header();
		header.setName(RequestName.SetFanSpeedConfirmation.name());
		header.setNamespace(NameSpace.Control);
		JSONObject fanSpeed = JSON.parseObject(protocol.getPayload().toString())
				.getJSONObject("fanSpeed");
		//获取温度信息
		int fanSpeedValue = fanSpeed.getIntValue("value");
		String fanSpeedLevel = fanSpeed.getString("level");
		ConfirmPayload confirmPayload = new ConfirmPayload();
		if(fanSpeedLevel != null){
			confirmPayload.setFanSpeed("level",fanSpeedLevel);
		}else{
			confirmPayload.setFanSpeed("value",fanSpeedValue);
		}
		confirmPayload.setApplianceId(appliance.getApplianceId());
		protocol.setHeader(header);
		protocol.setPayload(confirmPayload);
		//上报属性
		resultCallBack.onResult(YzAction.ACTION_REPORT_DEVICE_STATUS, JsonUtil.toJson(protocol));
	}

	private void setTemperature() {
		//收到打开命令后,控制设备打开,返回信息成功后,对应上报信息
		//组装协议
		Header header = new Header();
		header.setName(RequestName.SetTemperatureConfirmation.name());
		header.setNamespace(NameSpace.Control);
		JSONObject targetTemperature = JSON.parseObject(protocol.getPayload().toString())
				.getJSONObject("targetTemperature");
		//获取温度信息
		int targetTemperatureValue = targetTemperature.getIntValue("value");
		String targetTemperatureScale = targetTemperature.getString("scale");
		Log.d(TAG, "targetTemperatureValue: []"+targetTemperatureValue);
		Log.d(TAG, "targetTemperatureScale: []"+targetTemperatureScale);
		ConfirmPayload confirmPayload = new ConfirmPayload();
		confirmPayload.setTemperature("value",targetTemperatureValue);
		confirmPayload.setMode("value","AUTO");
		confirmPayload.setApplianceId(appliance.getApplianceId());
		protocol.setHeader(header);
		protocol.setPayload(confirmPayload);
		//上报属性
		resultCallBack.onResult(YzAction.ACTION_REPORT_DEVICE_STATUS, JsonUtil.toJson(protocol));
	}

	private void pause() {
		//收到打开命令后,控制设备打开,返回信息成功后,对应上报信息
		//组装协议
		Header header = new Header();
		header.setName(RequestName.PauseConfirmation.name());
		header.setNamespace(NameSpace.Control);
		ConfirmPayload payload = new ConfirmPayload();
		payload.setApplianceId(appliance.getApplianceId());
		protocol.setHeader(header);
		protocol.setPayload(payload);
		//上报属性
		resultCallBack.onResult(YzAction.ACTION_REPORT_DEVICE_STATUS, JsonUtil.toJson(protocol));
	}

	/**
	 *  开处理
	 */
	private void turnOn() {
		//收到打开命令后,控制设备打开,返回信息成功后,对应上报信息
		Attribute attribute = new Attribute();
		attribute.setName(YzAttribute.turnOnState);
		attribute.setValue("ON");
		//组装协议
		Header header = new Header();
		header.setName(RequestName.TurnOnConfirmation.name());
		header.setNamespace(NameSpace.Control);
		ConfirmPayload payload = new ConfirmPayload();

		payload.setApplianceId(appliance.getApplianceId());
		payload.addAttribute(attribute);
		protocol.setHeader(header);
		protocol.setPayload(payload);
		//上报属性
		resultCallBack.onResult(YzAction.ACTION_REPORT_DEVICE_STATUS, JsonUtil.toJson(protocol));
	}

	/**
	 *  关处理
	 */
	private void turnOff() {
		//收到打开命令后,控制设备打开,返回信息成功后,对应上报信息
		Attribute attribute = new Attribute();
		attribute.setName(YzAttribute.turnOnState);
		attribute.setValue("OFF");
		//组装协议
		Header header = new Header();
		header.setName(RequestName.TurnOffConfirmation.name());
		header.setNamespace(NameSpace.Control);
		ConfirmPayload payload = new ConfirmPayload();

		payload.setApplianceId(appliance.getApplianceId());
		payload.addAttribute(attribute);

		protocol.setHeader(header);
		protocol.setPayload(payload);
		//上报属性
		resultCallBack.onResult(YzAction.ACTION_REPORT_DEVICE_STATUS, JsonUtil.toJson(protocol));
	}


}
