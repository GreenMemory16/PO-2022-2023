package prr.app.lookups;


import prr.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;
import java.util.ArrayList;
import prr.terminals.Terminal;

//FIXME add more imports if needed

/**
 * Show unused terminals (without communications).
 */
class DoShowUnusedTerminals extends Command<Network> {

	DoShowUnusedTerminals(Network receiver) {
		super(Label.SHOW_UNUSED_TERMINALS, receiver);
		addStringField("id", Prompt.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
                //FIXME implement command
		List<Terminal> terminal_list = _receiver.getUnusedTerminals(stringField("id"));
		for(int i = 0; i < terminal_list.size(); i++){
			_display.popup(terminal_list.get(i));
		}
			
	}
}
