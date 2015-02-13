package com.example.utils;

import java.util.List;

public interface ChatInterface {

	public void onList(List<User> l);
	
	public void onReceivedMessage(Message message);
}
