package com.in.party;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Request;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.facebook.HttpMethod;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.android.Facebook;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
public class SelectionPreference extends PreferenceActivity {
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
        CharSequence msg = getText(R.string.welcome_message);
        showBreadCrumbs(msg, msg);
    }

    public static class SelectionPreferenceFragment extends PreferenceFragment {	
	    private TextView mUserName;
	    private final ArrayList<Events> mEvents = new ArrayList<Events>();
    	
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        super.onCreateView(inflater, container, savedInstanceState);
	        View view = inflater.inflate(R.layout.selection, container, false);
	        mUserName = (TextView) view.findViewById(R.id.username);
	        return view;
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
	    
//	    private void updateEventList() {
//	        // Make an API call to get user data and define a 
//	        // new callback to handle the response.
//	        Request request = Request.newMeRequest(Session.getActiveSession(), 
//	                new Request.GraphUserCallback() {
//	            @Override
//	            public void onCompleted(GraphUser user, Response response) {
//	                // If the response is successful
//	                if (user != null) {
//	                        // Set the id for the ProfilePictureView
//	                        // view that in turn displays the profile picture.
//	                	mUserName.setText(user.getName());
//	                	
//	                }
//	                if (response.getError() != null) {
//	                    // Handle errors, will do so later.
//	                }
//	            }
//	        });
//	        request.executeAsync();
//	    } 
	    public void updateEventList() {
	    	//String fqlQuery = "SELECT eid, name, pic, creator, start_time FROM event WHERE eid IN (SELECT eid FROM event_member WHERE uid='XX' and rsvp_status='attending')";       
	        //Bundle params = new Bundle();
	        //params.putString("q", fqlQuery);
	    	Request request = Request.newMeRequest(Session.getActiveSession(), 
	                new Request.GraphUserCallback() {

	            @Override
	            public void onCompleted(GraphUser user, Response response) {
	                if (user != null) {
	                	Log.d("Himank", "Inside On Completed");
	                	String userId = user.getId();
	                	EventList e = new EventList(userId);
	                	Thread t = new Thread(e);
	                	t.start();
	                }
	            }});
	    	request.executeAsync();
	   }
	    
	    public static class EventList implements Runnable {
	        private String mUserId;
	    	public EventList(String userId) {
	        	mUserId = userId;
	        }
	    	public void sendEventRequest() throws ClientProtocolException, IOException, URISyntaxException {
	    	   HttpClient client = new DefaultHttpClient();
	           HttpGet eventRequest = new HttpGet();
	           Log.d("Himank", GraphURI());
	           eventRequest.setURI(new URI(GraphURI()));
	           HttpResponse response = client.execute(eventRequest);
	           BufferedReader rd = new BufferedReader
	        		  (new InputStreamReader(response.getEntity().getContent()));
	         	           
	        		String line = "";
	        		while ((line = rd.readLine()) != null) {
	        		  Log.d("Himank", line);
	        		}
	        		
	        		
	           
//	           InputStream is = response.getEntity().getContent();
//	           BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
//	           StringBuilder sb = new StringBuilder();
//	           String line = null;
//	           while((line = reader.readLine())!= null) {
//	        	   sb.append(line + "\n");
//	        	   Log.d("Himank", line);
//	           }
	          }
	    
	          public String GraphURI() {
	        	  
	    	     String GraphRequestURI = "https://graph.facebook.com/";
	    	     return GraphRequestURI = GraphRequestURI + mUserId + "/events?access_token=" + Session.getActiveSession().getAccessToken();
	          }

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					sendEventRequest();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
	    }
        public static class Events {
            String mName;
            String mHost;
            String mLocation;
            public Events(String name, String host, String location) {
                mName = name;
                mHost = host;
                mLocation = location;
            }
        }
    }
	    
}
