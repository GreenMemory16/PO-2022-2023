package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show balance.
 */
class DoShowTerminalBalance extends TerminalCommand {

	DoShowTerminalBalance(Network context, Terminal terminal) {
		super(Label.SHOW_BALANCE, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		long payments = _receiver.getAllPayments();
		long debts = _receiver.getAllDebts();
		String terminal = _receiver.getId();

		_display.popup(Message.terminalPaymentsAndDebts(terminal,payments, debts));
	}
}
