package internetCafe.my.api;

import internetCafe.my.model.Guest;
import internetCafe.my.model.Head;

public class UserAuth {
	private static UserAuth instance;
	private Guest loginUser;
	private Head loginHead;
	
	// Singleton Pattern
	public static UserAuth getInstance() {
		if(instance == null) {
			instance = new UserAuth();
		}
		return instance;
	}
	
	public void login(Head head) {
		this.loginHead = head;
	}
	
	public Head getHead() {
		return loginHead;
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