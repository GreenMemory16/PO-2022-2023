package prr.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

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
                //FIXME add command fields
	}

	@Override
	protected final void execute() throws CommandException {
                try {
                        if (_reciever.changed()) {
                                DoSaveFile cmd = new DoSaveFile(_reciever);
                                cmd.execute();
                        }
                        _reciever.load(Form.requestString(Message.OPEN_FILE));
                } catch (FileNotFoundException e) {
                        _display.popup(Message.fileNotFound());
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch(IOException e) {
                        e.printStackTrace();
                }



                /*
                        try {
                                //FIXME implement command
                        } catch (UnavailableFileException e) {
                                throw new FileOpenFailedException(e);
                        }
                */

	}
}
