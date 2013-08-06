package com.in.party;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.Preference;
import android.util.Log;
import android.widget.Toast;

public class EventPreference extends Preference{
    private String mEventName;
    private Long mEventId;
    private int mTotalCount;
    private String mHost;
    private String mUserId;
    private Long mCreator;
    private Context mContext;
    public static String EVENT_ID = "event_id";
    
    public EventPreference(Context context, Long creator, String host, Long eid, int memberCount, String userId) {
    	super(context);
    	//	mEventName = row.getString("name");
			mEventId = eid;
			mTotalCount = memberCount;
			mHost = host;
			mCreator = creator;
			mUserId = userId;
			mContext = context;
    }
    
    public void onPreferenceClickHelper(EventPreference preference) {
    	if (preference.mCreator == null) {
    		Log.d("Himank", "null");
    		return;
    	}
    	if (preference.mUserId == null) {
    		Log.d("Himank", "usernull");
    		return;
    	}
    	if (preference.mCreator.toString().equals(preference.mUserId)) {
    		Intent intent = new Intent(mContext, EventPreferenceAdvancedHost.class);
    		intent.putExtra(EVENT_ID, mEventId); 
    		mContext.startActivity(intent);
    	} else {
    		if (MainActivity.mHostActivity != null) {
    			if (mEventId.toString() != null) {
    			    int a[] = MainActivity.mHostActivity.dbGet(mEventId.toString());
    			    if (a == null) {
    			    	Toast.makeText(mContext, "Host doesn't set anything yet..!", Toast.LENGTH_LONG).show();
    			    		Intent intent = new Intent(mContext, EventPreferenceAdvancedGuest.class);
                        	intent.putExtra(EVENT_ID, mEventId); 
                        	mContext.startActivity(intent);
    			    	//return;
    			    } else {
    			    	displayMessageAlert(mContext);
    			    }
    			}
    		}
    		//Intent intent = new Intent(mContext, EventPreferenceAdvancedGuest.class);
    		 //mContext.startActivity(intent);
  	}
    }
    
    public void displayMessageAlert(final Context context) {
        new AlertDialog.Builder(context)
                .setMessage("Host has set items needed for the party. Press Ok to Continue...")
                .setTitle("Event Updated by Host")
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                            	Intent intent = new Intent(mContext, EventPreferenceAdvancedGuest.class);
                            	intent.putExtra(EVENT_ID, mEventId); 
                            	mContext.startActivity(intent);
                            	//context.finish();
                            }
                        })
                  .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        })
                .show();
    }
}
