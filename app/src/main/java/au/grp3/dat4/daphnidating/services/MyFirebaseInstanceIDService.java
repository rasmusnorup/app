package au.grp3.dat4.daphnidating.services;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{
	private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
	
	public MyFirebaseInstanceIDService()
	{
	}
	
	@Override
	public void onTokenRefresh()
	{
		String newToken = FirebaseInstanceId.getInstance().getToken();
		Log.i(TAG, "Token refreshed: " + newToken);
		
		updateServer(newToken);
	}
	
	private void updateServer(String newToken)
	{
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
		
		if(currentUser == null)
			return;
		
		String uid = currentUser.getUid();
		
		DocumentReference userRef = db.collection("users").document(uid);
		
		userRef.update("token", newToken)
				.addOnSuccessListener(new OnSuccessListener<Void>()
				{
					@Override
					public void onSuccess(Void aVoid)
					{
						Log.i(TAG, "Updated token on server");
					}
				})
				.addOnFailureListener(new OnFailureListener()
				{
					@Override
					public void onFailure(@NonNull Exception e)
					{
						Log.i(TAG, "Unable to update token on server: " + e);
					}
				});
	}
}
