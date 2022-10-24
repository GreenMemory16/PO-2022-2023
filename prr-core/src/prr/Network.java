package prr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import prr.clients.Client;
import prr.exceptions.DuplicateClientKeyExceptionCore;
import prr.exceptions.DuplicateTerminalKeyExceptionCore;
import prr.exceptions.ImportFileException;
import prr.exceptions.InvalidTerminalKeyExceptionCore;
import prr.exceptions.TerminalTypeNotSupportedException;
import prr.exceptions.UnknownClientKeyExceptionCore;
import prr.exceptions.UnknownTerminalKeyExceptionCore;
import prr.exceptions.UnrecognizedEntryException;
import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;
import prr.terminals.Silence;
import prr.terminals.Off;
import prr.terminals.Idle;
import prr.terminals.Busy;



public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private Map<String, Client> _clients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	private Map<String, Terminal> _terminals = new TreeMap<String, Terminal>();

	private int _communicationNumber = 1;

	private boolean hasChanged = false;

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
         * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException if there is an IO erro while processing the text file
	 * @throws ImportFileException
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException, ImportFileException {
		try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
			String s;
			while ((s = in.readLine()) != null) {
				String line = new String(s.getBytes(), "UTF-8");
				if (line.charAt(0) == '#') {
					continue;
				}
				String[] fields = line.split("\\|");
				switch(fields[0]) {
					case "CLIENT" -> registerClient(fields[1], fields[2], Integer.parseInt(fields[3])); 
					case "BASIC", "FANCY" -> registerTerminal(fields[1], fields[2], fields[0], fields[3]);
					case "FRIENDS" -> makeFriends(_terminals.get(fields[1]), _terminals.get(fields[2]));
					default -> throw new UnrecognizedEntryException(fields[0]);
				}
			} 
		} catch (DuplicateClientKeyExceptionCore | UnknownClientKeyExceptionCore | TerminalTypeNotSupportedException |
		 		 InvalidTerminalKeyExceptionCore | DuplicateTerminalKeyExceptionCore e ) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new ImportFileException(filename);
		}
	}
	
	/**
	 * Registers a client;
	 * 
	 * @param key is the client key;
	 	* @throws DuplicateClientKeyExceptionCore if the client key is already in use;
	* @param name is the client name;
	* @param taxId is the client tax id;

	* @return the client;
	 */
	 
	public Client registerClient(String key, String name, int taxId) throws DuplicateClientKeyExceptionCore {
		if (_clients.containsKey(key)) {
			throw new DuplicateClientKeyExceptionCore(key);
		}

		Client client = new Client(key, name, taxId);
		_clients.put(key, client);

		return client;
	}


	/**
	 * Gets a certain client;
	 * 
	 * @param id is the client ID;
	 	* @throws UnknownClientKeyExceptionCore if the client key is not in use;
	 * @return the client;
	 */
	public Client getClient(String key) throws UnknownClientKeyExceptionCore {
		Client c = _clients.get(key);
		if (c == null) {
			throw new UnknownClientKeyExceptionCore(key);
		}
		return c;
	}

	/**
	 * Gets all the clients;
	 * @return collection of all clients;
	 */	
	public Collection<Client> getAllClients() {
		return Collections.unmodifiableCollection(_clients.values());
	}

	/**
	 * Changes Notifications settings of a specific client
	 * @param key is the client key;
	 	* @throws UnknownClientKeyExceptionCore if the client key is not in use;
	* @param value is the new value of the notifications settings;

	 */
	 
	public void changeNotifications(String key, Boolean value) throws UnknownClientKeyExceptionCore{
		getClient(key).setActiveNotifications(value);
	}


