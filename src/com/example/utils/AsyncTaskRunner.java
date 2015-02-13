package com.example.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.example.wschat.ListMessagesAdapter;

import android.os.AsyncTask;

public class AsyncTaskRunner extends AsyncTask<String, String,List<Message>> {

	 MessageTable messagetable;
	 ListMessagesAdapter listMessagesAdapter;
	 
	 public AsyncTaskRunner(MessageTable mt, ListMessagesAdapter l){
		 messagetable = mt;
		 listMessagesAdapter = l;
	 }
	
	 @Override
	  protected void onPostExecute(List<Message> results) {
		 
		 for (Message message : results) {
			 listMessagesAdapter.insert(message, 0);
		}
	   
	  }
	 
	 @Override
	  protected void onPreExecute() {
	   // Things to be done before execution of long running operation. For
	   // example showing ProgessDialog
	  }
	 
	 @Override
	 protected void onProgressUpdate(String... text) {
	   
		 
	  }

	@Override
	protected List<Message> doInBackground(String...params){
		
		// TODO Auto-generated method stub
		String user_id = params[0];
		String page = params[1];
		return messagetable.fetchMessages(user_id, Integer.parseInt(page));
		
		
	}
}
