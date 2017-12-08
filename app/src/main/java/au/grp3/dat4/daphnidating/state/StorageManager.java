package au.grp3.dat4.daphnidating.state;

import android.content.SharedPreferences;

import au.grp3.dat4.daphnidating.MyApp;

public class StorageManager
{
	private static final String PREF_NAME = "storage";
	
	private static final String MATCH_UID = "matchUID";
	private static final String MATCH_ID = "matchID";
	private static final String OWN_PLACEMENT = "ownPlacement"; //in the 'participants' array
	private static final String LONGITUDE = "longitude";
	private static final String LATITUDE = "latitude";
	private static final String PARTNER_ARRIVED = "partnerArrived"; //is your date already there
	
	public static String getPartnerUID()
	{
		return MyApp.getContext().getSharedPreferences(PREF_NAME, 0).getString(MATCH_UID, "error");
	}
	
	public static void setPartnerUID(String uid)
	{
		SharedPreferences.Editor editor = MyApp.getContext().getSharedPreferences(PREF_NAME, 0).edit();
		editor.putString(MATCH_UID, uid);
		editor.commit();
	}
	
	public static String getMatchID()
	{
		return MyApp.getContext().getSharedPreferences(PREF_NAME, 0).getString(MATCH_ID, "error");
	}
	
	public static void setMatchID(String id)
	{
		SharedPreferences.Editor editor = MyApp.getContext().getSharedPreferences(PREF_NAME, 0).edit();
		editor.putString(MATCH_ID, id);
		editor.commit();
	}
	
	public static int getOwnPlacement()
	{
		return MyApp.getContext().getSharedPreferences(PREF_NAME, 0).getInt(OWN_PLACEMENT, -1);
	}
	
	public static void setOwnPlacement(int placement)
	{
		SharedPreferences.Editor editor = MyApp.getContext().getSharedPreferences(PREF_NAME, 0).edit();
		editor.putInt(OWN_PLACEMENT, placement);
		editor.commit();
	}
	
	public static boolean getPartnerArrived()
	{
		return MyApp.getContext().getSharedPreferences(PREF_NAME, 0).getBoolean(PARTNER_ARRIVED, false);
	}
	
	public static void setPartnerArrived(boolean isArrived)
	{
		SharedPreferences.Editor editor = MyApp.getContext().getSharedPreferences(PREF_NAME, 0).edit();
		editor.putBoolean(PARTNER_ARRIVED, isArrived);
		editor.commit();
	}
	
	public static String getLatitude()
	{
		return MyApp.getContext().getSharedPreferences(PREF_NAME, 0).getString(LATITUDE, "0");
	}
	
	public static void setLatitude(String latitude)
	{
		SharedPreferences.Editor editor = MyApp.getContext().getSharedPreferences(PREF_NAME, 0).edit();
		editor.putString(LATITUDE, latitude);
		editor.commit();
	}
	
	public static String getLongitude()
	{
		return MyApp.getContext().getSharedPreferences(PREF_NAME, 0).getString(LONGITUDE, "0");
	}
	
	public static void setLongitude(String latitude)
	{
		SharedPreferences.Editor editor = MyApp.getContext().getSharedPreferences(PREF_NAME, 0).edit();
		editor.putString(LONGITUDE, latitude);
		editor.commit();
	}
}
