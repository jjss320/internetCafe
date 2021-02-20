package internetCafe.my.cli;

import internetCafe.my.api.GuestAPI;
import internetCafe.my.api.HeadAPI;
import internetCafe.my.api.UserAuth;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "update")
public class CommonUpdate implements Runnable {
	@Option(names = { "-i", "--id" }, description = "The ID is Primary")
	private String id;

	@Option(names = { "-n", "--name" }, description = "The Name")
	private String name;

	@Option(names = { "-p", "--password" }, description = "The Password")
	private String password;

	@Option(names = { "-a", "--age" }, description = "The Age")
	private int age;
	
	@Option(names = { "-pn", "--phoneNumber" }, description = "The PhoneNumber")
	private String phoneNumber;
	
	@Option(names = { "-ad", "--address" }, description = "The Address")
	private String address;
	
	@Option(names = { "-pc", "--pcNum" }, description = "The PCNumber")
	private int pcNum;

	@ParentCommand
	CliCommands parent;

	public void run() {
		if(UserAuth.getInstance().isLogin() == true) {
			if(UserAuth.getInstance().getGuest() != null) {
				GuestAPI api = new GuestAPI();
				boolean result = api.update(id, name, password, age, phoneNumber, address);
				if (result) {
					parent.out.println("update success");
				} else {
					parent.out.println("update fail");
				}
			}
			
			else if(UserAuth.getInstance().getHead() != null) {
				HeadAPI api = new HeadAPI();
				boolean result = api.update(id, name, password, address, pcNum);
				if (result) {
					parent.out.println("update success");
				} else {
					parent.out.println("update fail");
				}
			}
			else {
				parent.out.println("Unknown User");
			}
		}
	}
}