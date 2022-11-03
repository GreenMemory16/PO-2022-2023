package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
import prr.exceptions.UnknownTerminalKeyExceptionCore;
import prr.app.exceptions.UnknownTerminalKeyException;

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {

	DoRemoveFriend(Network context, Terminal terminal) {
		super(Label.REMOVE_FRIEND, context, terminal);
		//terminal that's gonna be removed as friend
		addStringField("id", Prompt.terminalKey());	}

	@Override
	protected final void execute() throws CommandException {
		try{
			String id = stringField("id");
			_receiver.removeFriend(_network, id);
		}
		catch(UnknownTerminalKeyExceptionCore e){
			throw new UnknownTerminalKeyException(e.getId());
		}	
	}
}
