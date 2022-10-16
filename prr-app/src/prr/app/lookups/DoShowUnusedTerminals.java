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
	}

	@Override
	protected final void execute() throws CommandException {
                //FIXME implement command
		List<Terminal> terminal_list = new ArrayList<Terminal>(); //ERROR
		System.out.println(terminal_list);
		//terminal_list.addAll(_receiver.getUnusedTerminals());
				//System.out.println(_receiver.getUnusedTerminals());

		for(int i = 0; i < terminal_list.size(); i++){
			_display.popup(terminal_list.get(i));
		}
		//i must put this in the right format
			
	}
}
