package com.example.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.sax.StartElementListener;

public class MessageTable{

	DbAdapter dbadapter;
	private int currentPage = 0;
	
	public MessageTable(Context context) {
		dbadapter = new DbAdapter(context);
	}
	
	
	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public void add(Message message,String mode){
		User user = null;
		if(mode.equals("receive"))
			user = message.getSender();
		else
			user = message.getReceiver();
		
		try{
			dbadapter =  dbadapter.open();	
			dbadapter.add(user.id, message.getMode(), message.getMessage(), message.getDate());
			dbadapter.close();
		}catch(SQLException ex){
			
		}
		
	}
	
	public List<Message> fetchMessages(String id_user,int page){
		Cursor cursor;
		
		List<Message> list = new ArrayList<Message>();
		
		dbadapter =  dbadapter.open();	
		cursor = dbadapter.fetchByIdUser(id_user,page);
		while ( cursor.moveToNext() ) {
			Message message = new Message();
			message.setMessage(cursor.getString( cursor.getColumnIndex(DbAdapter.KEY_MESSAGE)));
			message.setMode(cursor.getString( cursor.getColumnIndex(DbAdapter.KEY_MODE)));
			list.add(message);
		}
		dbadapter.close();
		return list;
		
		
	}
	
	public static List<Message> invertList(List<Message> list){
		//inverto
		List<Message> revertedlist = new  ArrayList<Message>();
		ListIterator<Message> listIterator = list.listIterator(list.size());
		while(listIterator.hasPrevious()){
			revertedlist.add(listIterator.previous());
			}
		return revertedlist;
	}
}
