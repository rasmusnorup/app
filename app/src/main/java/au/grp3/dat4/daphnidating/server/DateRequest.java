package au.grp3.dat4.daphnidating.server;

public class DateRequest
{
	private String uid;
	private String latitude;
	private String longitude;
	
	public DateRequest()
	{
	}
	
	public DateRequest(String uid, String latitude, String longitude)
	{
		this.uid = uid;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String getUid()
	{
		return uid;
	}
	
	public void setUid(String uid)
	{
		this.uid = uid;
	}
	
	public String getLatitude()
	{
		return latitude;
	}
	
	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}
	
	public String getLongitude()
	{
		return longitude;
	}
	
	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}
}
