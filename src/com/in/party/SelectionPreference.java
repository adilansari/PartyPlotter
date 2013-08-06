package com.in.party;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Request;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.facebook.HttpMethod;
import com.facebook.Response;
import com.facebook.Session;
import android.util.Log;

public class SelectionPreference extends PreferenceActivity {
    public static String mUserId;
    public static String mUserName;
	@Override
    public Intent getIntent() {
        Intent modIntent = new Intent(super.getIntent());
        modIntent.putExtra(EXTRA_SHOW_FRAGMENT,
        		SelectionPreferenceFragment.class.getName());
        modIntent.putExtra(EXTRA_NO_HEADERS, true);
        return modIntent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Himank", "Reached inside oncreate Selction");
        CharSequence msg = getText(R.string.welcome_message) + mUserName;
        showBreadCrumbs(msg, msg);
   }

    public static class SelectionPreferenceFragment extends PreferenceFragment {
    	@Override
	    public void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	    	addPreferencesFromResource(R.xml.preference_event_list);
	    }
    	
    		    
	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	        updateEventList();
	    }
	    
	    @Override
	    public void onResume() {
	        super.onResume();
	    }

	    @Override
	    public void onPause() {
	        super.onPause();
	    }
	    

	    private void updateEventList() {
	    	Session session = Session.getActiveSession();
	    	Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(getActivity(), Arrays.asList("user_events"));
            session.requestNewReadPermissions(newPermissionsRequest);
	    	String fqlQuery = "SELECT eid, name, all_members_count, creator, host FROM event WHERE eid IN " + " (SELECT eid FROM event_member WHERE uid = me() LIMIT 25)";
	    	//String fqlQuery = "SELECT uid, name, pic_square FROM user WHERE uid IN " +
	          //      "(SELECT uid2 FROM friend WHERE uid1 = me() LIMIT 25)"; 
	    	  Bundle params = new Bundle();
	          params.putString("q", fqlQuery);
	          Request request = new Request(session,
	              "/fql",                         
	              params,                         
	              HttpMethod.GET,                 
	              new Request.Callback(){         
	                  public void onCompleted(Response response) {
	                	  Log.i("Himank", "Result: " + response.toString());
	                      GraphObject graphObject = response.getGraphObject();
	                      JSONArray jsonarray = (JSONArray)graphObject.getProperty("data");
	                      for (int i = 0; i < jsonarray.length(); i++) {
	                            try {
	                                JSONObject row = jsonarray.getJSONObject(i);
	                                Preference event = new EventPreference(getActivity(), row.getLong("creator"), row.getString("host"), 
	                                		row.getLong("eid"), row.getInt("all_members_count"), mUserId);
	                                event.setTitle(row.getString("name"));
	                                getPreferenceScreen().addPreference(event);
	                             } catch (JSONException e) {
	                                    e.printStackTrace();
	                             }
	                      }
	                  }                  
	          }); 
	          Request.executeBatchAsync(request);                 
	      }
	    @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
                Preference preference) {
            if (preference instanceof EventPreference) {
                ((EventPreference)preference).onPreferenceClickHelper((EventPreference)preference);
            }
            return true;
        }   

    }
	    
}