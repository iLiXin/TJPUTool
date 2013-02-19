package com.lixin.tjpu;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BookDetail extends Activity{
	
	
	TextView titleV;
	TextView authorV;
	TextView statusV;
	TextView numberV;
	TextView typeV;
	TextView locV;
	TextView isbnV;
	TextView chubansheV;
	TextView yemaV;
	TextView jianjieV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_detail);
		titleV = (TextView) findViewById(R.id.bookTitle);
		authorV = (TextView) findViewById(R.id.bookAuthor);
		statusV= (TextView) findViewById(R.id.bookStatus);
		numberV= (TextView) findViewById(R.id.bookNumber);
		typeV = (TextView) findViewById(R.id.bookType);
		locV = (TextView) findViewById(R.id.bookLoc);
		isbnV = (TextView) findViewById(R.id.bookIsbn);
		chubansheV = (TextView) findViewById(R.id.bookChubanshe);
		yemaV = (TextView) findViewById(R.id.bookYema);
		jianjieV = (TextView) findViewById(R.id.bookJianjie);
		
		Intent intent = getIntent();
		String detail = intent.getStringExtra("detail");
		String[] cut = detail.split(" <!-- Print the title, if one exists -->");
		String test[] = cut[1].split("<([^>]*)>");
		System.out.println("``````````````"+test.length);
		
		String title = test[1];
		String author = test[4];
		String status = test[22];
		String number = test[57];
		String type = test[61];
		String loc = test[63];
		String isbn = test[104];
		String chubanshe = test[112];
		String yema = test[115];
		String jianjie = test[182];
		
		titleV.setText(title);
		authorV.setText(author.substring(28, author.length()));
		statusV.setText(status.substring(0, status.length()));
		numberV.setText(number.substring(0, number.length()));
		typeV.setText(type.substring(0, type.length()));
		locV.setText(loc.substring(0, loc.length()));
		isbnV.setText(isbn.substring(0, isbn.length()));
		chubansheV.setText(chubanshe.substring(0, chubanshe.length()));
		yemaV.setText(yema.substring(0, yema.length()));
		jianjieV.setText(jianjie.substring(0, jianjie.length()));
	}
	
	
	
}
