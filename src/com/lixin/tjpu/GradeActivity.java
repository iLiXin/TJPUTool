package com.lixin.tjpu;

/** �ɼ���ѯҳ�� */

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
import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class GradeActivity extends Activity {
	
	//�������пؼ�
	Button gradeButton = null;
	Button scheduleButton = null;
	Button xiaoliButton = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade);
        
        //��ȡ���а�ť�ؼ��������Ӧ�¼�
        gradeButton = (Button) findViewById(R.id.bxqcjcx);
        gradeButton.setOnClickListener(new cxButtonListener());
        scheduleButton = (Button) findViewById(R.id.bxqkb);
        scheduleButton.setOnClickListener(new kbButtonListener());
        xiaoliButton = (Button) findViewById(R.id.xlcx);
        xiaoliButton.setOnClickListener(new xlButtonListener());
        
    }
    
    //�ɼ���ѯListener
    
    class cxButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			String baseUrl = "http://jwpt.tjpu.edu.cn:8081/bxqcjcxAction.do";  
	        
	        HttpClient httpClient = new DefaultHttpClient(); 
	        
	        //ȡ��Cookie����������Application��
	        CookieStore cookie = ((AbstractHttpClient)httpClient).getCookieStore();
			MyApp myCookie = (MyApp) getApplication();
			cookie = myCookie.getCookies();
			List<Cookie> cookies = cookie.getCookies();
			
			//�ж�cookies�����Ƿ�Ϊ��
			if (cookies.isEmpty()) {     
				System.out.println("cookieΪ�հ�");
			}else{
				System.out.println(cookies.get(0).getValue());
			}
			
			//��cookie���뵽�µ�����ͷ��
			HttpPost httpPost = new HttpPost(baseUrl);  
			httpPost.setHeader("Cookie", "JSESSIONID=" + cookies.get(0).getValue()); 
			   
			 
			try{  
		 
			    HttpResponse response = httpClient.execute(httpPost); //����POST����  
			  
			    int resCode = response.getStatusLine().getStatusCode(); //��ȡ��Ӧ��  
			    String result = EntityUtils.toString(response.getEntity(), "utf-8");//��ȡ��������Ӧ���� 
			    System.out.println(resCode);

			    //�����ݽ�����͸�GradeResultҳ����д���
			    Intent intent = new Intent();
			    intent.putExtra("grades", result);
				intent.setClass(GradeActivity.this, GradeResult.class);
				GradeActivity.this.startActivity(intent);
				
			}catch (ClientProtocolException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
    	
    }
    
    
    //�α��ѯListener
    
    class kbButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			//ֱ����ת��Scheduleҳ��
			Intent intent = new Intent();
			intent.setClass(GradeActivity.this, Schedule.class);
			GradeActivity.this.startActivity(intent);
		
		}
    	
    }
    
    //У����ѯListener
    
    class xlButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			showToast(GradeActivity.this, "�˹�����δ����", 2000);
		}
    	
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
    
    //��̨ͳ��
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    
    
    
}