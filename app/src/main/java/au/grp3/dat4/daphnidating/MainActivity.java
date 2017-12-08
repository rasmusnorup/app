package au.grp3.dat4.daphnidating;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import au.grp3.dat4.daphnidating.state.StateManager;
import au.grp3.dat4.daphnidating.ui.CreateDateActivity;
import au.grp3.dat4.daphnidating.ui.DateRequestActivity;
import au.grp3.dat4.daphnidating.ui.DateSearchActivity;
import au.grp3.dat4.daphnidating.ui.DateStatusActivity;
import au.grp3.dat4.daphnidating.ui.LoginActivity;
import au.grp3.dat4.daphnidating.ui.SetupPreferencesActivity;
import au.grp3.dat4.daphnidating.ui.WaitForResponseActivity;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionsmenu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.gotoMain:
				DateUtils.gotoMainActivity();
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
    protected void onStart() {
        super.onStart();

        //TODO: Check if online

       user = mAuth.getCurrentUser();
        
        if(user == null) //Not signed in
        {
            Log.i(TAG, "Not logged in. Going to LoginActivity");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else
		{
			checkIfPreferencesIsSet();
		}
    }
	
	private void checkIfPreferencesIsSet()
	{
		db.collection("users")
				.whereEqualTo("uid", user.getUid())
				.get()
				.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
				{
					@Override
					public void onComplete(@NonNull Task<QuerySnapshot> task)
					{
						if(task.isSuccessful())
						{
							QuerySnapshot documents = task.getResult();
							if(documents.size() == 0)
							{
								//First time user
								setupPreferences();
							}
							else if(documents.size() == 1)
							{
								//Already registered
								checkState();
							}
							else
							{
								throw new RuntimeException("Multiple users in database with the same UID");
							}
						}
						else
						{
							Log.e(TAG, "Unable to load users");
						}
					}
				});
	}
	
	private void checkState()
	{
		Intent intent = null;
		
		Log.i(TAG, "Checking state: " + StateManager.getState());
		
		switch(StateManager.getState())
		{
			case StateManager.IDLE:
				Log.i(TAG, "IDLE State - Going to Create Date Activity");
				intent = new Intent(this, CreateDateActivity.class);
				break;
			case StateManager.REQUEST:
				Log.i(TAG, "REQUEST State - Going to Date Searching State");
				intent = new Intent(this, DateSearchActivity.class);
				break;
			case StateManager.MATCH:
				Log.i(TAG, "MATCH State - Going to Date Request State");
				intent = new Intent(this, DateRequestActivity.class);
				break;
			case StateManager.WAIT_FOR_RESPONSE:
				Log.i(TAG, "WAIT FOR REPLY State - Going to Wait for Response Activity");
				intent = new Intent(this, WaitForResponseActivity.class);
				break;
			case StateManager.DATE:
			case StateManager.ARRIVED:
				Log.i(TAG, "DATE | ARRIVED State - Going to State Status Activity");
				intent = new Intent(this, DateStatusActivity.class);
				break;
			default:
				throw new RuntimeException("StateManager is in an invalid state: " + StateManager.getState());
		}
		
		startActivity(intent);
	}
	
	private void setupPreferences()
	{
		StateManager.setState(StateManager.IDLE);
		Intent intent = new Intent(this, SetupPreferencesActivity.class);
		startActivity(intent);
	}
}
