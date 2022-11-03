package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.app.exceptions.InvalidTerminalKeyException;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.exceptions.CantStartCommunicationException;
import prr.exceptions.DestinationIsOffException;
import prr.exceptions.SenderEqualsReceiverException;
import prr.exceptions.UnknownTerminalKeyExceptionCore;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

        DoSendTextCommunication(Network context, Terminal terminal) {
                super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
                addStringField("id", Prompt.terminalKey());
                addStringField("text", Prompt.textMessage());
        }

        @Override
        protected final void execute() throws CommandException {
                try {
                        String id = stringField("id");
                        String text = stringField("text");
                        _receiver.makeTextCommunication(_network, id, text);
                } catch (UnknownTerminalKeyExceptionCore e) {
                        throw new UnknownTerminalKeyException(e.getId());
                } catch (DestinationIsOffException e) {
                        _display.popup(Message.destinationIsOff(e.getId()));
                } catch (CantStartCommunicationException e) {
                        //nothing
                } 
        }
} 
