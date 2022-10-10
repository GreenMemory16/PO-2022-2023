package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.DuplicateTerminalKeyException;
import prr.app.exceptions.InvalidTerminalKeyException;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

	DoRegisterTerminal(Network receiver) {
		super(Label.REGISTER_TERMINAL, receiver);
		addStringField("clientkey", Prompt.clientKey());
    	addStringField("id", Prompt.terminalKey());
    	//addStringField("type", Prompt.terminalType());
		//the problem is that tecnhically terminal is an
		//asbtract class, sooooo we must think about how
		//we wanna implement this
	}

	@Override
	protected final void execute() throws CommandException {
		String clientkey = stringField("clientkey");
		String id = stringField("id");
		//Sring type = integerField("type");
		_receiver.registerTerminal(id, clientkey /*,type*/);
                //FIXME please
	}
}
