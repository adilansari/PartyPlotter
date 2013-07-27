package com.in.party;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        super.onCreateView(inflater, container, savedInstanceState);
	        View view = inflater.inflate(R.layout.selection, container, false);
	        return view;
	   }
    }
}