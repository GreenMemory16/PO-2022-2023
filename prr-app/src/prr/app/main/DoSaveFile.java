package prr.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import prr.exceptions.MissingFileAssociationException;
import prr.NetworkManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
//FIXME add more imports if needed

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

	DoSaveFile(NetworkManager receiver) {
		super(Label.SAVE_FILE, receiver);
		addStringField("filename", Prompt.saveAs());
	}

	@Override
	protected final void execute() {
		String saveFilename = stringField("filename");

        try {
			if (saveFilename != null) {
				_receiver.save(saveFilename);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MissingFileAssociationException e) {
			e.printStackTrace();
		}
		
		
	}
}
