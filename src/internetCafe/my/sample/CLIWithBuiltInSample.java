package internetCafe.my.sample;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.fusesource.jansi.AnsiConsole;
import org.jline.builtins.Builtins;
import org.jline.builtins.Completers.SystemCompleter;
import org.jline.builtins.Options.HelpException;
import org.jline.builtins.Widgets.TailTipWidgets;
import org.jline.builtins.Widgets.TailTipWidgets.TipType;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import internetCafe.my.cli.CliCommands;
import internetCafe.my.cli.DescriptionGenerator;

import picocli.CommandLine;
import picocli.shell.jline3.PicocliCommands;

/**
 * Example that demonstrates how to build an interactive shell with JLine3 and
 * picocli.
 * 
 * @since 4.1.2
 */
public class CLIWithBuiltInSample {

	private static CommandLine cmd;
	private static CliCommands commands;
	private static DescriptionGenerator descriptionGenerator;

	public static void main(String[] args) {
		AnsiConsole.systemInstall();
		try {
			// 기본 제공 명령어 세팅 (JLine built-in 명령어)
			Path workDir = Paths.get("");
			Builtins builtins = new Builtins(workDir, null, null);
			builtins.rename(org.jline.builtins.Builtins.Command.TTOP, "top");
			builtins.alias("zle", "widget");
			builtins.alias("bindkey", "keymap");
			SystemCompleter systemCompleter = builtins.compileCompleters();

			// 추가 명령어 세팅 (picocli 명령어)
			commands = new CliCommands();
			cmd = new CommandLine(commands);
			PicocliCommands picocliCommands = new PicocliCommands(workDir, cmd);
			systemCompleter.add(picocliCommands.compileCompleters());
			systemCompleter.compile();
			Terminal terminal = TerminalBuilder.builder().build();
			LineReader reader = LineReaderBuilder.builder().terminal(terminal).completer(systemCompleter)
					.parser(new DefaultParser()).variable(LineReader.LIST_MAX, 50) // max tab completion candidates
					.build();
			builtins.setLineReader(reader);
			commands.setReader(reader);

			descriptionGenerator = new DescriptionGenerator(builtins, picocliCommands);
			new TailTipWidgets(reader, descriptionGenerator::commandDescriptionWithBuildIn, 5, TipType.COMPLETER);

			// start the shell and process input until the user quits with Ctrl-D
			String prompt = "prompt> ";
			String rightPrompt = null;
			String line;
			while (true) {
				try {
					// 명령어 읽기
					line = reader.readLine(prompt, rightPrompt, (MaskingCallback) null, null);
					if (line.matches("^\\s*#.*")) {
						continue;
					}

					// 명령어 파싱
					ParsedLine pl = reader.getParser().parse(line, 0);
					String[] arguments = pl.words().toArray(new String[0]);
					String command = Parser.getCommand(pl.word());

					// 명령어 정의되었는지 확인
					if (builtins.hasCommand(command)) {
						builtins.execute(command, Arrays.copyOfRange(arguments, 1, arguments.length), System.in,
								System.out, System.err);
					} else {
						new CommandLine(commands).execute(arguments);
					}
				} catch (HelpException e) {
					HelpException.highlight(e.getMessage(), HelpException.defaultStyle()).print(terminal);
				} catch (UserInterruptException e) {
					// Ignore
				} catch (EndOfFileException e) {
					return;
				} catch (Exception e) {
					AttributedStringBuilder asb = new AttributedStringBuilder();
					asb.append(e.getMessage(), AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
					asb.toAttributedString().println(terminal);
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}