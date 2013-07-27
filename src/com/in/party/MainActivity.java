package com.in.party;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.app.Activity;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

public class MainActivity extends Activity {
	
	private boolean isResumed = false;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Himank", "Reached inside oncreate");
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
	}
          
    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
        isResumed = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
        isResumed = false;
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
        if (isResumed) {
            if (state.isOpened()) {
                // If the session state is open:
                // Show the authenticated fragment
                //showFragment(SELECTION, false);
            	Log.d("Himank", "Reached inside onsession state change");
            	Intent intent = new Intent(this, SelectionPreference.class);
                startActivity(intent);
            } else if (state.isClosed()) {
                // If the session state is closed:
                // Show the login fragment
                //showFragment(SPLASH, false);
            }
        }
    }
    
    private UiLifecycleHelper uiHelper;
    private Session.StatusCallback callback = 
        new Session.StatusCallback() {
        @Override
        public void call(Session session, 
                SessionState state, Exception exception) {
        	 Log.d("Himank", "Reached inside callback");
        	onSessionStateChange(session, state, exception);
        }
    };
}


