package au.grp3.dat4.daphnidating.server;

public class User
{
	private String uid;
	private String token;
	
	private String name;
	private String gender;
	private int age;
	private String pref1;
	private String pref2;
	private String pref3;
	private int maxAge;
	private int minAge;
	private int maxDistance;
	private String targetGender;
	
	public User()
	{
	
	}
	
	public User(String uid, String token, String name, String gender, int age, String pref1, String pref2, String pref3, int maxAge, int minAge, int maxDistance, String targetGender)
	{
		this.uid = uid;
		this.token = token;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.pref1 = pref1;
		this.pref2 = pref2;
		this.pref3 = pref3;
		this.maxAge = maxAge;
		this.minAge = minAge;
		this.maxDistance = maxDistance;
		this.targetGender = targetGender;
	}
	
	public String getToken()
	{
		return token;
	}
	
	public void setToken(String token)
	{
		this.token = token;
	}
	
	public void setUid(String uid)
	{
		this.uid = uid;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setGender(String gender)
	{
		this.gender = gender;
	}
	
	public void setAge(int age)
	{
		this.age = age;
	}
	
	public void setPref1(String pref1)
	{
		this.pref1 = pref1;
	}
	
	public void setPref2(String pref2)
	{
		this.pref2 = pref2;
	}
	
	public void setPref3(String pref3)
	{
		this.pref3 = pref3;
	}
	
	public void setMaxAge(int maxAge)
	{
		this.maxAge = maxAge;
	}
	
	public void setMinAge(int minAge)
	{
		this.minAge = minAge;
	}
	
	public void setMaxDistance(int maxDistance)
	{
		this.maxDistance = maxDistance;
	}
	
	public void setTargetGender(String targetGender)
	{
		this.targetGender = targetGender;
	}
	
	public String getUid()
	{
		return uid;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getGender()
	{
		return gender;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public String getPref1()
	{
		return pref1;
	}
	
	public String getPref2()
	{
		return pref2;
	}
	
	public String getPref3()
	{
		return pref3;
	}
	
	public int getMaxAge()
	{
		return maxAge;
	}
	
	public int getMinAge()
	{
		return minAge;
	}
	
	public int getMaxDistance()
	{
		return maxDistance;
	}
	
	public String getTargetGender()
	{
		return targetGender;
	}
}
