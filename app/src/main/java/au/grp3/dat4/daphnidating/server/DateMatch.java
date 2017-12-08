package au.grp3.dat4.daphnidating.server;

/**
 * Created by PeterStat on 08-12-2017.
 */

public class DateMatch
{
	private String participant1;
	private String participant2;
	private String response1;
	private String response2;
	private String latitude;
	private String longitude;
	private String placeName;
	
	public DateMatch()
	{
	}
	
	public DateMatch(String participant1, String participant2, String response1, String response2, String latitude, String longitude, String placeName)
	{
		this.participant1 = participant1;
		this.participant2 = participant2;
		this.response1 = response1;
		this.response2 = response2;
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
	
	public String getResponse1()
	{
		return response1;
	}
	
	public void setResponse1(String response1)
	{
		this.response1 = response1;
	}
	
	public String getResponse2()
	{
		return response2;
	}
	
	public void setResponse2(String response2)
	{
		this.response2 = response2;
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
