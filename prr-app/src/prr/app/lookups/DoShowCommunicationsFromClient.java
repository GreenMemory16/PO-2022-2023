package prr.app.lookups;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import prr.exceptions.UnknownClientKeyExceptionCore;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show communications from a client.
 */
class DoShowCommunicationsFromClient extends Command<Network> {

	DoShowCommunicationsFromClient(Network receiver) {
		super(Label.SHOW_COMMUNICATIONS_FROM_CLIENT, receiver);
		addStringField("key", Prompt.clientKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			String key = stringField("key");
			_display.popup(_receiver.getAllCommunicationsFromClient(key));
		} catch (UnknownClientKeyExceptionCore e) {
			throw new UnknownClientKeyException(e.getKey());
		}
	}
}