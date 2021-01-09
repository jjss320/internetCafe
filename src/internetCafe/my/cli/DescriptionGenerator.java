package internetCafe.my.cli;

import org.jline.builtins.Builtins;
import org.jline.builtins.Widgets.CmdDesc;
import org.jline.builtins.Widgets.CmdLine;
import org.jline.reader.Parser;

import picocli.shell.jline3.PicocliCommands;

/**
 * Provide command descriptions for JLine TailTipWidgets to be displayed in the
 * status bar.
 */
public class DescriptionGenerator {
	Builtins builtins;
	PicocliCommands picocli;

	public DescriptionGenerator(PicocliCommands picocli) {
		this.picocli = picocli;
	}
	
	public CmdDesc commandDescription(CmdLine line) {
		CmdDesc out = null;
		switch (line.getDescriptionType()) {
		case COMMAND:
			String cmd = Parser.getCommand(line.getArgs().get(0));
			if (picocli.hasCommand(cmd)) {
				out = picocli.commandDescription(cmd);
			}
			break;
		default:
			break;
		}
		return out;
	}
	
	public DescriptionGenerator(Builtins builtins, PicocliCommands picocli) {
		this.builtins = builtins;
		this.picocli = picocli;
	}

	public CmdDesc commandDescriptionWithBuildIn(CmdLine line) {
		CmdDesc out = null;
		switch (line.getDescriptionType()) {
		case COMMAND:
			String cmd = Parser.getCommand(line.getArgs().get(0));
			if (builtins.hasCommand(cmd)) {
				out = builtins.commandDescription(cmd);
			} else if (picocli.hasCommand(cmd)) {
				out = picocli.commandDescription(cmd);
			}
			break;
		default:
			break;
		}
		return out;
	}
}