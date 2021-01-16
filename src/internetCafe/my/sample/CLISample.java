package internetCafe.my.sample;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.fusesource.jansi.AnsiConsole;
import org.jline.builtins.Widgets.TailTipWidgets;
import org.jline.builtins.Widgets.TailTipWidgets.TipType;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import internetCafe.my.cli.CliAuth;
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
public class CLISample {

	private static CommandLine cmd;
	private static CliCommands commands;
	private static DescriptionGenerator descriptionGenerator;
	private static CliAuth auth = CliAuth.getInstance();

	public static void main(String[] args) {
		AnsiConsole.systemInstall();
		try {
			// 명령어 세팅
			commands = new CliCommands();
			cmd = new CommandLine(commands);
			Path workDir = Paths.get("");
			PicocliCommands picocliCommands = new PicocliCommands(workDir, cmd);
			Terminal terminal = TerminalBuilder.builder().build();
			LineReader reader = LineReaderBuilder.builder().terminal(terminal).parser(new DefaultParser())
					.variable(LineReader.LIST_MAX, 50) // max tab completion candidates
					.build();
			commands.setReader(reader);

			descriptionGenerator = new DescriptionGenerator(picocliCommands);
			new TailTipWidgets(reader, descriptionGenerator::commandDescription, 5, TipType.COMPLETER);

			// start the shell and process input until the user quits with Ctrl-D
			String prompt = "";
			String rightPrompt = null;
			String line;
			while (true) {
				try {
					if(auth.isLogin()) {
						prompt = auth.getLoginName() + "> ";
					} else {
						prompt = "prompt> ";
					}
					
					// 명령어 읽기
					line = reader.readLine(prompt, rightPrompt, (MaskingCallback) null, null);
					if (line.matches("^\\s*#.*")) {
						continue;
					}
					
					// 명령어 파싱
					ParsedLine pl = reader.getParser().parse(line, 0);
					String[] arguments = pl.words().toArray(new String[0]);

					// 명령어 실행
					new CommandLine(commands).execute(arguments);
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