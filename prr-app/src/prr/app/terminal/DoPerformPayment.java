package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
import prr.exceptions.InvalidCommunicationExceptionCore;

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

	DoPerformPayment(Network context, Terminal terminal) {
		super(Label.PERFORM_PAYMENT, context, terminal);
		addIntegerField("id", Prompt.commKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try{
			int id = integerField("id");
			_receiver.performPayment(id);
		}
		catch(InvalidCommunicationExceptionCore e) {
			_display.popup(Message.invalidCommunication());
		}
	}
}
