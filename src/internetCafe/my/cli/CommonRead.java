package internetCafe.my.cli;

import internetCafe.my.api.GuestApi;
import internetCafe.my.model.Guest;

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

	@Option(names = { "-al", "--all" }, description = "The guest Password")
	private Boolean isAll;

	@ParentCommand
	CliCommands parent;

	public void run() {
		switch (role) {
		case "guest":
			GuestApi api = new GuestApi();
			Guest guest = api.read(id);
			if (guest != null) {
				parent.out.println(guest.toString());
			} else {
				parent.out.printf("%s is not exist\n", id);
			}
			break;
		case "NonGuest":
			break;
		default:
			parent.out.printf("'%s' is not support\n", role);
			break;
		}
	}
}