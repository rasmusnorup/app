package au.grp3.dat4.daphnidating.services;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import au.grp3.dat4.daphnidating.server.DateMatch;
import au.grp3.dat4.daphnidating.server.ServerCommunicator;
import au.grp3.dat4.daphnidating.state.StateManager;
import au.grp3.dat4.daphnidating.state.StorageManager;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
	private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
	private LocalBroadcastManager broadcaster;
	
	public MyFirebaseMessagingService()
	{
	
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		broadcaster = LocalBroadcastManager.getInstance(this);
	}
	
	@Override
	public void onMessageReceived(RemoteMessage remoteMessage)
	{
		super.onMessageReceived(remoteMessage);
		
		Map data = remoteMessage.getData();
		final String messageType = (String) data.get(MessageConstants.MESSAGE_TYPE);
		
		Log.i(TAG, "Got message: ");
		Log.i(TAG, "Title: " + remoteMessage.getNotification().getTitle());
		Log.i(TAG, "Body: " + remoteMessage.getNotification().getBody());
		Log.i(TAG, "Data: " + remoteMessage.getData());
		
		switch (messageType)
		{
			case MessageConstants.MATCH_INITIAL_MESSAGE:
				
				final String matchID = (String) data.get("matchID");
				ServerCommunicator.getMatch(matchID, new OnSuccessListener<DocumentSnapshot>()
				{
					@Override
					public void onSuccess(DocumentSnapshot documentSnapshot)
					{
						DateMatch match = documentSnapshot.toObject(DateMatch.class);
						
						String ownUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
						int ownPlacement;
						String matchUID;
						
						if(match.getParticipant1().equals(ownUID))
						{
							ownPlacement = 1;
							matchUID = match.getParticipant2();
						}
						else
						{
							ownPlacement = 2;
							matchUID = match.getParticipant1();
						}
						
						StorageManager.setPartnerUID(matchUID);
						StorageManager.setMatchID(matchID);
						StorageManager.setOwnPlacement(ownPlacement);
						StorageManager.setLatitude(match.getLatitude());
						StorageManager.setLongitude(match.getLongitude());
						
						StateManager.setState(StateManager.MATCH);
						
						Log.i(TAG, "Got a match");
						
						sendBroadcast(messageType);
					}
				});
				
				break;
			case MessageConstants.MATCH_ACCEPT_MESSAGE:
				StateManager.setState(StateManager.DATE);
				
				Log.i(TAG, "MATCH ACCEPT");
				
				sendBroadcast(messageType);
				
				//Create Geofence based on date location. Use ServerCommunication
				
				break;
			case MessageConstants.MATCH_DECLINE_MESSAGE:
				if(!StateManager.getState().equals(StateManager.MATCH))
					return;
				
				StateManager.setState(StateManager.IDLE);
				
				Log.i(TAG, "DECLINE");
				
				sendBroadcast(messageType);
				
				break;
			case MessageConstants.DATE_CANCELLED_MESSAGE:
				//TODO: Implement
				break;
			case MessageConstants.DATE_ARRIVED_MESSAGE:
				//If already arrived - ignore
				if(StateManager.getState().equals(StateManager.ARRIVED))
					break;
				
				StorageManager.setPartnerArrived(true);
				
				Log.i(TAG, "DATE ARRIVED");
				
				sendBroadcast(messageType);
				
				break;
			case MessageConstants.DATE_START_MESSAGE:
				StateManager.setState(StateManager.DATE_STARTED);
		}
	}
	
	private void sendBroadcast(String msg)
	{
		Intent intent = new Intent(MessageConstants.BROADCAST_RESULT);
		intent.putExtra(MessageConstants.BROADCAST_MESSAGE, msg);
		broadcaster.sendBroadcast(intent);
	}
}
