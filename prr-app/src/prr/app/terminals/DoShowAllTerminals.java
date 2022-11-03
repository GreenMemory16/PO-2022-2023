package prr.app.terminals;

import prr.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Shows all terminals.
 */
class DoShowAllTerminals extends Command<Network> {

	DoShowAllTerminals(Network receiver) {
		super(Label.SHOW_ALL_TERMINALS, receiver);
	}

	//no exception will be thrown here since the method getTerminals() doesn't throw any exception
	@Override
	protected final void execute() throws CommandException {
		
		_display.popup(_receiver.getAllTerminals());
	}
}
