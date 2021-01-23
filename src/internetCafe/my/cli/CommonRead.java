package internetCafe.my.cli;

import internetCafe.my.api.GuestAPI;
import internetCafe.my.api.HeadAPI;
import internetCafe.my.model.Guest;
import internetCafe.my.model.Head;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "read")
public class CommonRead implements Runnable {
	@Parameters(paramLabel = "Role", description = "The role")
	private String role;

	@Option(names = { "-i", "--id" }, description = "The ID")
	private String id;

	@Option(names = { "-al", "--all" }, description = "The Password")
	private Boolean isAll;

	@ParentCommand
	CliCommands parent;

	public void run() {
		switch (role) {
		case "guest":{
			GuestAPI api = new GuestAPI();
			Guest guest = api.read(id);
			if (guest != null) {
				parent.out.println(guest.toString());
			} else {
				parent.out.printf("%s is not exist\n", id);
			}
		}
			break;
		case "NonGuest":
			break;
		case "head":{
			HeadAPI api = new HeadAPI();
			Head head = api.read(id);
			if (head != null) {
				parent.out.println(head.toString());
			} else {
				parent.out.printf("%s is not exist\n", id);
			}
		}
			break;
		default:
			parent.out.printf("'%s' is not support\n", role);
			break;
		}
	}
}