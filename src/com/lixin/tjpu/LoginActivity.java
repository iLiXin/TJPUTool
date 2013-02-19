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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
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
		
		sp = getSharedPreferences("UserInfo", 0);
		username.setText(sp.getString("username", null));
		password.setText(sp.getString("password", null));
		
		
		textView.setText(Html.fromHtml("2.���������������ϵ���� "+"<a href=\"http://weibo.com/328858558\">@���Ӷ��������</a>"));
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		

		
		
		loginButton = (ImageButton) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new LoginListener());
		
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
		
		
		/*loginButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				 if(event.getAction() == MotionEvent.ACTION_DOWN){    
                     //����Ϊ����ʱ�ı���ͼƬ    
                     v.setBackgroundResource(R.drawable.login1);    
				 }else if(event.getAction() == MotionEvent.ACTION_UP){    
                     //��Ϊ̧��ʱ��ͼƬ    
                     v.setBackgroundResource(R.drawable.login);    
             }     
				return false;
			}
		});*/

		
	}
	
	
	class LoginListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			if(!isNetworkAvailable(LoginActivity.this)){
				showToast(LoginActivity.this, "���������֣�û���ò���", 2000);
			}
			else{
			
			String uname = username.getText().toString();
			String upwd = password.getText().toString();
			             
			String baseUrl = "http://jwpt.tjpu.edu.cn:8081/loginAction.do?zjh="+ uname +"&mm="+upwd;  
			
			
			
			
			HttpGet getMethod = new HttpGet(baseUrl);  
			              
			HttpClient httpClient = new DefaultHttpClient();  
			  
		 try{
			    HttpResponse response = httpClient.execute(getMethod); //����GET����  
			  
			    int resCode = response.getStatusLine().getStatusCode(); //��ȡ��Ӧ��  
			    String result = EntityUtils.toString(response.getEntity(), "utf-8");//��ȡ��������Ӧ���� 
			    System.out.println(resCode);
//			    System.out.println(result);
			    
			if(result.contains("ѧ�����ۺϽ���")){
			
				//�����û�������
				sp = getSharedPreferences("UserInfo", Context.MODE_WORLD_WRITEABLE
			          | Context.MODE_WORLD_READABLE);
				sp.edit().putString("username",
				        username.getText().toString()).commit();
				sp.edit().putString("password",
				        password.getText().toString()).commit();
			
			//����cookie
			CookieStore cookies = ((AbstractHttpClient)httpClient).getCookieStore();
			MyApp myCookie = (MyApp) getApplication();
			myCookie.setCookies(cookies);
			
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, TjpuActivity.class);
			LoginActivity.this.startActivity(intent);
			finish();
			}
			
			else{
				Toast.makeText(getApplicationContext(), "��¼ʧ��",
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
	
	
	//toastƵ��
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
	
    
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
	
}
