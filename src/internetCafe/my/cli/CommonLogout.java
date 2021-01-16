package internetCafe.my.cli;

import internetCafe.my.api.GuestApi;
import internetCafe.my.model.Guest;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "logout")
public class CommonLogout implements Runnable {
	@Parameters(paramLabel = "Role", description = "The role")
	private String role;

	@ParentCommand
	CliCommands parent;

	public void run() {
		switch (role) {
		case "guest":
			GuestApi api = new GuestApi();
			boolean result = api.logout();
			if (result != false) {
				CliAuth.getInstance().logout();
				parent.out.println("logout success");
			} else {
				parent.out.println("logout fail");
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