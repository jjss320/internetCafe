package internetCafe.my.cli;

import internetCafe.my.api.UserAuth;
import internetCafe.my.api.GuestAPI;
import internetCafe.my.api.HeadAPI;
import internetCafe.my.model.Guest;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "logout")
public class CommonLogout implements Runnable {
	@ParentCommand
	CliCommands parent;

	public void run() {
		if(UserAuth.getInstance().isLogin() == true) {
			if(UserAuth.getInstance().getGuest() != null) {
				GuestAPI api = new GuestAPI();
				boolean result = api.logout();
				if (result != false) {
					UserAuth.getInstance().logout();
					parent.out.println("logout success");
				} else {
					parent.out.println("logout fail");
				}
			}
			
			else if(UserAuth.getInstance().getHead() != null) {
				HeadAPI api = new HeadAPI();
				boolean result = api.logout();
				if (result != false) {
					UserAuth.getInstance().logout();
					parent.out.println("logout success");
				} else {
					parent.out.println("logout fail");
				}
			}
			
			else {
				parent.out.println("Unknown User");
			}
		}
	}
}