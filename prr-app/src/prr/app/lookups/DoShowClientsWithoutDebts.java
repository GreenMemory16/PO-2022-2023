package prr.app.lookups;

import prr.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.util.List;
import java.util.ArrayList;
import prr.clients.Client;
/**
 * Show clients with positive balance.
 */
class DoShowClientsWithoutDebts extends Command<Network> {

	DoShowClientsWithoutDebts(Network receiver) {
		super(Label.SHOW_CLIENTS_WITHOUT_DEBTS, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		List<Client> client_list = new ArrayList<Client>(); 
		client_list.addAll(_receiver.getNoDebtsClient());
				

		for(int i = 0; i < client_list.size(); i++){
			_display.popup(client_list.get(i));
		}
	}
}
