package prr.app.terminals;

import prr.Network;
import prr.app.terminal.Menu;
import prr.terminals.Terminal;

import prr.app.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnknownTerminalKeyExceptionCore;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

	DoOpenMenuTerminalConsole(Network receiver) {
		super(Label.OPEN_MENU_TERMINAL, receiver);
		addStringField("id", Prompt.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try{
			Terminal terminal = _receiver.getTerminal(stringField("id"));
			Menu m = new Menu(_receiver, terminal);
			m.open();
		} catch(UnknownTerminalKeyExceptionCore e){
			throw new UnknownTerminalKeyException(e.getId());
		}
                // create an instance of prr.app.terminal.Menu with the
                // selected Terminal

				//HERE TO GET THE TERMINAL ID WHEN MENU DESSE TERMINAL
	}
}
