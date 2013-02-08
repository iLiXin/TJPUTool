package com.lixin.tjpu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class GradeResult extends ListActivity{
	
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grade_result);
		Intent intent = getIntent();
		String grades = intent.getStringExtra("grades");
			
		String gradeArray[] = grades.split("odd");
		System.out.println("------------"+gradeArray.length);
		
		
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		
		for(int i=1;i<gradeArray.length;i++){
			
			String test = filterHtml(gradeArray[i]);

			String[] result = test.split("\r\n");
//			System.out.println(result[8]);
//			System.out.println(result[21]);
			
			if(Integer.parseInt(result[21].substring(8, result[21].length()))<60){
				showToast(this, "挂科啦", 2000);
			}else{
				showToast(this, "恭喜,全部通过", 2000);
				
			}
		
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("course", result[8].substring(8, result[8].length()));
			map.put("grade", result[21]);
			list.add(map);
		}
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,
				R.layout.grade_item, new String[] { "course", "grade" }, new int[] {R.id.resultCourse,R.id.resultGrade});
		
		setListAdapter(simpleAdapter);
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//过滤html标签
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
	
	
	
	
	
	

}
