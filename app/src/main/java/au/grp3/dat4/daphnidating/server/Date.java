package au.grp3.dat4.daphnidating.server;

public class Date
{
	private String participant1;
	private String participant2;
	private String latitude;
	private String longitude;
	private String placeName;
	
	public Date()
	{
	}
	
	public Date(String participant1, String participant2, String latitude, String longitude, String placeName)
	{
		this.participant1 = participant1;
		this.participant2 = participant2;
		this.latitude = latitude;
		this.longitude = longitude;
		this.placeName = placeName;
	}
	
	public String getPlaceName()
	{
		return placeName;
	}
	
	public void setPlaceName(String placeName)
	{
		this.placeName = placeName;
	}
	
	public String getParticipant1()
	{
		return participant1;
	}
	
	public void setParticipant1(String participant1)
	{
		this.participant1 = participant1;
	}
	
	public String getParticipant2()
	{
		return participant2;
	}
	
	public void setParticipant2(String participant2)
	{
		this.participant2 = participant2;
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
