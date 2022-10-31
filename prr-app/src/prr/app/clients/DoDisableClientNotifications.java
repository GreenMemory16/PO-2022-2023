package prr.app.clients;

import prr.Network;
import prr.exceptions.ClientNotificationsAlreadyDefinedException;
import prr.exceptions.UnknownClientKeyExceptionCore;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

	DoDisableClientNotifications(Network receiver) {
		super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
		addStringField("key", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
        try {
			String key = stringField("key");
			_receiver.changeNotifications(key, false);

		} catch (UnknownClientKeyExceptionCore e) {
			throw new UnknownClientKeyException(e.getKey());
		} catch (ClientNotificationsAlreadyDefinedException e) {
			_display.popup(Message.clientNotificationsAlreadyDisabled());
		}
	}
}
