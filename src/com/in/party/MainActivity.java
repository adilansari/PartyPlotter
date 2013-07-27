package com.in.party;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.Activity;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

public class MainActivity extends Activity {
	
	private boolean isResumed;
	private static final String TAG = "com.in.party"; 
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Reached inside oncreate");
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
	}
          
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Inside OnResume");
        isResumed = true;
        uiHelper.onResume();
        
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Inside OnPause");
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
    	if (isResumed && state.isOpened()) {
    		// If the session state is open:
            // Show the authenticated fragment
            //showFragment(SELECTION, false);
            Intent intent = new Intent(this, SelectionPreference.class);
            startActivity(intent);
        }
    }
    
    private UiLifecycleHelper uiHelper;
    private Session.StatusCallback callback = 
        new Session.StatusCallback() {
        @Override
        public void call(Session session, 
                SessionState state, Exception exception) {
        	 Log.d(TAG, "Reached inside callback");
        	 isResumed = true;
        	 onSessionStateChange(session, state, exception);
        }
    };
}