package au.grp3.dat4.daphnidating.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import au.grp3.dat4.daphnidating.DateUtils;
import au.grp3.dat4.daphnidating.R;
import au.grp3.dat4.daphnidating.server.ServerCommunicator;
import au.grp3.dat4.daphnidating.services.MessageConstants;
import au.grp3.dat4.daphnidating.state.StateManager;

public class DateStatusActivity extends AppCompatActivity
{
	private BroadcastReceiver receiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_status);
		
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent)
			{
				String msg = intent.getStringExtra(MessageConstants.BROADCAST_MESSAGE);
				if(msg.equals(MessageConstants.DATE_ARRIVED_MESSAGE))
				{
					//Update ui to reflect that partner arrived
				}
				else if(msg.equals(MessageConstants.DATE_CANCELLED_MESSAGE) ||
						msg.equals(MessageConstants.DATE_START_MESSAGE))
				{
					updateBasedOnState();
				}
			}
		};
	}
	
	private void updateBasedOnState()
	{
		if(StateManager.getState().equals(StateManager.DATE))
		{
			//When on your way to the date
		}
		else if(StateManager.getState().equals(StateManager.ARRIVED))
		{
			//When waiting for partner
		}
		else if(StateManager.getState().equals(StateManager.IDLE))
		{
			DateUtils.resetStorage();
			DateUtils.gotoTopActivity(CreateDateActivity.class);
		}
		else if(StateManager.getState().equals(StateManager.DATE_STARTED))
		{
			DateUtils.gotoTopActivity(DateStartedActivity.class);
		}
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
		
		if(!StateManager.getState().equals(StateManager.REQUEST))
			updateBasedOnState();
	}
	
	@Override
	protected void onStop()
	{
		LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
		super.onStop();
	}
	
	public void onArriveClickHandler(View view)
	{
		ServerCommunicator.updateDateArrival(new OnSuccessListener<DocumentSnapshot>(){
			@Override
			public void onSuccess(DocumentSnapshot documentSnapshot)
			{
				StateManager.setState(StateManager.ARRIVED);
				updateBasedOnState();
			}
		});
	}
}
