package prr.app.clients;

import prr.Network;
import prr.exceptions.DuplicateClientKeyExceptionCore;
import prr.app.exceptions.DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

	DoRegisterClient(Network receiver) {
		super(Label.REGISTER_CLIENT, receiver);
		addStringField("key", Prompt.key());
    	addStringField("name", Prompt.name());
    	addIntegerField("taxId", Prompt.taxId());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			String key = stringField("key");
			String name = stringField("name");
			int taxId = integerField("taxId");
			//registers the client
			_receiver.registerClient(key, name, taxId);

		} catch (DuplicateClientKeyExceptionCore e) {
			throw new DuplicateClientKeyException(e.getKey());
		}
		
	}

}
