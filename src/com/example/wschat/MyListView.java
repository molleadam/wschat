package com.example.wschat;

import com.example.utils.AsyncTaskRunner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MyListView extends ListView{

	ChatActivity ref;
	
	public MyListView(Context context, AttributeSet attrs) {
		super(context,attrs);
		Button button = new Button(context);
		button.setText("Carica Altri");
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ref.loadMessages(v);
				
			}
		});
		
		this.addHeaderView(button);
		
	}

	public ChatActivity getRef() {
		return ref;
	}

	public void setRef(ChatActivity ref) {
		this.ref = ref;
	}
	
}
