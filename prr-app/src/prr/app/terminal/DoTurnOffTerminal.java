package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
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

		//_network.SwitchState("OFF", _receiver);

		//_network.Message.alreadyOff();
                //FIXME implement command
	}
}
