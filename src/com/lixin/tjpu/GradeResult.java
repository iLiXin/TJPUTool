package com.lixin.tjpu;

/** �ɼ����ҳ�� */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.umeng.analytics.MobclickAgent;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class GradeResult extends ListActivity{
	
	//ToastƵ��
	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grade_result);
		
		//��ȡ���гɼ�����
		Intent intent = getIntent();
		String grades = intent.getStringExtra("grades");
			
		String gradeArray[] = grades.split("odd");
		System.out.println("------------"+gradeArray.length);
		
		
		//��װ���ݣ�ΪAdapter�ṩ����
		
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		
		HashMap<String, String> title = new HashMap<String, String>();
		
		//����ListViewչ��ʱ����
		title.put("course", "�γ���");
		title.put("grade", "                �ɼ�");
		list.add(title);
		
		for(int i=1;i<gradeArray.length;i++){
			
			String test = filterHtml(gradeArray[i]);

			String[] result = test.split("\r\n");
			
			if(Integer.parseInt(result[21].substring(8, result[21].length()))<60){
				showToast(this, "�ҿ���", 2000);
			}else{
				showToast(this, "��ϲ,ȫ��ͨ��", 2000);
			}
		
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("course", result[8].substring(8, result[8].length()));
			map.put("grade", result[21]);
			list.add(map);
		}
		
		//ΪListView����Adapter
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,
				R.layout.grade_item, new String[] { "course", "grade" }, new int[] {R.id.resultCourse,R.id.resultGrade});
		
		setListAdapter(simpleAdapter);
	}	
	
	
	//����html��ǩ
	
	public static String filterHtml(String str) {

		Pattern pattern = Pattern.compile( "<([^>]*)>");

		Matcher matcher = pattern.matcher(str);

		StringBuffer sb = new StringBuffer();

		boolean result1 = matcher.find();

		while (result1) {

			matcher.appendReplacement(sb, "");

			result1 = matcher.find();

		}

		matcher.appendTail(sb);

		return sb.toString();

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
