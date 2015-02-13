package com.example.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;



import com.example.utils.WebSocketClient.Listener;


public class WebSocketListener implements Listener {
	public ChatClient classReference = null;
	
	public  WebSocketListener(ChatClient reference){
		classReference = reference;
	}
	
	 	@Override
	    public void onConnect() {
	 		classReference.onConnect();
		 	
	    }

	    @Override
	    public void onMessage(String message) {
	    	
	    	try{
	    		JSONObject obj = new JSONObject(message);
	    		String command = obj.getString("command").toString();
	    		
	    		if(command.equals("list")){
	    			List<User> list = new ArrayList<User>();
		    		JSONObject listObj = obj.getJSONObject("list");
		    		for(Iterator<?> iterator = listObj.keys(); iterator.hasNext();) {
		    			String key = (String) iterator.next();
		    			JSONObject userObj = new JSONObject(listObj.get(key).toString());
		    		    User user = new User();
		    		    user.name = userObj.get("name").toString();
		    		    user.id = userObj.get("id").toString();
		    		    list.add(user);
		    		}
		    		classReference.onList(list);
	    		}
	    		
	    		if(command.equals("receiveMessage")){
	    			JSONObject messageObj = obj.getJSONObject("receiveMessage");
	    			JSONObject receiverObj = messageObj.getJSONObject("receiver");
	    			JSONObject senderObj = messageObj.getJSONObject("sender");
	    			
	    			User receiver = new User();
	    			receiver.name = receiverObj.get("name").toString();
	    			receiver.id = receiverObj.get("id").toString();
	    			
	    			User sender = new User();
	    			sender.name = senderObj.get("name").toString();
	    			sender.id = senderObj.get("id").toString();
	    			
	    			
	    			Message receivedMessage = new Message();
	    			receivedMessage.setReceiver(receiver);
	    			receivedMessage.setSender(sender);
	    			receivedMessage.setMessage(messageObj.get("message").toString());
		    		
	    			classReference.onReceivedMessage(receivedMessage);
	    		}
	    		
	    		
	    	}catch(JSONException je){
	    		System.out.println(je.getMessage());
	    	}
	    				    	
	    }

	    @Override
	    public void onMessage(byte[] data) {
	    	System.out.println("onMessage");
	    }

	    @Override
	    public void onDisconnect(int code, String reason) {
	    	System.out.println("disconnect");
	    }

	    @Override
	    public void onError(Exception error) {
	    	System.out.println(error);
	    }
}