package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.DuplicateTerminalKeyException;
import prr.app.exceptions.InvalidTerminalKeyException;
import prr.app.exceptions.UnknownClientKeyException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import prr.exceptions.DuplicateTerminalKeyExceptionCore;
import prr.exceptions.InvalidTerminalKeyExceptionCore;
import prr.exceptions.TerminalTypeNotSupportedException;
import prr.exceptions.UnknownClientKeyExceptionCore;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {
	String[] options = new String[]{"BASIC", "FANCY"};

	DoRegisterTerminal(Network receiver) {
		super(Label.REGISTER_TERMINAL, receiver);
    	addStringField("id", Prompt.terminalKey());
		addOptionField("type", Prompt.terminalType(), options);
		addStringField("clientkey", Prompt.clientKey());

	}

	@Override
	protected final void execute() throws CommandException {

		try{
			String id = stringField("id");
			String type = stringField("type");
			String clientkey = stringField("clientkey");
			_receiver.registerTerminal(id, clientkey ,type);
			
		}
		catch(DuplicateTerminalKeyExceptionCore e){
			throw new DuplicateTerminalKeyException(e.getId());
		}
		catch(InvalidTerminalKeyExceptionCore e){
			throw new InvalidTerminalKeyException(e.getId());
		}
		catch(UnknownClientKeyExceptionCore e){
			throw new UnknownClientKeyException(e.getKey());
		}
		
		
		     
	}
}
