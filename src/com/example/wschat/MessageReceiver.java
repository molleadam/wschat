package com.example.wschat;


import com.example.utils.Message;
import com.example.utils.User;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver{
	 
	 
	@Override
	  public void onReceive(Context context, Intent intent) {
		 // Prepare intent which is triggered if the
	    // notification is selected
		Message message = (Message) intent.getSerializableExtra("message");
		
	    Intent intentTrig = new Intent(context, ChatActivity.class);
	    intentTrig.putExtra("user", message.getSender());
	    PendingIntent pIntent = PendingIntent.getActivity(context, 0, intentTrig, 0);

	    // Build notification
	    // Actions are just fake
	    Notification noti = new Notification.Builder(context)
	        .setContentTitle("Message from " + message.getSender().name)
	        .setContentText(message.message)
	        .setSmallIcon(R.drawable.ic_launcher)
	        .setContentIntent(pIntent)
	        .addAction(R.drawable.ic_launcher, "Call", pIntent)
	        .addAction(R.drawable.ic_launcher, "More", pIntent)
	        .addAction(R.drawable.ic_launcher, "And more", pIntent).build();
	    NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
	    // hide the notification after its selected
	    noti.flags |= Notification.FLAG_AUTO_CANCEL;

	    notificationManager.notify(0, noti);
	 }

	
}
