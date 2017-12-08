package au.grp3.dat4.daphnidating.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import au.grp3.dat4.daphnidating.DateUtils;
import au.grp3.dat4.daphnidating.R;
import au.grp3.dat4.daphnidating.services.MessageConstants;
import au.grp3.dat4.daphnidating.state.StateManager;

public class WaitForResponseActivity extends AppCompatActivity
{
	private BroadcastReceiver receiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wait_for_response);
		
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent)
			{
				moveBasedOnState();
			}
		};
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
				new IntentFilter(MessageConstants.BROADCAST_RESULT)
		);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		moveBasedOnState();
	}
	
	@Override
	protected void onStop()
	{
		LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
		super.onStop();
	}
	
	private void moveBasedOnState()
	{
		if(StateManager.getState().equals(StateManager.DATE))
		{
			DateUtils.gotoTopActivity(DateStatusActivity.class);
		}
		else if(StateManager.getState().equals(StateManager.IDLE))
		{
			DateUtils.gotoTopActivity(CreateDateActivity.class);
		}
	}
}
