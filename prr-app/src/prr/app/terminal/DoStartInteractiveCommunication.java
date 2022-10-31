package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.exceptions.DestinationIsBusyException;
import prr.exceptions.DestinationIsOffException;
import prr.exceptions.DestinationIsSilentException;
import prr.exceptions.SenderEqualsReceiverException;
import prr.exceptions.UnknownTerminalKeyExceptionCore;
import prr.exceptions.UnsupportedAtDestinationException;
import prr.exceptions.UnsupportedAtOriginException;
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
		} catch (DestinationIsOffException e) {
			_display.popup(Message.destinationIsOff(e.getId()));
		} catch (DestinationIsBusyException e) {
			_display.popup(Message.destinationIsBusy(e.getId()));
		} catch (DestinationIsSilentException e) {
			_display.popup(Message.destinationIsSilent(e.getId()));
		} catch (UnsupportedAtDestinationException e) {
			_display.popup(Message.unsupportedAtDestination(e.getId(), e.getType()));
		} catch (UnsupportedAtOriginException e) {
			_display.popup(Message.unsupportedAtOrigin(e.getId(), e.getType()));
		} catch (SenderEqualsReceiverException e) {
			_display.popup(Message.destinationIsBusy(e.getId()));
		}
	}
}