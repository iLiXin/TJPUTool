package com.lixin.tjpu;

import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Schedule extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule);
		String url = "http://jwpt.tjpu.edu.cn:8081/xkAction.do?actionType=6";
		
		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		MyApp myCookie = (MyApp) getApplication();
		CookieStore cookie = myCookie.getCookies();
		cookie = myCookie.getCookies();
		List<Cookie> cookies = cookie.getCookies();
		if (cookies.isEmpty()) {     
			System.out.println("cookieÎª¿Õ°¡");
		}else{
			
			String cookieString = cookies.get(0).getName() + "=" + cookies.get(0).getValue() + "; domain=" + cookies.get(0).getDomain();
			cookieManager.setCookie(url, cookieString);
			CookieSyncManager.getInstance().sync();
		}
		
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
	
}
