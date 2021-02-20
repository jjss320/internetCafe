package internetCafe.my.api;

import internetCafe.my.model.Guest;
import internetCafe.my.model.Head;

public class UserAuth {
	private static UserAuth instance;
	private Guest loginGuest;
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
		this.loginGuest = guest;
	}
	// Guest
	public Guest getGuest() {
		return loginGuest;
	}
	
//	// Head, Guest
//	public getUser() {
//		return loginUser;
//	}
	
	// Head, Guest
	public boolean isLogin() {
		return (loginGuest != null | loginHead != null) ? true : false;
	}
	// Head, Guest
	public String getLoginName() {
		return loginGuest != null ? loginGuest.getName() : loginHead.getName();
	}
	// Head, Guest
	public void logout() {
		if(loginGuest != null) {
			loginGuest = null;
		} else {
			loginHead = null;
		}
	}
}