/** ********************Terminal related methods*************************** */



	/**
	 * Switches the terminal state.
	 * 
	 * @param state is the new state;
	 * @param terminal is the terminal to be switched;
	 */

	public void switchState(String state, Terminal terminal) {
		if(state.equals("ON")) {
			terminal.setState(new Idle(terminal));
		}
		else if(state.equals("OFF")) {
			terminal.setState(new Off(terminal));
		}
		else if(state.equals("SILENCE")) {
			terminal.setState(new Silence(terminal));
		}
		else if(state.equals("BUSY")) {
			terminal.setState(new Busy(terminal));
		}
	}	

	/**
	 * Registers the terminal;
	 * 
	 * @param id is the terminal ID;
	 	* @throws InvalidTerminalKeyExceptionCore if the terminal ID is invalid;
	 	* @throws DuplicateTerminalKeyExceptionCore if the terminal ID is already registered;
	 * @param clientKey is the terminal's client key;
	 	* @throws UnknownClientKeyExceptionCore if the client key is unknown;
	 * @param type is the type of the terminal, either FANCY or BASIC;
	 	* @throws TerminalTypeNotSupportedException if the terminal type is not supported;
	 * @param state is the state of the terminal, by default is IDLE;
	 
	 * @return the terminal;
	 */
	public Terminal registerTerminal(String id, String clientKey, String type, String state) 
				throws UnknownClientKeyExceptionCore, TerminalTypeNotSupportedException, 
				InvalidTerminalKeyExceptionCore, DuplicateTerminalKeyExceptionCore 
				 {

		if (_terminals.containsKey(id)) {
			throw new DuplicateTerminalKeyExceptionCore(id);
		}
		if (!(_clients.containsKey(clientKey))) {
			throw new UnknownClientKeyExceptionCore(clientKey);
		}
		Terminal terminal;

		if(type.equals("BASIC")){
			terminal = new Basic(id, clientKey);
		}		
		else if(type.equals("FANCY")) {
			terminal = new Fancy(id, clientKey);
		}

		else {
			throw new TerminalTypeNotSupportedException();
		}

		if(state.equals("ON")) {
			terminal.setState(new Idle(terminal));
		}
		else if(state.equals("OFF")) {
			terminal.setState(new Off(terminal));
		}
		else if(state.equals("SILENCE")) {
			terminal.setState(new Silence(terminal));
		}


		switchState(state, terminal);

		// Registers the terminal in the client _terminals list
		getClient(clientKey).insertTerminal(terminal);
		
		//register the terminal in the network list
		_terminals.put(id, terminal);

		return terminal;
	}

	/**
	 * Gets a certain terminal;
	 * 
	 * @param id is the terminal ID;
	 	* @throws UnknownTerminalKeyExceptionCore if the terminal ID is unknown;
	 * @return the terminal;
	 */
	public Terminal getTerminal(String id) throws UnknownTerminalKeyExceptionCore {
		Terminal terminal = _terminals.get(id);
		if (terminal == null) {
			throw new UnknownTerminalKeyExceptionCore(id);
		}
		return terminal;
	}

	/**
	 * Gets the terminals that have no communications;
	 * @return terminal list;
	 */
	public Collection<Terminal> getUnusedTerminals() {
		List<Terminal> terminal_list = new ArrayList<Terminal>();
		for(Map.Entry<String,Terminal> entry : _terminals.entrySet()){
			if(entry.getValue().noCommunications()){
				terminal_list.add(entry.getValue());
			}
		}
		return terminal_list;
	}

	/**
	 * Gets all the terminals;
	 * @return collection of all terminals;
	 */	
	public Collection<Terminal> getAllTerminals() {
		return Collections.unmodifiableCollection(_terminals.values());
	}

	/**
	 * Adds a terminal friend;
	 * @param terminal1 is the terminal that wants to add a friend;
	 * @param terminal2 is the terminal that is going to be added as a friend;
	 */
	public void makeFriends(Terminal terminal1, Terminal terminal2) {
		terminal1.addFriend(terminal2);
	}


	/**
	 * Removes a terminal friend;
	 * @param terminal1 is the terminal that wants to remove a friend;
	 * @param terminal2 is the terminal that is going to be removed as a friend;
	 */
	public void deMakeFriends( Terminal terminal1, Terminal terminal2){
		terminal1.removeFriend(terminal2);
	}

/* *************************Communication Methods**************************** */
	

}

