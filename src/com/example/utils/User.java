package com.example.utils;

import java.io.Serializable;

public class User implements Serializable{
	public String id;
	public String name;
	
	@Override
    public String toString() {
        return "{\"name\":\""+name+"\",\"id\":\""+id+"\"}";
    }   
}
