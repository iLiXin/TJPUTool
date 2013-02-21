package com.lixin.tjpu;

/** ͼ���ҳ�� */

import java.io.IOException;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.umeng.analytics.MobclickAgent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LibraryActivity extends Activity {
	
	TextView textView;
	EditText editText;
	Button searchButton;
	String finalUrl;
	String tempUrl;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library);
        
        //ȡ�����пؼ�
        textView = (TextView) findViewById(R.id.libra);
        editText = (EditText) findViewById(R.id.search);
        searchButton = (Button) findViewById(R.id.searchButton);

        //ΪButton���ü�����
        searchButton.setOnClickListener(new searchListener());
        
        //new session
        String url0 = "http://211.81.31.33/uhtbin/cgisirsi/N2yrsw6IqR/43220271/2/1000";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet getMethod = new HttpGet(url0);  
        try{
        	
	        HttpResponse response = httpClient.execute(getMethod); //����GET����  
		    int resCode = response.getStatusLine().getStatusCode(); //��ȡ��Ӧ��  
		    String result = EntityUtils.toString(response.getEntity(), "GBK");//��ȡ��������Ӧ���� 
		    System.out.println(resCode);
		    String session[] = result.split("HREF=");
		    String sessionUrl = "http://211.81.31.33" + session[1].substring(0, 32);
		    
		    //ͼ���Ŀ¼���ӣ�ȡ����ʵSessionֵ
		    HttpGet getMethod1 = new HttpGet(sessionUrl);
		    HttpResponse response1 = httpClient.execute(getMethod1); 
		    
		    //ͨ����ʵSessionֵ������URL
		    String result1 = EntityUtils.toString(response1.getEntity(), "utf-8");
		    String menuResult[] = result1.split("nstead -->");
		    tempUrl = "http://211.81.31.33" + menuResult[1].substring(20, 57);
		    
	    
        }
        catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    
    //��ѯ��ť����Ӧ�¼�
    class searchListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			HttpClient httpClient1 = new DefaultHttpClient();
			
			try{
				
				//ȡ��������еĹؼ���
			    String temp = editText.getText().toString();
			    
			    //�жϹؼ����Ƿ�Ϊ��
			    if(temp.equals("")){
			    	  Toast.makeText(LibraryActivity.this, "������ؼ��֣�", Toast.LENGTH_SHORT).show();
			    }
			    else{
			    	
			    //�Թؼ������±���	
			    String keyword = URLEncoder.encode(temp, "GB2312");
			    
			    //�������շ����ѯ������ַ���
				finalUrl = tempUrl + "/123?searchdata1="+keyword+"&srchfield1=GENERAL%5ESUBJECT%5E%5E%5E%B4%CA%D7%E9%B6%CC%D3%EF&library=%CC%EC%BD%F2%B9%A4%B4%F3&sort_by=TI";
				HttpPost postMethod = new HttpPost(finalUrl);
				
				//��������
			    HttpResponse response2 = httpClient1.execute(postMethod); 
			    
			    //�Է��صĽ�����±���
			    String result20 = EntityUtils.toString(response2.getEntity(), "GBK");
			    String result2 = new String(result20.getBytes("GBK"),"GBK");
			    
			    //��������͸�BookItems��������cookieֵ
			    Intent intent = new Intent();
			    intent.putExtra("result", result2 + tempUrl);
			    intent.setClass(LibraryActivity.this, BookItems.class);
			    LibraryActivity.this.startActivity(intent);
		    
		    }
		    
		    
			}catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    
		}
		
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
