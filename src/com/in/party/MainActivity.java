package com.in.party;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.app.Activity;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

public class MainActivity extends Activity {
	public static HostActivity mHostActivity;
	private boolean isResumed;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Himank", "Reached inside oncreate");
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        mHostActivity = new HostActivity(getContentResolver());
	}
	          
    @Override
    public void onResume() {
        super.onResume();
        Log.d("Himank", "Inside OnResume");
        isResumed = true;
        uiHelper.onResume();
        
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Himank", "Inside OnPause");
        isResumed = false;
        uiHelper.onPause();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
    
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        // Only make changes if the activity is visible
    	final Context context = this;
    	if (isResumed && state.isOpened()) {
    		// If the session state is open:
            // Show the authenticated fragment
            //showFragment(SELECTION, false);
    		Request request = Request.newMeRequest(Session.getActiveSession(), new Request.GraphUserCallback() {
	            String user_ID;
	    		@Override
	            public void onCompleted(GraphUser user, Response response) {
	                // If the response is successful
	                if (user != null) {
	                    user_ID = user.getId();//user
	                    Intent intent = new Intent(context, SelectionPreference.class);
	                    SelectionPreference.mUserId = user_ID;
	                    SelectionPreference.mUserName = user.getFirstName() + " " + user.getLastName();
	                    startActivity(intent);
	                }      
	            }
	        }); 
	    	Request.executeBatchAsync(request);
        }
    }
    
    private UiLifecycleHelper uiHelper;
    private Session.StatusCallback callback = 
        new Session.StatusCallback() {
        @Override
        public void call(Session session, 
                SessionState state, Exception exception) {
        	 isResumed = true;
        	 onSessionStateChange(session, state, exception);
        }
    };
}


