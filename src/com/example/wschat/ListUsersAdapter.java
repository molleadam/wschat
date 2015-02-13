package com.example.wschat;

import java.util.List;

import com.example.utils.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListUsersAdapter extends ArrayAdapter<User> {
	
	public ListUsersAdapter(Context context, int textViewResourceId,List<User> user) {
        super(context, textViewResourceId, user);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_list, null);
        TextView nome = (TextView)convertView.findViewById(R.id.rowText);
        TextView reference = (TextView)convertView.findViewById(R.id.reference);
        User user = getItem(position); 
        nome.setText(user.name);
        reference.setText(user.id);
        return convertView;
    }

}
