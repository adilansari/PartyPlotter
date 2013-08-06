package com.in.party;

import java.util.Arrays;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Button;


//needs - Event Object itself (owner, attendees, id)
public class HostActivity extends PreferenceActivity {
	
	public static Uri mUri = myProvider.CONTENT_URI;
	private final ContentResolver mContentResolver;
	private final ContentValues[] mContentValues;
	
	
	
	public HostActivity(ContentResolver _cr) {
		mContentResolver = _cr;
		mContentValues = initTestValues();
	}
	
	private ContentValues[] initTestValues() {
        ContentValues[] cv = new ContentValues[20];
        for (int i = 0; i < 20; i++) {
            cv[i] = new ContentValues();
            cv[i].put(myHelper.KEY_FIELD, "key" + Integer.toString(i));
            cv[i].put(myHelper.PIZZA_FIELD, i*4);
            cv[i].put(myHelper.DRINK_FIELD, i*3);
            cv[i].put(myHelper.BEER_FIELD, i*2);
            Log.i("adil", "key"+ Integer.toString(i));
        }

        return cv;
    }
	
	
	private boolean testInsert() {
		Log.e("adil", "inside testInsert");
        try {
            for (int i = 0; i < 20; i++) {
            	Log.v("adil", "testInsert Success");
                mContentResolver.insert(mUri, mContentValues[i]);
            }
        } catch (Exception e) {
        	Log.e("adil", "testInsert Fail");
            return false;
        }

        return true;
    }
	
	public void dbInsert(String eventId, int pizza, int beer, int drinks) {
		ContentValues cv = new ContentValues();
		cv.put(myHelper.KEY_FIELD, eventId);
		cv.put(myHelper.PIZZA_FIELD, pizza);
		cv.put(myHelper.BEER_FIELD, beer);
		cv.put(myHelper.DRINK_FIELD, drinks);
		mContentResolver.insert(mUri, cv);
		
	}
	
	int[] dbGet(String eventId) {
		Cursor resultCursor = mContentResolver.query(mUri, null, eventId, null, null);
		if (resultCursor.getCount() != 0) {
		resultCursor.moveToFirst();
		int[] values = new int[3];
		values[0] = resultCursor.getInt(resultCursor.getColumnIndex(myHelper.PIZZA_FIELD));
		values[1] = resultCursor.getInt(resultCursor.getColumnIndex(myHelper.BEER_FIELD));
		values[2] = resultCursor.getInt(resultCursor.getColumnIndex(myHelper.DRINK_FIELD));
		return values;
		}
		return null;
	}
}
