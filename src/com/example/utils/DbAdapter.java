package com.example.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

 
public class DbAdapter {
  @SuppressWarnings("unused")
  private static final String LOG_TAG = DbAdapter.class.getSimpleName();
         
  private Context context;
  private SQLiteDatabase database;
  private DatabaseHelper dbHelper;
 
  // Database fields
  private static final String DATABASE_TABLE      = "messages";
 
  public static final String KEY_ID = "_id";
  public static final String KEY_ID_USER = "id_user";
  public static final String KEY_MODE = "mode";
  public static final String KEY_MESSAGE = "message";
  public static final String KEY_DATE = "date";
 
  public DbAdapter(Context context) {
    this.context = context;
  }
 
  public DbAdapter open() throws SQLException {
    dbHelper = new DatabaseHelper(context);
    database = dbHelper.getWritableDatabase();
    return this;
  }
 
  public void close() {
    dbHelper.close();
  }
 
  private ContentValues createContentValues(String id_user, String mode, String message, String date ) {
    ContentValues values = new ContentValues();
    values.put( KEY_ID_USER, id_user );
    values.put( KEY_MODE, mode );
    values.put( KEY_MESSAGE, message );
    values.put( KEY_DATE, date );
     
   return values;
  }
         
  //create 
  public long add(String id_user, String mode, String message, String date ) {
    ContentValues initialValues = createContentValues(id_user, mode, message, date);
    return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
  }
 
  
                 
  //delete       
  public boolean delete(long ID) {
    return database.delete(DATABASE_TABLE, KEY_ID + "=" + ID, null) > 0;
  }
 
  //fetch all
  public Cursor fetchAll() {
    return database.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_ID_USER, KEY_MESSAGE, KEY_MODE, KEY_DATE}, null, null, null, null, null);
  }
   
  //fetch filter by a string
  public Cursor fetchByIdUser(String idUser,int page) {
	  	System.out.println(page);
	  	int limit = 10;
	  	int offset = limit * (page-1);
		String whereClause = KEY_ID_USER+"=?";
		String[] whereArgs = new String[]{idUser	};
	    Cursor mCursor = database.query(
	    		true, 
	    		DATABASE_TABLE, 
	    		new String[] {KEY_ID, KEY_ID_USER, KEY_MESSAGE, KEY_MODE, KEY_DATE },
	    		whereClause, 
	    		whereArgs, 
	    		null, 
	    		null, 
	    		KEY_ID+" DESC", 
	    		Integer.toString(offset)+","+Integer.toString(limit)
	    		);
	         
	    return mCursor;
  }
}