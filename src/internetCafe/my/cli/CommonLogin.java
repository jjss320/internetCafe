package internetCafe.my.cli;

import internetCafe.my.api.GuestApi;
import internetCafe.my.model.Guest;
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

	@Option(names = { "-p", "--password" }, description = "The guest Password", required = true)
	private String password;

	@ParentCommand
	CliCommands parent;

	public void run() {
		switch (role) {
		case "guest":
			GuestApi api = new GuestApi();
			Guest guest = api.login(id, password);
			if (guest != null) {
				CliAuth.getInstance().login(guest);
				parent.out.println("login success");
			} else {
				parent.out.println("login fail");
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