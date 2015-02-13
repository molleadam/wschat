package com.example.wschat;

import java.net.URI;

import com.example.utils.ChatClient;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class ChatService extends Service{	
	private IBinder myBinder;
	public ChatClient client;
	
	
	@Override
  public void onCreate() {
	 
	 
	 myBinder = new MyBinder(this);
	 client = new ChatClient(URI.create("ws://192.168.1.181:9000"),getApplicationContext());
	
  }
	
	@Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    //TODO do something useful
	if(client.isConnected == false){
		client.setName(intent.getExtras().get("nome").toString());
		try{
			client.connect();	
		}catch(IllegalStateException ex){
			System.out.println("il server non è raggiungibile");
		}
		
		
		 SharedPreferences prefs = getSharedPreferences("WsChatPreferences", Context.MODE_PRIVATE);
		 Editor editor = prefs.edit();
		 editor.putString("name", intent.getExtras().get("nome").toString()); // Storing string
		 editor.commit();
	}
    return Service.START_STICKY;
  }

	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void startForeground() {
		startForeground(17, null); // Because it can't be zero...
	}
	  
	@Override
  public IBinder onBind(Intent intent) {
  //TODO for communication return IBinder implementation
	  return myBinder;
  }
	  
	
	@Override
	public void onDestroy() {
		client.disconnect();
	}
	  
	  
  public class MyBinder extends Binder {
	  ChatService reference;
	  public MyBinder(ChatService ref){
		  reference = ref;
	  }
	  public ChatService getService() {
            return reference;
        }
    }
  
  
 
  
}
