package prr.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import prr.exceptions.MissingFileAssociationException;
import prr.NetworkManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

	DoSaveFile(NetworkManager receiver) {
		super(Label.SAVE_FILE, receiver);
	}

	@Override
	protected final void execute() {
        try {
			_receiver.save();
		} catch (MissingFileAssociationException ex) {
			try {
				_receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
			} catch (MissingFileAssociationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch(FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		
	}

}
