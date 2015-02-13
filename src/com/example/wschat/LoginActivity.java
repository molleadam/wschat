package com.example.wschat;
import java.net.URI;

import com.example.utils.ChatClient;
import com.example.wschat.ChatService.MyBinder;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.EditText;




public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		 SharedPreferences prefs = getSharedPreferences("WsChatPreferences", Context.MODE_PRIVATE);
		 String value = prefs.getString("name", null);
		 
		 if(value != null){
			 System.out.println("sono entrato nell if");
			 Intent newintent = new Intent(this, ListActivity.class);
		     startActivity(newintent);
		 }
	}
	
	
	public void enter(View v){
		EditText editText = (EditText) findViewById(R.id.editTextName);
		String name = editText.getText().toString();
		if(name.length() >5){
			Intent intent = new Intent(this, ChatService.class);
			intent.putExtra("nome", name);
	        startService(intent);
	        
	        Intent newintent = new Intent(this, ListActivity.class);
	        startActivity(newintent);
		}
		
	}
	
	@Override
	public void onBackPressed() {
		 
		Intent intent = new Intent(Intent.ACTION_MAIN);
	    intent.addCategory(Intent.CATEGORY_HOME);
	    startActivity(intent);
	}
}