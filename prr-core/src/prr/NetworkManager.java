package prr;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Collection;

import prr.clients.Client;
import prr.terminals.Terminal;
import prr.exceptions.DuplicateClientKeyExceptionCore;
import prr.exceptions.ImportFileException;
import prr.exceptions.MissingFileAssociationException;
import prr.exceptions.TerminalTypeNotSupportedException;
import prr.exceptions.UnavailableFileException;
import prr.exceptions.UnknownClientKeyExceptionCore;
import prr.exceptions.UnrecognizedEntryException;

//FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

	/** The network itself. */
	private Network _network = new Network();
        //FIXME  addmore fields if needed

        public Network getNetwork() {
		return _network;
	}

	/**
	 * @param filename name of the file containing the serialized application's state
         *        to load.
	 * @throws UnavailableFileException if the specified file does not exist or there is
         *         an error while processing this file.
	 */
	public void load(String filename) throws UnavailableFileException, IOException, ClassNotFoundException {
		try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))){
			System.out.println("Loaded from file " + filename);
			_network = (Network) in.readObject();
		} 
	}

	/**
         * Saves the serialized application's state into the file associated to the current network.
         *
	 * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
	 * @throws MissingFileAssociationException if the current network does not have a file.
	 * @throws IOException if there is some error while serializing the state of the network to disk.
	 */
	public void save(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
		try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
			out.writeObject(_network);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
         * Saves the serialized application's state into the specified file. The current network is
         * associated to this file.
         *
	 * @param filename the name of the file.
	 * @throws FileNotFoundException if for some reason the file cannot be created or opened.
	 * @throws MissingFileAssociationException if the current network does not have a file.
	 * @throws IOException if there is some error while serializing the state of the network to disk.
	 */
	public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
		save(filename);
	}

	/**
	 * Read text input file and create domain entities..
	 * 
	 * @param filename name of the text input file
	 * @throws ImportFileException
	 * @throws TerminalTypeNotSupportedException
	 */
	public void importFile(String filename) throws ImportFileException {
		try {
			_network.importFile(filename);
        } catch (IOException e) {
			e.printStackTrace();
		} catch (UnrecognizedEntryException e) {
			e.printStackTrace();
		} 
    	
	}
}
