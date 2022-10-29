package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnknownTerminalKeyExceptionCore;
import prr.terminals.Terminal;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {
	String[] options = new String[]{"VOICE", "VIDEO"};

	DoStartInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
		addStringField("id", Prompt.terminalKey());
		addOptionField("type", Prompt.commType(), options);
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			String id = stringField("id");
			String type = stringField("type");
			_receiver.makeInteractiveCommunication(_network, id, type);
		} catch (UnknownTerminalKeyExceptionCore e) {
			throw new UnknownTerminalKeyException(e.getId());
		}
	}
}
