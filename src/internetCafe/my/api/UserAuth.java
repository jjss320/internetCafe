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
	// Head
	public void login(Head head) {
		this.loginHead = head;
	}
	// Head
	public Head getHead() {
		return loginHead;
	}
	// Guest
	public void login(Guest guest) {
		this.loginUser = guest;
	}
	// Guest
	public Guest getUser() {
		return loginUser;
	}
	// Head, Guest
	public boolean isLogin() {
		return (loginUser != null | loginHead != null) ? true : false;
	}
	// Head, Guest
	public String getLoginName() {
		return loginUser != null ? loginUser.getName() : loginHead.getName();
	}
	// Head, Guest
	public void logout() {
		if(loginUser != null) {
			loginUser = null;
		} else {
			loginHead = null;
		}
	}
}