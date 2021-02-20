package internetCafe.my.cli;

import internetCafe.my.api.GuestAPI;
import internetCafe.my.api.HeadAPI;
import internetCafe.my.api.UserAuth;
import internetCafe.my.model.Guest;
import internetCafe.my.model.Head;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "read")
public class CommonRead implements Runnable {
	@ParentCommand
	CliCommands parent;

	public void run() {
		if(UserAuth.getInstance().isLogin() == true) {
			if(UserAuth.getInstance().getGuest() != null) {
				GuestAPI api = new GuestAPI();
				Guest guest = api.read();
				if (guest != null) {
					parent.out.println(guest.toString());
				} else {
					parent.out.printf("you are not exist\n");
				}
			}
			else if(UserAuth.getInstance().getHead() != null) {
				HeadAPI api = new HeadAPI();
				Head head = api.read();
				if (head != null) {
					parent.out.println(head.toString());
				} else {
					parent.out.printf("you are not exist\n");
				}
			}
			else {
				parent.out.println("Unknown User");
			}
		}
		else {
			System.out.println("Please Login");
		}
	}
}