package prr.app.terminals;

import prr.Network;
import prr.app.terminal.Menu;
import prr.terminals.Terminal;

import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add mode import if needed

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

	DoOpenMenuTerminalConsole(Network receiver) {
		super(Label.OPEN_MENU_TERMINAL, receiver);
		addStringField("id", Prompt.terminalKey());
		//FIXME add command fields
	}

	@Override
	protected final void execute() throws CommandException {
		//try{
			Terminal terminal = _receiver.getTerminal(stringField("id"));
			Menu m = new Menu(_receiver, terminal);
			m.open();
		/*} catch(UnknownTerminalKeyException e){
			throw new UnknownTerminalKeyException(e.getId());
		}*/
                //FIXME implement command
                // create an instance of prr.app.terminal.Menu with the
                // selected Terminal
	}
}
