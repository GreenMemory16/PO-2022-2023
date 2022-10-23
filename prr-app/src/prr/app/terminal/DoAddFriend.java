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
			Terminal terminal1 = _receiver;
			Terminal terminal2 = _network.getTerminal(id);
				
			_network.makeFriends(terminal1, terminal2);
		} catch ( UnknownTerminalKeyExceptionCore e ) {
			throw new UnknownTerminalKeyException(e.getId());
		} 
	}
}
