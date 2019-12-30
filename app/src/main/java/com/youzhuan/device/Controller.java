package com.youzhuan.device;

import com.youzhuan.protocol.Protocol;
import com.youzhuaniot.callback.IResultCallBack;

/**
 * 智能家居设备控制接口
 */
public interface Controller {

	//执行
	void execute(Protocol protocol, IResultCallBack resultCallBack);

}
