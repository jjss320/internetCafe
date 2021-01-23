package internetCafe.my.cli;

import internetCafe.my.api.UserAuth;
import internetCafe.my.api.GuestAPI;
import internetCafe.my.api.HeadAPI;
import internetCafe.my.model.Guest;
import internetCafe.my.model.Head;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "login")
public class CommonLogin implements Runnable {
	@Parameters(paramLabel = "Role", description = "The role")
	private String role;

	@Option(names = { "-i", "--id" }, description = "The ID", required = true)
	private String id;

	@Option(names = { "-p", "--password" }, description = "The Password", required = true)
	private String password;

	@ParentCommand
	CliCommands parent;

	public void run() {
		switch (role) {
		case "guest":{
			GuestAPI api = new GuestAPI();
			Guest guest = api.login(id, password);
			if (guest != null) {
				UserAuth.getInstance().login(guest);
				parent.out.println("login success");
			} else {
				parent.out.println("login fail");
			}
		}
			break;
		case "NonGuest":
			break;
		case "head":{
			HeadAPI api = new HeadAPI();
			Head head = api.login(id, password);
			if (head != null) {
				UserAuth.getInstance().login(head);
				parent.out.println("login success");
			} else {
				parent.out.println("login fail");
			}
		}
			break;
		default:
			parent.out.printf("'%s' is not support\n", role);
			break;
		}
	}
}