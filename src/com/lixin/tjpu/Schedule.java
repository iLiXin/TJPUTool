package com.lixin.tjpu;

/** 课表详情页面*/

import java.util.List;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Schedule extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule);
		String url = "http://jwpt.tjpu.edu.cn:8081/xkAction.do?actionType=6";
		
		//取得cookies并加入请求中
		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		MyApp myCookie = (MyApp) getApplication();
		CookieStore cookie = myCookie.getCookies();
		cookie = myCookie.getCookies();
		List<Cookie> cookies = cookie.getCookies();
		if (cookies.isEmpty()) {     
			System.out.println("cookie为空啊");
		}else{
			
			String cookieString = cookies.get(0).getName() + "=" + cookies.get(0).getValue() + "; domain=" + cookies.get(0).getDomain();
			cookieManager.setCookie(url, cookieString);
			CookieSyncManager.getInstance().sync();
		}
		
		//直接显示网页
		WebView webView = (WebView) findViewById(R.id.webView);
//		webView.getSettings().setJavaScriptEnabled(true); 
		webView.setWebViewClient(new WebViewClient(){
			public boolean shouldOverrideUrlLoading(WebView view, String url) {       
                view.loadUrl(url);       
                return true;
			}
		});
		webView.loadUrl(url);
		
	}
	
	
	//后台统计
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
	
}
