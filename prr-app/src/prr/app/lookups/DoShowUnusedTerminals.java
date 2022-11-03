package prr.app.lookups;


import java.util.ArrayList;
import java.util.List;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show unused terminals (without communications).
 */
class DoShowUnusedTerminals extends Command<Network> {

	DoShowUnusedTerminals(Network receiver) {
		super(Label.SHOW_UNUSED_TERMINALS, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		List<Terminal> terminal_list = new ArrayList<Terminal>(); 
		terminal_list.addAll(_receiver.getUnusedTerminals());
				

		for(int i = 0; i < terminal_list.size(); i++){
			_display.popup(terminal_list.get(i));
		}
			
	}
}
