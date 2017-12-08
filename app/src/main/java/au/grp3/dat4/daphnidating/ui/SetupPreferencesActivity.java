package au.grp3.dat4.daphnidating.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import au.grp3.dat4.daphnidating.R;
import au.grp3.dat4.daphnidating.server.ServerCommunicator;
import au.grp3.dat4.daphnidating.server.User;

public class SetupPreferencesActivity extends AppCompatActivity
{
	
	private static final String TAG = SetupPreferencesActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_preferences);
	}
	
	public void onSetDefaultHandler(View view)
	{
		FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
		
		User user = new User(
				currentUser.getUid(),
				FirebaseInstanceId.getInstance().getToken(),
				"Kenneth",
				"male",
				24,
				"Øl",
				"Fisse",
				"Hornmusik",
				50,
				18,
				1,
				"female"
		);
		
		ServerCommunicator.createUser(user, new OnSuccessListener()
		{
			@Override
			public void onSuccess(Object o)
			{
				finish();
			}
		});
	}
}
