package com.cxdmg.config;

import java.io.IOException;


import org.json.JSONException;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

/**
 * 腾讯云短信工具类
 * @author 60157
 *
 */
public class SmsUtils {

	// 短信应用SDK AppID
	// 1400开头
	private static int appid = 1400170993; 
	// 短信应用SDK AppKey
	private static String appkey = "9967d29daf2f900085638f62865b1f16";
	// NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
	private static String smsSign = "小逗的一天";
	
	public static String sendSms(String[] params,String[] phoneNumbers,int templateId) {
		try {
		    SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
		    SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
		    	// 签名参数未提供或者为空时，会使用默认签名发送短信
		        templateId, params, smsSign, "", "");  
		    System.out.println(result);
		    return result.toString();
		} catch (HTTPException e) {
		    // HTTP响应码错误
		    e.printStackTrace();
		} catch (JSONException e) {
		    // json解析错误
		    e.printStackTrace();
		} catch (IOException e) {
		    // 网络IO错误
		    e.printStackTrace();
		}
		return "";
	}
	
}
