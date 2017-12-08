package au.grp3.dat4.daphnidating.server;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import au.grp3.dat4.daphnidating.state.StateManager;
import au.grp3.dat4.daphnidating.state.StorageManager;

public class ServerCommunicator
{
	private static final String TAG = ServerCommunicator.class.getSimpleName();
	
	public static void createUser(User user, OnSuccessListener onSuccessListener)
	{
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		
		onSuccessListener = createIfNull(onSuccessListener);
		
		db.collection("users").document(user.getUid())
				.set(user)
				.addOnSuccessListener(onSuccessListener)
				.addOnFailureListener(new OnFailureListener()
				{
					@Override
					public void onFailure(@NonNull Exception e)
					{
						Log.e(TAG, "ERROR: Unable to add user: " + e);
					}
				});
	}
	
	public static void getUser(final String userID, OnSuccessListener onSuccessListener)
	{
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		
		onSuccessListener = createIfNull(onSuccessListener);
		
		db.collection("users").document(userID)
				.get()
				.addOnSuccessListener(onSuccessListener)
				.addOnFailureListener(new OnFailureListener()
				{
					@Override
					public void onFailure(@NonNull Exception e)
					{
						Log.e(TAG, "ERROR: Unable to load user with id: " + userID);
						Log.e(TAG, "Error: " + e);
					}
				});
	}
	
	public static void createRequest(DateRequest request, OnSuccessListener onSuccessListener)
	{
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		
		onSuccessListener = createIfNull(onSuccessListener);
		
		db.collection("requests")
				.add(request)
				.addOnSuccessListener(onSuccessListener)
				.addOnFailureListener(new OnFailureListener()
				{
					@Override
					public void onFailure(@NonNull Exception e)
					{
						Log.e(TAG, "ERROR: Unable to add user: " + e);
					}
				});
	}
	
	public static void getMatch(final String matchID, OnSuccessListener onSuccessListener)
	{
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		
		onSuccessListener = createIfNull(onSuccessListener);
		
		db.collection("matches")
				.document(matchID)
				.get()
				.addOnSuccessListener(onSuccessListener)
				.addOnFailureListener(new OnFailureListener()
				{
					@Override
					public void onFailure(@NonNull Exception e)
					{
						Log.e(TAG, "ERROR: Unable to fetch Match with id: " + matchID);
						Log.e(TAG, "Error: ", e);
					}
				});
	}
	
	public static void updateMatchResponse(final String response)
	{
		final FirebaseFirestore db = FirebaseFirestore.getInstance();
		
		db.collection("matches").document(StorageManager.getMatchID())
				.get()
				.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
				{
					@Override
					public void onSuccess(DocumentSnapshot documentSnapshot)
					{
						db.collection("matches").document(StorageManager.getMatchID())
								.update("response" + String.valueOf(StorageManager.getOwnPlacement()), response)
								.addOnSuccessListener(new OnSuccessListener<Void>()
								{
									@Override
									public void onSuccess(Void aVoid)
									{
										Log.i(TAG,"Updated match on server to: " + response);
									}
								})
								.addOnFailureListener(new OnFailureListener()
								{
									@Override
									public void onFailure(@NonNull Exception e)
									{
										Log.i(TAG, "Unable to update match on server: " + e);
									}
								});
					}
				})
				.addOnFailureListener(new OnFailureListener()
				{
					@Override
					public void onFailure(@NonNull Exception e)
					{
						Log.i(TAG, "Unable to fetch match: " + e);
						StateManager.setState(StateManager.IDLE);
						//TODO: Does the server contain unwanted data at this point?
					}
				});
	}
	
	public static void updateDateArrival(OnSuccessListener onSuccessListener)
	{
		final FirebaseFirestore db = FirebaseFirestore.getInstance();
		
		db.collection("dates").document(StorageManager.getMatchID())
				.update("participant" + String.valueOf(StorageManager.getOwnPlacement()) + "Arrived", true)
				.addOnSuccessListener(onSuccessListener)
				.addOnFailureListener(new OnFailureListener()
				{
					@Override
					public void onFailure(@NonNull Exception e)
					{
						Log.e(TAG, "ERROR: Unable to update date status on server");
					}
				});
	}
	
	private static OnSuccessListener createIfNull(OnSuccessListener onSuccessListener)
	{
		if(onSuccessListener == null)
		{
			onSuccessListener = new OnSuccessListener()
			{
				@Override
				public void onSuccess(Object o)
				{
				
				}
			};
		}
		
		return onSuccessListener;
	}
}
