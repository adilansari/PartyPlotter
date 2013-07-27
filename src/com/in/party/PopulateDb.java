package com.in.party;

import android.net.Uri;

public class PopulateDb implements Runnable {

	private static final Uri mUri = buildUri("content", "party.events.provider");
	
	private static Uri buildUri(String scheme, String authority) {
		Uri.Builder uriBuilder = new Uri.Builder();
		uriBuilder.authority(authority);
		uriBuilder.scheme(scheme);
		return uriBuilder.build();
	}
	
	public void run() {
	}
	
}
