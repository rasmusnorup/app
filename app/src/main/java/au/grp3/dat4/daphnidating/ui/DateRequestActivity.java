package au.grp3.dat4.daphnidating.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import au.grp3.dat4.daphnidating.DateUtils;
import au.grp3.dat4.daphnidating.R;
import au.grp3.dat4.daphnidating.server.Date;
import au.grp3.dat4.daphnidating.server.DateMatch;
import au.grp3.dat4.daphnidating.server.ServerCommunicator;
import au.grp3.dat4.daphnidating.server.User;
import au.grp3.dat4.daphnidating.services.MessageConstants;
import au.grp3.dat4.daphnidating.state.StateManager;
import au.grp3.dat4.daphnidating.state.StorageManager;

public class DateRequestActivity extends AppCompatActivity
{
	private static final String TAG = DateRequestActivity.class.getSimpleName();
	
	private BroadcastReceiver receiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_request);
		
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent)
			{
				String msg = intent.getStringExtra(MessageConstants.BROADCAST_MESSAGE);
				if(msg.equals(MessageConstants.MATCH_DECLINE_MESSAGE))
					handleDecline();
			}
		};
		
		ServerCommunicator.getUser(StorageManager.getPartnerUID(), new OnSuccessListener<DocumentSnapshot>()
		{
			@Override
			public void onSuccess(DocumentSnapshot docSnap)
			{
				User user = docSnap.toObject(User.class);
				
				((TextView) findViewById(R.id.name)).setText(user.getName());
				((TextView) findViewById(R.id.age)).setText(user.getAge());
				((TextView) findViewById(R.id.gender)).setText(user.getGender());
				((TextView) findViewById(R.id.pref1)).setText(user.getPref1());
				((TextView) findViewById(R.id.pref2)).setText(user.getPref2());
				((TextView) findViewById(R.id.pref3)).setText(user.getPref3());
			}
		});
		
		ServerCommunicator.getMatch(StorageManager.getMatchID(), new OnSuccessListener<DocumentSnapshot>()
		{
			@Override
			public void onSuccess(DocumentSnapshot docSnap)
			{
				DateMatch match = docSnap.toObject(DateMatch.class);
				((TextView) findViewById(R.id.pref3)).setText(match.getPlaceName());
			}
		});
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
		
		if(!StateManager.getState().equals(StateManager.MATCH))
		{
			handleDecline();
		}
	}
	
	private void handleDecline()
	{
		createToast("The other party cancelled");
		DateUtils.gotoTopActivity(CreateDateActivity.class);
	}
	
	@Override
	protected void onStop()
	{
		LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
		super.onStop();
	}
	
	public void onAcceptHandler(View view)
	{
		ServerCommunicator.updateMatchResponse("accept");
		StateManager.setState(StateManager.WAIT_FOR_RESPONSE);
		DateUtils.gotoTopActivity(WaitForResponseActivity.class);
	}
	
	public void onDeclineHandler(View view)
	{
		ServerCommunicator.updateMatchResponse("decline");
		StateManager.setState(StateManager.IDLE);
		DateUtils.gotoTopActivity(CreateDateActivity.class);
	}
	
	private void createToast(String msg)
	{
		Toast.makeText(this, msg	, Toast.LENGTH_LONG).show();
	}
}
