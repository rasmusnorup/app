package au.grp3.dat4.daphnidating.state;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import au.grp3.dat4.daphnidating.MyApp;

public class StateManager
{
	/**
	 * For when the user isn't doing anything with the app
	 */
	public static final String IDLE = "idle";
	
	/**
	 * When the user is requesting a date
	 */
	public static final String REQUEST = "request";
	
	/**
	 * When a match has been proposed, and the user needs to accept/decline
	 */
	public static final String MATCH = "match";
	
	/**
	 * When the user have pressed "Accept" and is waiting for a reply
	 */
	public static final String WAIT_FOR_RESPONSE = "waitForReply";
	
	/**
	 * When a date is planned
	 */
	public static final String DATE = "date";
	
	/**
	 * When the user have arrived, and is waiting for their date
	 */
	public static final String ARRIVED = "arrived";
	
	/**
	 * When a date is ongoing
	 */
	public static final String DATE_STARTED = "dateStarted";
	
	private static final String PREFS_NAME = "statePref";
	private static final String STATE_NAME = "stateName";
	
	@SuppressLint("ApplySharedPref")
	public static void setState(String state)
	{
		SharedPreferences.Editor editor = MyApp.getContext().getSharedPreferences(PREFS_NAME, 0).edit();
		editor.clear();
		editor.putString(STATE_NAME, state);
		editor.commit();
	}
	
	public static String getState()
	{
		SharedPreferences preferences = MyApp.getContext().getSharedPreferences(PREFS_NAME, 0);
		return preferences.getString(STATE_NAME, IDLE);
	}
}
