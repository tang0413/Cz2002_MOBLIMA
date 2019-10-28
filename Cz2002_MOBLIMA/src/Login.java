import java.util.Scanner;

public class Login 
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
	
	public boolean login()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("StaffId : ");
		this.userId = sc.nextLine();
		System.out.println("Password : ");
		this.password = sc.nextLine();
		
		if(this.userId.equals("Admin") && this.password.equals("Password"))
			return true;
		else
			return false;
	}
}
