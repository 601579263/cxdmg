package com.cxdmg.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
/**
 * 讯飞语音听写
 * @author 60157
 *
 */
public class KdxfUtil {

	public static String httpPostWithJSON(String url) throws Exception {

		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		
		// 这两个参数改成自己的，不然IP进不了白名单也是不能调用的
		String appId = "5c25dd39";							
		String apiKey = "778e4a90b37c5d6b9309c0ff6fc20dd8";

		String parm = "{\"engine_type\": \"sms16k\",\"aue\": \"raw\"}";

		String x_param = new String(Base64.encodeBase64(parm.getBytes("UTF-8"))); 
		
		String x_time = System.currentTimeMillis() / 1000L + "";
		String checksum = apiKey + x_time + x_param;
		String x_checksum = getMD5(checksum).toLowerCase();

		httpPost.setHeader("X-Appid", appId);
		httpPost.setHeader("X-CurTime", x_time);
		httpPost.setHeader("X-Param", x_param);
		httpPost.setHeader("X-CheckSum", x_checksum);

		File file = new File("D:/新建文件夹/demo.wav");
		String audio = null;
		try {
			InputStream is = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(is);
			audio = Base64.encodeBase64String(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
		pairList.add(new BasicNameValuePair("audio", audio));
		StringEntity entity = new UrlEncodedFormEntity(pairList, "utf-8");
		entity.setContentType("application/x-www-form-urlencoded; charset=utf-8");
		httpPost.setEntity(entity);
		
		HttpResponse resp = client.execute(httpPost);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he, "UTF-8");
		}
		return respContent;
	}


    public static String getMD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
	
	public static void main(String[] args) throws Exception {
		String result = httpPostWithJSON("http://api.xfyun.cn/v1/service/v1/iat");
		System.out.println(result);
	}
}
