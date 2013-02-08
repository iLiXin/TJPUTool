package com.lixin.tjpu;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


public class Schedule extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		WebView webView = new WebView(this);
		webView.getSettings().setJavaScriptEnabled(true); 
		webView.loadUrl("http://jwpt.tjpu.edu.cn:8081/xkAction.do?actionType=6");
		
	}
	
}
