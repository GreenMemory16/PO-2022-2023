package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnknownTerminalKeyExceptionCore;
import prr.exceptions.InvalidFriendExceptionCore;

import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {

	DoAddFriend(Network context, Terminal terminal) {
		super(Label.ADD_FRIEND, context, terminal);
		//terminal that's gonna be added as friend
		addStringField("id", Prompt.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			String id = stringField("id");
			_receiver.makeFriends(_network, id);
			
		} catch ( UnknownTerminalKeyExceptionCore e ) {
			throw new UnknownTerminalKeyException(e.getId());
		} 
	}
}