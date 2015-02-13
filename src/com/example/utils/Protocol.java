package com.example.utils;

import java.io.Serializable;

public class Protocol implements Serializable{
	public String command;
	public Object data;
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	@Override
    public String toString() {
        return "{\"command\":\""+command+"\",\""+command+"\":"+data.toString()+"}";
    }    
}
