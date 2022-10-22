package prr.app.clients;

import prr.Network;
import prr.exceptions.UnknownClientKeyExceptionCore;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

	DoEnableClientNotifications(Network receiver) {
		super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
		addStringField("key", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
        try {
			String key = stringField("key");
			_receiver.changeNotifications(key, true);

		} catch (UnknownClientKeyExceptionCore e) {
			throw new UnknownClientKeyException(e.getKey());
		}
	}
}
