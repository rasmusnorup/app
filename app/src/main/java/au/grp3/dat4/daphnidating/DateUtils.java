package au.grp3.dat4.daphnidating;

import android.content.Intent;

import au.grp3.dat4.daphnidating.state.StorageManager;

public class DateUtils
{
	public static void gotoMainActivity()
	{
		gotoTopActivity(MainActivity.class);
	}
	
	public static void gotoTopActivity(Class target)
	{
		Intent intent = new Intent(MyApp.getContext(), target);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		MyApp.getContext().startActivity(intent);
	}
	
	public static void resetStorage()
	{
		StorageManager.setOwnPlacement(-1);
		StorageManager.setMatchID(null);
		StorageManager.setLatitude(null);
		StorageManager.setLongitude(null);
		StorageManager.setPartnerUID(null);
		StorageManager.setPartnerArrived(false);
	}
}
