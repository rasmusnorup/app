package au.grp3.dat4.daphnidating.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import au.grp3.dat4.daphnidating.DateUtils;
import au.grp3.dat4.daphnidating.R;
import au.grp3.dat4.daphnidating.state.StateManager;

public class DateStartedActivity extends AppCompatActivity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_started);
	}
	
	public void onEndDateHandler(View view)
	{
		StateManager.setState(StateManager.IDLE);
		
		DateUtils.resetStorage();
		
		DateUtils.gotoTopActivity(CreateDateActivity.class);
	}
}
