/**   
 * @Title: httpUtils.java 
 * @Package com.hutu.net 
 * @Description: TODO
 * @author Long Li  
 * @date 2015-9-28 上午8:39:09 
 * @version V1.0   
 */
package com.leng.fileupdate_new.upload.uploadUtil;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * TODO<请描述这个类是干什么的>
 *
 * @author Long Li
 * @data: 2015-9-28 上午8:39:09
 * @version: V1.0
 */
public class httpUtils {

	private Context mContext = null;
	private int type;
	private String localName;
	private String remoteName;
	private int httpResult = -1;

	public httpUtils(Context mContext, int type, String localName, String remoteName) {
		this.mContext = mContext;
		this.type = type;
		this.localName = localName;
		this.remoteName = remoteName;
	}

	public int getHttpResult() {
		return httpResult;
	}

	public void getHttpRequest() {

		new Thread() {
			public void run() {
				String imei = ((TelephonyManager) mContext
						.getSystemService(mContext.TELEPHONY_SERVICE))
						.getDeviceId();
				//for test
				//String imei = "123456789012345";
				String regCode = "2bgz12yp";
				String fileType = "" + type;
				String localFileName = localName;
				String fileName = remoteName;
//
//				//使用上帝从SharedPreferences里面得到保存的ftp IP地址和端口
//				SharedPreferences ServerSetting = LFBApplication.getApplication().getSharedPreferences("ServerSetting", 0);
//				String databaseAddress=ServerSetting.getString("databaseIp", "");
//				String databasePort=ServerSetting.getString("databasePort", "80");  //默认的是80端口
//
				String baseURL= "http://kc.xun365.net/Manager/CommitFileInfo";
//				//要是没有保存的话就使用软件默认的

				System.out.println("databaseIPandPort=="+baseURL);
				String url = baseURL + "?imei=" + imei + "&regCode=" + regCode
						+ "&fileType=" + type + "&localFileName="
						+ fileName + "&fileName=" + localFileName;
				url = url.replaceAll(" ", "%20");
				
				Log.i("xiaoming", "url is " + url);
				HttpGet httpGet = null;
				HttpClient httpClient = null;
/*				try {
					URL strUrl = new URL(url);
					
					URI uri = new URI(strUrl.getProtocol(), strUrl.getHost(),
							strUrl.getPath(), strUrl.getQuery(), null);

				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				

				httpGet = new HttpGet(url);
				httpClient = new DefaultHttpClient();

				// 生成请求对象

				// 发送请求
				try {

					HttpResponse response = httpClient.execute(httpGet);
					if (response != null) {
						// 显示响应
						HttpEntity httpEntity = response.getEntity();

						InputStream inputStream = httpEntity.getContent();
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(inputStream));
						String result = "";
						String line = "";
						while (null != (line = reader.readLine())) {
							result += line;

						}
						Log.d("xiaoming", "result is " + result);
						if ((result != null) && (result.contains("True"))) {
							httpResult = 1;
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (httpResult != 1) {
					httpResult = 0;
				}
			}
		}.start();
	}
}
