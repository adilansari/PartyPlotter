package com.in.party;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class EventPreferenceAdvancedHost extends PreferenceActivity {
	public static Long mEventId;
	@Override
    public Intent getIntent() {
        Intent modIntent = new Intent(super.getIntent());
        modIntent.putExtra(EXTRA_SHOW_FRAGMENT,
        		EventPreferenceAdvancedHostFragment.class.getName());
        modIntent.putExtra(EXTRA_NO_HEADERS, true);
        return modIntent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CharSequence msg = SelectionPreference.mUserName + getText(R.string.host_screen);
        showBreadCrumbs(msg, msg);
        mEventId = getIntent().getLongExtra(EventPreference.EVENT_ID, 0);
    }
    
public static class EventPreferenceAdvancedHostFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

    	
    	private EditTextPreference mPizzaText;
    	private EditTextPreference mBeerText;
    	private EditTextPreference mPepsiText;
    	private EditTextPreference mCokeText;
    	@Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_event_host);
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }
    	
    	@Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if (MainActivity.mHostActivity != null) {
    			if ( mEventId.toString() != null) {
    				mPizzaText = (EditTextPreference) findPreference(EventPreferenceAdvancedGuest.KEY_PIZZA);
			    	mBeerText = (EditTextPreference) findPreference(EventPreferenceAdvancedGuest.KEY_BEER);
			    	mCokeText = (EditTextPreference) findPreference(EventPreferenceAdvancedGuest.KEY_COKE);
			    	mPizzaText.setSummary(Integer.toString(0));
			    	mBeerText.setSummary(Integer.toString(0));
			    	mCokeText.setSummary(Integer.toString(0));
    			    int a[] = MainActivity.mHostActivity.dbGet(mEventId.toString());
    			    if (a != null) {
    			    	mPizzaText = (EditTextPreference) findPreference(EventPreferenceAdvancedGuest.KEY_PIZZA);
    			    	mBeerText = (EditTextPreference) findPreference(EventPreferenceAdvancedGuest.KEY_BEER);
    			    	mCokeText = (EditTextPreference) findPreference(EventPreferenceAdvancedGuest.KEY_COKE);
    			    	mPizzaText.setSummary(Integer.toString(a[0]));
    			    	mBeerText.setSummary(Integer.toString(a[1]));
    			    	mCokeText.setSummary(Integer.toString(a[2]));
    			    	
    			    }
    			 }
            }
    	}
    	
    	@Override
    	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            String value = "";
    	    if (key.equals(EventPreferenceAdvancedGuest.KEY_PIZZA)){
    	    	mPizzaText = (EditTextPreference) findPreference(key);
    	    	mPizzaText.setSummary(sharedPreferences.getString(key, ""));
    	    	value = sharedPreferences.getString(key, "");
    	    } else if (key.equals(EventPreferenceAdvancedGuest.KEY_BEER)) {
    	    	mBeerText = (EditTextPreference) findPreference(key);
    	    	mBeerText.setSummary(sharedPreferences.getString(key, ""));
    	    	value = sharedPreferences.getString(key, "");
    	    } else if (key.equals(EventPreferenceAdvancedGuest.KEY_COKE)){
    	    	mCokeText = (EditTextPreference) findPreference(key);
    	    	mCokeText.setSummary(sharedPreferences.getString(key, ""));
    	    	value = sharedPreferences.getString(key, "");
    	    }
    	    updateDBValue(value, key);

    	}
    	
    	public void updateDBValue(String value, String tag) {
    		if (value.equals("")) {
    			value = "0";
    		} else {
    			Integer.parseInt(value);
    		}
    		if (MainActivity.mHostActivity != null) {
    			if (mEventId.toString() != null) {
    			    int a[] = MainActivity.mHostActivity.dbGet(mEventId.toString());
    			    if (a == null) {
    			    	a = new int[3];
    			    }
    			    if(tag.equals(EventPreferenceAdvancedGuest.KEY_PIZZA))
    			    	MainActivity.mHostActivity.dbInsert(mEventId.toString(), Integer.parseInt(value), a[1], a[2]);
    			    else if(tag.equals(EventPreferenceAdvancedGuest.KEY_BEER))
    			    	MainActivity.mHostActivity.dbInsert(mEventId.toString(), a[0], Integer.parseInt(value), a[2]);
    			    else if(tag.equals(EventPreferenceAdvancedGuest.KEY_COKE))
    			    		MainActivity.mHostActivity.dbInsert(mEventId.toString(), a[0], a[1], Integer.parseInt(value));
    			}
    		}
    		//mPizzaText.getEditText().
    	}
    	
    	@Override
    	public void onResume() {
    	    super.onResume();
    	    getPreferenceScreen().getSharedPreferences()
    	            .registerOnSharedPreferenceChangeListener(this);
    	}

    	@Override
    	public void onPause() {
    	    super.onPause();
    	    getPreferenceScreen().getSharedPreferences()
    	            .unregisterOnSharedPreferenceChangeListener(this);
    	}
    }
}
