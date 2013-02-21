package com.lixin.tjpu;

/** 登录页面 */

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



public class LoginActivity extends Activity{
	
	private EditText username;
	private EditText password;
	private ImageButton loginButton;
	private TextView textView;

	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		textView = (TextView) findViewById(R.id.Tipstxt2);
		
		//从SharedPreferences获取数据填充用户名密码
		sp = getSharedPreferences("UserInfo", 0);
		username.setText(sp.getString("username", null));
		password.setText(sp.getString("password", null));
		
		//加入超链接，作者新浪微博
		textView.setText(Html.fromHtml("2.如有问题或建议请联系作者 "+"<a href=\"http://weibo.com/328858558\">@贝加尔湖的最深处</a>"));
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		
		//登录按钮
		loginButton = (ImageButton) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new LoginListener());
		
		//检查新版本
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
		
		
		/*loginButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			
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
	
	
	//登录按钮响应事件
	
	class LoginListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			//判断网络状态
			if(!isNetworkAvailable(LoginActivity.this)){
				showToast(LoginActivity.this, "宝贝，别闹！没网用不了", 2000);
			}
			else{
			
			String uname = username.getText().toString();
			String upwd = password.getText().toString();
			
			//构造登录URL
			String baseUrl = "http://jwpt.tjpu.edu.cn:8081/loginAction.do?zjh="+ uname +"&mm="+upwd;  
			
			
			HttpGet getMethod = new HttpGet(baseUrl);  
			              
			HttpClient httpClient = new DefaultHttpClient();  
			  
			try{
			    HttpResponse response = httpClient.execute(getMethod); //发起GET请求  
			  
			    int resCode = response.getStatusLine().getStatusCode(); //获取响应码  
			    String result = EntityUtils.toString(response.getEntity(), "utf-8");//获取服务器响应内容 
			    System.out.println(resCode);
			    
				if(result.contains("学分制综合教务")){
				
					//保存用户名密码
					sp = getSharedPreferences("UserInfo", Context.MODE_WORLD_WRITEABLE
				          | Context.MODE_WORLD_READABLE);
					sp.edit().putString("username",
					        username.getText().toString()).commit();
					sp.edit().putString("password",
					        password.getText().toString()).commit();
				
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
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	}
	
	//判断网络状态
	public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (connectivity == null) {   
            return false;   
        } else {   
            NetworkInfo[] info = connectivity.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;   
                    }   
                }   
            }   
        }   
        return false;   
    }
	
	
	//toast频率
    private static Toast mToast = null;  
    public static void showToast(Context context, String text, int duration) {  
        if (mToast == null) {  
            mToast = Toast.makeText(context, text, duration);  
        } else {  
            mToast.setText(text);  
            mToast.setDuration(duration);  
        }  
  
        mToast.show();  
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
