package internetCafe.my.cli;

import internetCafe.my.api.GuestAPI;
import internetCafe.my.api.HeadAPI;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "update")
public class CommonUpdate implements Runnable {
	@Parameters(paramLabel = "Role", description = "The role")
	private String role;

	@Option(names = { "-i", "--id" }, description = "The ID")
	private String id;

	@Option(names = { "-n", "--name" }, description = "The Name")
	private String name;

	@Option(names = { "-p", "--password" }, description = "The Password")
	private String password;

	@Option(names = { "-a", "--address" }, description = "The Address")
	private String address;

	@ParentCommand
	CliCommands parent;

	public void run() {
		switch (role) {
		case "guest":{
			if (id == "")
				break;
			GuestAPI api = new GuestAPI();
			boolean result = api.update(id, name, password, address);
			if (result) {
				parent.out.println("update success");
			} else {
				parent.out.println("update fail");
			}
		}
			break;
		case "head":{
			if (id == "")
				break;
			HeadAPI api = new HeadAPI();
			boolean result = api.update(id, name, password, address);
			if (result) {
				parent.out.println("update success");
			} else {
				parent.out.println("update fail");
			}
		}
			break;
		default:
			parent.out.printf("'%s' is not support\n", role);
			break;
		}
	}
}