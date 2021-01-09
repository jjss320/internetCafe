package internetCafe.my.cli;

import java.io.IOException;
import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

/**
 * Command that clears the screen.
 */
@Command(name = "cls", 
	aliases = "clear", 
	mixinStandardHelpOptions = true, 
	description = "Clears the screen", 
	version = "1.0")
public class ClearScreen implements Callable<Void> {
	@ParentCommand
	CliCommands parent;

	public Void call() throws IOException {
		parent.reader.clearScreen();
		return null;
	}
}	