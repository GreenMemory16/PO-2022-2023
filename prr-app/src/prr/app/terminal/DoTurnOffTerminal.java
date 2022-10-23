package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
import prr.exceptions.AlreadyInStateException;
//FIXME add more imports if needed
//to be implemented later on

/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {

	DoTurnOffTerminal(Network context, Terminal terminal) {
		super(Label.POWER_OFF, context, terminal);
		//addstringField(Prompt.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.turnOff();
		} catch (AlreadyInStateException e) {
			_display.popup(Message.alreadyOff());
		}

		//_network.SwitchState("OFF", _receiver);

		//_network.Message.alreadyOff();
                //FIXME implement command
	}
}
