package au.grp3.dat4.daphnidating.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import au.grp3.dat4.daphnidating.DateUtils;
import au.grp3.dat4.daphnidating.R;
import au.grp3.dat4.daphnidating.server.DateRequest;
import au.grp3.dat4.daphnidating.server.ServerCommunicator;
import au.grp3.dat4.daphnidating.state.StateManager;

public class CreateDateActivity extends AppCompatActivity
{
	
	private static final int PERMISSION_REQUEST = 1;
	
	private static final String TAG = CreateDateActivity.class.getSimpleName();
	
	private FusedLocationProviderClient fusedLocationProviderClient;
	
	private boolean haveRequestedLocation = false;
	private boolean lookingForDate = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_date);
		
		fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		if(checkPermission())
		{
			requestSingleLocationUpdate();
		}
		else
		{
			requestPermission();
		}
	}
	
	private void requestSingleLocationUpdate()
	{
		haveRequestedLocation = true;
		
		LocationRequest locationRequest = new LocationRequest()
				.setInterval(10000)
				.setNumUpdates(1)
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		
		LocationCallback callback = new LocationCallback() {
			@Override
			public void onLocationResult(LocationResult locationResult)
			{
				super.onLocationResult(locationResult);
				Log.i(TAG, "Updated location");
			}
		};
		
		if(checkPermission())
			fusedLocationProviderClient.requestLocationUpdates(locationRequest, callback, null);
	}
	
	public void onCreateDateClickHandler(View view)
	{
		requestDate();
	}
	
	private void requestDate()
	{
		lookingForDate = true;
		
		if(checkPermission())
		{
			fusedLocationProviderClient.getLastLocation()
					.addOnSuccessListener(new OnSuccessListener<Location>()
					{
						@Override
						public void onSuccess(Location location)
						{
							if(location == null)
							{
								makeToast("Unable to get you location");
							}
							else
							{
								requestWithLocation(location);
							}
						}
					});
		}
		else
		{
			requestPermission();
			return;
		}
		
		lookingForDate = false;
	}
	
	private void requestWithLocation(Location location)
	{
		FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
		
		DateRequest request = new DateRequest(currentUser.getUid(), String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
		ServerCommunicator.createRequest(request, new OnSuccessListener()
		{
			@Override
			public void onSuccess(Object o)
			{
				handleRequestSuccessful();
			}
		});
	}
	
	private void handleRequestSuccessful()
	{
		StateManager.setState(StateManager.REQUEST);
		DateUtils.gotoTopActivity(DateSearchActivity.class);
	}
	
	private void makeToast(String msg)
	{
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
	
	private void requestPermission()
	{
		ActivityCompat.requestPermissions(this, new String[]{
				Manifest.permission.ACCESS_FINE_LOCATION},
				PERMISSION_REQUEST
		);
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		
		if(requestCode == PERMISSION_REQUEST)
		{
			boolean gotPermission = true;
			
			for(int i = 0; i < permissions.length; i++)
			{
				if(grantResults[i] == PackageManager.PERMISSION_DENIED)
				{
					gotPermission = false;
					Log.d(TAG, "Didn't get permission from user");
				}
			}
			
			if(gotPermission)
			{
				if(haveRequestedLocation)
					requestSingleLocationUpdate();
				
				if(lookingForDate)
					requestDate();
			}
		}
	}
	
	private boolean checkPermission()
	{
		return (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
	}
}
