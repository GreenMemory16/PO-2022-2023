package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME !

/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {

	DoAddFriend(Network context, Terminal terminal) {
		super(Label.ADD_FRIEND, context, terminal);
		//addStringField("id", Prompt.terminalKey());
		//how am i gonna pass the terminal to execute
		//it should take an id not a terminal
		//addStringField("terminal", terminal);
	}

	@Override
	protected final void execute() throws CommandException {
                //FIXME implement command
				//try{

				//}
				/*Terminal terminal = stringField("terminal");
				String id = stringField("id");
				_receiver.makeFriends(terminal, id);*/

	}
}
