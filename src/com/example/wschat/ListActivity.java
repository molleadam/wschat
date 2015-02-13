package com.example.wschat;

import java.lang.reflect.Field;
import java.net.URI;


import java.util.List;


import com.example.utils.ChatClient;
import com.example.utils.ChatInterface;
import com.example.utils.Message;
import com.example.utils.User;
import com.example.wschat.ChatService.MyBinder;

import android.support.v7.app.ActionBarActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;



public class ListActivity extends ActionBarActivity implements ChatInterface {
	
	public List<User> list;
	MyBinder binder;
	
	
	
	private ServiceConnection myConnection = new ServiceConnection() {
		@Override
	    public void onServiceConnected(ComponentName className,IBinder service) {
			binder = (MyBinder) service;
			onChatServiceAvaiable();
	    }
		@Override
	    public void onServiceDisconnected(ComponentName arg0) {}
	};
	
	private void getOverflowMenu() {

	     try {
	        ViewConfiguration config = ViewConfiguration.get(this);
	        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	        if(menuKeyField != null) {
	            menuKeyField.setAccessible(true);
	            menuKeyField.setBoolean(config, false);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getOverflowMenu();
		setContentView(R.layout.activity_main);
		Intent intent = new Intent(this, ChatService.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
		System.out.println("menu infalting");
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.mainmenu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	public void onChatServiceAvaiable(){
		binder.getService().client.setForeground(true);
		binder.getService().client.setActivityCallback(this);
		binder.getService().client.getList();
	}
	
	@Override
	public void onList(List<User> l){
		
		list = l;
		
		runOnUiThread(new Runnable() { 
            public void run() 
            { 
            	ListView listView = (ListView) findViewById(R.id.arrayList); 
        		ListUsersAdapter adapter = new ListUsersAdapter(getApplicationContext(), R.layout.item_list, getList());
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new OnItemClickListener(){
                    @Override 
                    public void onItemClick(AdapterView<?> adapterView,    View row,    int position,    long index){
                    	 String reference = ((TextView) row.findViewById(R.id.reference)).getText().toString();
                    	 String name = ((TextView) row.findViewById(R.id.rowText)).getText().toString();
                    	 
                    	 User user = new User();
                    	 user.id = reference;
                    	 user.name = name;
                    	 Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                    	 intent.putExtra("user", user);
                    	 startActivity(intent);
                    }
                  });
            } 
        }); 
		
	}
	
	@Override
	public void onReceivedMessage(Message message){
		
	}
	public List<User> getList(){
		return list;
	}
	
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		unbindService(myConnection);
	}
	
	@Override
	public void onBackPressed() {
		binder.getService().client.setForeground(false);
		Intent intent = new Intent(Intent.ACTION_MAIN);
	    intent.addCategory(Intent.CATEGORY_HOME);
	    startActivity(intent);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_logout:
	        	unbindService(myConnection);
	    		stopService(new Intent(this, ChatService.class));
	    		SharedPreferences prefs = getSharedPreferences("WsChatPreferences", Context.MODE_PRIVATE);
	    		prefs.edit().remove("name").commit();
	    		startActivity(new Intent(this,LoginActivity.class));
	            return true;
	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}