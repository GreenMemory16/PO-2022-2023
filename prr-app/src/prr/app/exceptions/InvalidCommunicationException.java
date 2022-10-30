package prr.app.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Exception thrown when a communication is invalid.
 */
 public class InvalidCommunicationException extends CommandException {
 	private static final long serialVersionUID = 202208091753L;


 	@Override
 	public final String getMessage() {
 		return Message.invalidCommunication();
 	}
 }
