package com.example.utils;

import java.io.Serializable;

public class Message implements Serializable{
	public String message;
	public User receiver;
	public User sender;
	public String mode;
	public String date;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	
	
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	@Override
    public String toString() {
		String string ="";
		string = "{\"message\" :\""+message+"\",\"receiver\":"+receiver.toString()+"}";
        return string;
  
    }  
}
