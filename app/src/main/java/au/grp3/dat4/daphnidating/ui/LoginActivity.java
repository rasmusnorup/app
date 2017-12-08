package au.grp3.dat4.daphnidating.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import au.grp3.dat4.daphnidating.R;

public class LoginActivity extends AppCompatActivity {
	
	private static final String TAG = LoginActivity.class.getSimpleName();
	private static final int RC_SIGN_IN = 123;
	
	List<AuthUI.IdpConfig> providers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		providers = Collections.singletonList(
				new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
		);
		
		requestLogin();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == RC_SIGN_IN)
		{
			IdpResponse response = IdpResponse.fromResultIntent(data);
			
			if(resultCode == Activity.RESULT_OK)
			{
				Log.i(TAG, "Login successfully: " + FirebaseAuth.getInstance().getCurrentUser());
				finish();
			}
			else
			{
				Log.i(TAG, "Login unsuccessful, trying again");
				
				requestLogin();
			}
		}
	}
	
	private void requestLogin()
	{
		startActivityForResult(AuthUI.getInstance()
						.createSignInIntentBuilder()
						.setAvailableProviders(providers)
						.build(),
				RC_SIGN_IN
		);
	}
}
