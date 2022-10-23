package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
import prr.exceptions.AlreadySilentExceptionCore;
import prr.exceptions.AlreadyInStateException;
//FIXME add more imports if needed

/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {

	DoSilenceTerminal(Network context, Terminal terminal) {
		super(Label.MUTE_TERMINAL, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		try {
        	_receiver.switchToSilence();
		} catch (AlreadyInStateException e) {
			System.out.println("HELLO");
			_display.popup(Message.alreadySilent());
		}
	}
}
