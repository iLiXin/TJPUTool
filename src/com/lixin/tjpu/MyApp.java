package com.lixin.tjpu;

import org.apache.http.client.CookieStore;

import android.app.Application;

public class MyApp extends Application{
	private CookieStore cookies;

	public CookieStore getCookies() {
		return cookies;
	}

	public void setCookies(CookieStore cookies) {
		this.cookies = cookies;
	}
	
}
