package control;

public class LoginProcess 
{
	private String userId;
	private String password;
	
	public String getUserId()
	{
		return userId;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public boolean loginCheck()
	{
		if(this.userId.equals(userId) && this.password.equals(password))
			return true;
		else
			return false;
	}
	
}
