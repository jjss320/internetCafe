package internetCafe.my.cli;

import internetCafe.my.api.GuestAPI;
import internetCafe.my.api.HeadAPI;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "register")
public class CommonRegister implements Runnable {
	@Parameters(paramLabel = "Role", description = "The role")
	private String role;

	@Option(names = { "-i", "--id" }, description = "The ID")
	private String id;

	@Option(names = { "-n", "--name" }, description = "The Name")
	private String name;

	@Option(names = { "-p", "--password" }, description = "The Password")
	private String password;

	@Option(names = { "-a", "--address" }, description = "The Address, and nullable")
	private String address;

	@ParentCommand
	CliCommands parent;

	public void run() {
		switch (role) {
		case "guest":{
			GuestAPI api = new GuestAPI();
			if (id == null || password == null) {
				parent.out.println("ID or PASSWORD is null, please input again");
				break;
			}
			boolean result = api.signUp(id, name, password);
			if (result) {
				parent.out.println("register success");
			} else {
				parent.out.println("register fail");
			}
		}
			break;
		case "NonGuest":
			break;
		case "head":{
			HeadAPI api = new HeadAPI();
			if (id == null || password == null) {
				parent.out.println("ID or PASSWORD is null, please input again");
				break;
			}
			boolean result = api.signUp(id, name, password);
			if (result) {
				parent.out.println("register success");
			} else {
				parent.out.println("register fail");
			}
		}
			break;
		default:
			parent.out.printf("'%s' is not support\n", role);
			break;
		}
	}
}