package com.example.utils;

import java.net.URI;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.utils.User;
import com.example.wschat.ListActivity;
import com.example.wschat.MessageReceiver;



public class ChatClient extends WebSocketClient{
    
	public String name;
	public Boolean isConnected = false;
	public Activity activityCallback;
	public Context context;
	public MessageTable messageTable;
	
	private Boolean foreground = false;
	
	public  ChatClient(URI uri,Context c){
		super(uri);
		Listener listener = new WebSocketListener(this);
		setListener(listener);
		context = c;
		messageTable = new MessageTable(context);
		
	}
	
	
	public Boolean getForeground() {
		return foreground;
	}


	public void setForeground(Boolean foreground) {
		this.foreground = foreground;
	}


	public void setActivityCallback(Activity act){
		activityCallback = act;
	}
	
	public Activity getActivityCallback(Activity act){
		return activityCallback;
	}
	
	public void setName(String n){
		name = n;
	}	
	
	public void getList(){
		 String jsonstring = "{'command': 'getList'}";
		 this.send(jsonstring);
	}
	
	public void onConnect(){
		// I'm going to send my name to Server 
		 String jsonstring = "{'command': 'addUser','name': '"+name+"'}";
		 this.send(jsonstring);
	}
	
	public void onList(List<User> list){
		((ChatInterface) activityCallback).onList(list);
	}
	
	public void onReceivedMessage(Message message){
		message.setMode("R");
		messageTable.add(message,"receive");
		if(!getForeground()){
			Intent intent = new Intent();
			intent.setAction("com.example.wschat.action.MESSAGE_RECEIVED");
			intent.putExtra("message", message);
			context.sendBroadcast(intent);
		}
		
		((ChatInterface) activityCallback).onReceivedMessage(message);
	}
	
	public void sendMessage(Message message){
		Protocol protocol = new Protocol();
		protocol.setCommand("sendMessage");
		protocol.setData(message);
		super.send(protocol.toString());
		messageTable.add(message,"send");
	}

	public void connect() throws IllegalStateException{
		super.connect();
		isConnected = true;
	}
}