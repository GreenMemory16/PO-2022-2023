package prr.app.clients;

import prr.clients.Client;
import prr.Network;
import prr.exceptions.UnknownClientKeyExceptionCore;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

	DoShowClientPaymentsAndDebts(Network receiver) {
		super(Label.SHOW_CLIENT_BALANCE, receiver);
		addStringField("key", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			Client client = (Client) _receiver.getClient(stringField("key"));
        	//_display.popup(_receiver.getClient(stringField("key")));
			_display.popup(Message.clientPaymentsAndDebts("key", client.Payments(), client.Debts()));
		}
		catch (UnknownClientKeyExceptionCore e) {
			throw new UnknownClientKeyException(e.getKey());
		}
	}
	}
