package com.example.wschat;

import java.util.Collections;
import java.util.List;



import com.example.utils.AsyncTaskRunner;
import com.example.utils.ChatInterface;
import com.example.utils.Message;
import com.example.utils.MessageTable;
import com.example.utils.Protocol;
import com.example.utils.User;
import com.example.wschat.ChatService.MyBinder;

import android.support.v7.app.ActionBarActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class ChatActivity extends ActionBarActivity implements ChatInterface{
	MyBinder binder;
	User user; 
	Message message;
	ListMessagesAdapter listMessagesAdapter;
	int curretPage;
	
	
	private ServiceConnection myConnection = new ServiceConnection() {
		@Override
	    public void onServiceConnected(ComponentName className,IBinder service) {
			binder = (MyBinder) service;
			binder.getService().client.setActivityCallback(ChatActivity.this);
			binder.getService().client.setForeground(true);
			MyListView listView = (MyListView) findViewById(R.id.arrayList);
			listView.setRef(ChatActivity.this);
			
			List<Message> messages = binder.getService().client.messageTable.fetchMessages(user.id,curretPage);
			messages = MessageTable.invertList(messages);
			listMessagesAdapter = new ListMessagesAdapter(getApplicationContext(), R.layout.single_message,messages);
	        listView.setAdapter(listMessagesAdapter);
			
	    }
		@Override
	    public void onServiceDisconnected(ComponentName arg0) {}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		curretPage = 1;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		user = (User) getIntent().getSerializableExtra("user");
		getSupportActionBar().setTitle(user.name);
		Intent intent = new Intent(this, ChatService.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
        
        
		
	}


	
	public void sendMessage(View v){
		EditText editText = (EditText)findViewById(R.id.editmessage);
		String text = editText.getText().toString();
		editText.setText("");
		
		User sender = new User();
		sender.name = "Io";
		
		Message message = new Message();
		message.setMessage(text);
		message.setReceiver(user);
		message.setSender(sender);
		message.setMode("S");
		
		listMessagesAdapter.add(message);
		binder.getService().client.sendMessage(message);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		unbindService(myConnection);
		
		
	}

	@Override
	public void onList(List<User> l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceivedMessage(Message m) {
		message = m;
		
		runOnUiThread(new Runnable() { 
            public void run() 
            { 
            	listMessagesAdapter.add(message);
            }
		});
		
	}
	
	public void loadMessages(View v){
		curretPage = curretPage + 1;
		AsyncTaskRunner runner = new AsyncTaskRunner(binder.getService().client.messageTable,listMessagesAdapter);
		runner.execute(user.id,Integer.toString(curretPage));
	}
	
	
}