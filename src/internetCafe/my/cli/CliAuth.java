package internetCafe.my.cli;

import internetCafe.my.model.Guest;

public class CliAuth {
	private static CliAuth instance;
	private Guest loginUser;
	
	// Singleton Pattern
	public static CliAuth getInstance() {
		if(instance == null) {
			instance = new CliAuth();
		}
		return instance;
	}
	
	public void login(Guest guest) {
		this.loginUser = guest;
	}
	
	public boolean isLogin() {
		return (loginUser != null) ? true : false;
	}
	
	public String getLoginName() {
		return loginUser.getName();
	}
	
	public Guest getUser() {
		return loginUser;
	}
	
	public void logout() {
		loginUser = null;
	}
}