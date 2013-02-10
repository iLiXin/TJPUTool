package com.lixin.tjpu;



import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



public class LoginActivity extends Activity{
	private EditText username;
	private EditText password;
	private ImageButton loginButton;
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		textView = (TextView) findViewById(R.id.Tipstxt2);
		
		textView.setText(Html.fromHtml("2.如有问题或建议请联系作者 "+"<a href=\"http://weibo.com/328858558\">@贝加尔湖的最深处</a>"));
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		
		loginButton = (ImageButton) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new LoginListener());
		
		/*loginButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				 if(event.getAction() == MotionEvent.ACTION_DOWN){    
                     //更改为按下时的背景图片    
                     v.setBackgroundResource(R.drawable.login1);    
				 }else if(event.getAction() == MotionEvent.ACTION_UP){    
                     //改为抬起时的图片    
                     v.setBackgroundResource(R.drawable.login);    
             }     
				return false;
			}
		});*/

		
	}
	//判断网路可用状态
	public static final boolean networkIsAvailable(final Context context) {
		NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		return (null != info && info.isAvailable());
	}
	
	class LoginListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			
			
			String uname = username.getText().toString();
			String upwd = password.getText().toString();
			             
			String baseUrl = "http://jwpt.tjpu.edu.cn:8081/loginAction.do?zjh="+ uname +"&mm="+upwd;  
			
			
			
			
			HttpGet getMethod = new HttpGet(baseUrl);  
			              
			HttpClient httpClient = new DefaultHttpClient();  
			  
		 try{
			    HttpResponse response = httpClient.execute(getMethod); //发起GET请求  
			  
			    int resCode = response.getStatusLine().getStatusCode(); //获取响应码  
			    String result = EntityUtils.toString(response.getEntity(), "utf-8");//获取服务器响应内容 
			    System.out.println(resCode);
//			    System.out.println(result);
			    
			if(result.contains("学分制综合教务")){
			
			
			//保存cookie
			CookieStore cookies = ((AbstractHttpClient)httpClient).getCookieStore();
			MyApp myCookie = (MyApp) getApplication();
			myCookie.setCookies(cookies);
			
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, TjpuActivity.class);
			LoginActivity.this.startActivity(intent);
			finish();
			}
			
			else{
				Toast.makeText(getApplicationContext(), "登录失败",
						Toast.LENGTH_SHORT).show();
			}
			
			
		}catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
