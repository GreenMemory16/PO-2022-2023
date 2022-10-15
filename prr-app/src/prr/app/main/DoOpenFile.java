package prr.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import prr.exceptions.UnavailableFileException;
import prr.NetworkManager;
import prr.app.exceptions.FileOpenFailedException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {

	DoOpenFile(NetworkManager receiver) {
		super(Label.OPEN_FILE, receiver);
        addStringField("filename", Prompt.openFile());
	}

	@Override
	protected final void execute() throws CommandException {
		String filename = stringField("filename");
                
        try {
			_receiver.load(filename);

        } catch (UnavailableFileException e) {
			throw new FileOpenFailedException(e);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}