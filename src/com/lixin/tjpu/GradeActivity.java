package com.lixin.tjpu;



import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class GradeActivity extends Activity {
    /** Called when the activity is first created. */
	Button gradeButton = null;
	Button scheduleButton = null;
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade);
        gradeButton = (Button) findViewById(R.id.bxqcjcx);
        gradeButton.setOnClickListener(new cxButtonListener());
        scheduleButton = (Button) findViewById(R.id.bxqkb);
        scheduleButton.setOnClickListener(new kbButtonListener());
        
    }
    class cxButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String baseUrl = "http://jwpt.tjpu.edu.cn:8081/bxqcjcxAction.do";  
	        
	        
	        HttpClient httpClient = new DefaultHttpClient(); 
	        CookieStore cookie = ((AbstractHttpClient)httpClient).getCookieStore();
			MyApp myCookie = (MyApp) getApplication();
			cookie = myCookie.getCookies();
			System.out.println("------"+cookie.getCookies());
			List<Cookie> cookies = cookie.getCookies();
			if (cookies.isEmpty()) {     
				System.out.println("cookie为空啊");
			}else{
				System.out.println(cookies.get(0).getValue());
			}
			
			HttpPost httpPost = new HttpPost(baseUrl);  
			httpPost.setHeader("Cookie", "JSESSIONID=" + cookies.get(0).getValue()); 
			   
			 
			try{  
		 
			    HttpResponse response = httpClient.execute(httpPost); //发起POST请求  
			  
			    int resCode = response.getStatusLine().getStatusCode(); //获取响应码  
			    String result = EntityUtils.toString(response.getEntity(), "utf-8");//获取服务器响应内容 
			    System.out.println(resCode);
//			    System.out.println(result);
			    Intent intent = new Intent();
			    intent.putExtra("grades", result);
				intent.setClass(GradeActivity.this, GradeResult.class);
				GradeActivity.this.startActivity(intent);
			}catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    }
    
    
    
    
    class kbButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			showToast(GradeActivity.this, "此功能尚未完善", 2000);
			
			
			/*String baseUrl = "http://jwpt.tjpu.edu.cn:8081/xkAction.do?actionType=6";  
	        
	        
	        HttpClient httpClient = new DefaultHttpClient(); 
	        CookieStore cookie = ((AbstractHttpClient)httpClient).getCookieStore();
			MyApp myCookie = (MyApp) getApplication();
			cookie = myCookie.getCookies();
			System.out.println("------"+cookie.getCookies());
			List<Cookie> cookies = cookie.getCookies();
			if (cookies.isEmpty()) {     
				System.out.println("cookie为空啊");
			}else{
				System.out.println(cookies.get(0).getValue());
			}
			
			HttpPost httpPost = new HttpPost(baseUrl);  
			httpPost.setHeader("Cookie", "JSESSIONID=" + cookies.get(0).getValue()); 
			   
			 
			try{  
		 
			    HttpResponse response = httpClient.execute(httpPost); //发起POST请求  
			  
			    int resCode = response.getStatusLine().getStatusCode(); //获取响应码  
			    String result = EntityUtils.toString(response.getEntity(), "utf-8");//获取服务器响应内容 
			    System.out.println(resCode);
//			    System.out.println(result);
			    Intent intent = new Intent();
			    intent.putExtra("grades", result);
				intent.setClass(GradeActivity.this, Schedule.class);
				GradeActivity.this.startActivity(intent);
			}catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
    	
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
    
    
    
    
    
    
}