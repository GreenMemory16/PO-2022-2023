package prr.app.lookups;

import prr.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import prr.clients.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Show clients with negative balance.
 */
class DoShowClientsWithDebts extends Command<Network> {

	DoShowClientsWithDebts(Network receiver) {
		super(Label.SHOW_CLIENTS_WITH_DEBTS, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		List<Client> client_list = new ArrayList<Client>(); 
		client_list.addAll(_receiver.getYesDebtsClient());
				

		for(int i = 0; i < client_list.size(); i++){
			_display.popup(client_list.get(i));
		}
	}
}
