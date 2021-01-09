package internetCafe.my.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

/**
 * A command with some options to demonstrate completion.
 */
@Command(name = "print", 
	mixinStandardHelpOptions = true, 
	version = "1.0", 
	description = "Command with some options to demonstrate TAB-completion (note that enum values also get completed)", 
	subcommands = CommandLine.HelpCommand.class)
public class PrintInfo implements Runnable  {
	@Parameters(paramLabel = "FILE", description = "The name")
    private String name;

	@Option(names = { "-i", "--id" }, description = "The student ID", required = true)
	private String id = "";
	
	@Option(names = { "-p", "--phone" }, description = "The phone number", required = false)
	private String phone = "";
	
	@ParentCommand
	CliCommands parent;

	public void run() {
		if (phone != "") {
			parent.out.printf("hi! %s(%s). your phone number is %s%n", name, id, phone);
		} else {
			parent.out.printf("hi! %s(%s)\n", name, id);
		}
	}
}