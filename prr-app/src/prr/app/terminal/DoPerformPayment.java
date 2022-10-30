package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

	DoPerformPayment(Network context, Terminal terminal) {
		super(Label.PERFORM_PAYMENT, context, terminal);
		addStringField("id", Prompt.commKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try{
			String id = stringField("id");
			_receiver.performPayment(_network, id);
		}
		catch(InvalidCommunicationExceptionCore e) {
			throw new InvalidCommunicationException();
		}
	}
}
