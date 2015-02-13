package com.example.wschat;

import java.util.ArrayList;
import java.util.List;

import com.example.utils.Message;
import com.example.utils.User;

import android.R.drawable;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListMessagesAdapter extends ArrayAdapter {
	
	private LinearLayout singleMessageContainer;
	
	public ListMessagesAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
        
    }
	
	public ListMessagesAdapter(Context context, int textViewResourceId,List<Message> message) {
		super(context, textViewResourceId,message);
    }

	
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	View row = convertView;
    	Message message = (Message) getItem(position);
    	
    	LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		row = inflater.inflate(R.layout.single_message,null);
		
		int gravity = 0;
		Drawable background = null;
        
        if(message.getMode().equals("S")){
        	gravity = Gravity.RIGHT;
        	background = getContext().getResources().getDrawable(R.drawable.row_background_dx);
        }
        else{
        	gravity = Gravity.LEFT;
        	background = getContext().getResources().getDrawable(R.drawable.row_background_sx);
        }
        	
        
        singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
        TextView messageTextView = (TextView)row.findViewById(R.id.message);
        messageTextView.setBackground(background);
        singleMessageContainer.setGravity(gravity);
        messageTextView.setText(message.getMessage());
        return row;
    }

}
