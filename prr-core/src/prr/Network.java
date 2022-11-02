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
import prr.communication.Communication;
import prr.exceptions.ClientNotificationsAlreadyDefinedException;
import prr.exceptions.DuplicateClientKeyExceptionCore;
import prr.exceptions.DuplicateTerminalKeyExceptionCore;
import prr.exceptions.ImportFileException;
import prr.exceptions.InvalidFriendExceptionCore;
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
import prr.notifications.Notification;


/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	// Map of all clients created
	private Map<String, Client> _clients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	// Map of all terminals created
	private Map<String, Terminal> _terminals = new TreeMap<String, Terminal>();

	//Current communication number
	private int _communicationNumber = 0;

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
					case "FRIENDS" -> importFriends(fields);
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
	 * Imports clients
	 * 
	 * @param fields array of fields
	 	*@throws UnknownTermianlKeyExceptionCore if the terminal key is unknown 
	 */
	public void importFriends(String[] fields) {
		try {
			Terminal terminal = getTerminal(fields[1]);
			String[] friendsIds = fields[2].split(",");
			for(int i=0; i < friendsIds.length; i++) {
				Terminal friend = getTerminal(friendsIds[i]);
				makeFriends(terminal, friend);
			}
		} catch (UnknownTerminalKeyExceptionCore e) {
			e.printStackTrace();
		} 
			
	}
	
	/**
	 * Registers a new client;
	 * 
	 * @param key is the client key
	 	*@throws DuplicateClientKeyExceptionCore if the client key is already registered
	*@param name is the client name
	*@param taxId is the client tax id
	 * @return client created
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
	 * @param key is the client key
	 	*@throws UnknownClientKeyExceptionCore if the client does not exist
	 * @return c is the client 
	 */
	public Client getClient(String key) throws UnknownClientKeyExceptionCore {
		Client c = _clients.get(key);
		if (c == null) {
			throw new UnknownClientKeyExceptionCore(key);
		}
		return c;
	}

	/**
	 * Gets all clients.
	 
	 * @return a Collection of clients 
	 */
	public Collection<Client> getAllClients() {
		return Collections.unmodifiableCollection(_clients.values());
	}

	/**
	 * Gets the notifications from a certain client.
	 * 
	 * @param key is the client key
	 	*@throws UnknownClientKeyExceptionCore if the client does not exist
	 * @return an Array of notifications 
	 */

	public ArrayList<Notification> getNotifications(String key) throws UnknownClientKeyExceptionCore {
		ArrayList<Notification> notifications = new ArrayList<>(getClient(key).getNotifications());
		getClient(key).removeNotifications();
		return notifications;
	}

	/**
	 * Changes the notifications of a client to either active or not active;
	 * 
	 * @param key is the terminal key
	 	*@throws UnknownClientKeyExceptionCore if the client does not exist
	 * @param value is the boolean value to change the notifications to
	 */
	public void changeNotifications(String key, Boolean value) throws UnknownClientKeyExceptionCore, 
	ClientNotificationsAlreadyDefinedException{
		getClient(key).setActiveNotifications(value);
	}


/** ********************Terminal related methods*************************** */

	/**
	 * Auxiliary function to check if a string is numeric
	 * 
	 * @param str is the string
	 * @return the boolean value true if the string is numeric, false otherwise
	 */
	public static boolean isNumeric(String str) { 
		try {  
			Double.parseDouble(str);
			return true;
		} catch(NumberFormatException e){  
		return false;  
		}  
	}

/**
	 * Switches the terminal to the another state;
	 * 
	 * @param state the new state
	 * @param terminal the terminal to be switched
	 */
	

	public void SwitchState(String state, Terminal terminal) {
		if(state.equals("ON")) {
			terminal.setState(new Idle(terminal, true));
		}
		else if(state.equals("OFF")) {
			terminal.setState(new Off(terminal, terminal.getState().getPreviousIdle()));
		}
		else if(state.equals("SILENCE")) {
			terminal.setState(new Silence(terminal, false));
		}
	}	

	/**
	 * Registers a terminal;
	 * 
	 * @param id terminal id
	 	* @throws InvalidTerminalKeyExceptionCore if the terminal id is invalid
		* @throws DuplicateTerminalKeyExceptionCore if the terminal id is already registered
	 * @param clientkey client key
	 	* @throws UnknownClientKeyExceptionCore if the client key is unknown
	 * @param type terminal type
	 	* @throws TerminalTypeNotSupportedException if the terminal type is not supported
	 * @param state terminal state
	 * @return terminal the registered terminal
	 */
	public Terminal registerTerminal(String id, String clientKey, String type, String state) 
				throws UnknownClientKeyExceptionCore, TerminalTypeNotSupportedException, 
				InvalidTerminalKeyExceptionCore, DuplicateTerminalKeyExceptionCore {

		if (id.length() != 6 || !(isNumeric(id))) {
			throw new InvalidTerminalKeyExceptionCore(id);
		}
		if (_terminals.containsKey(id)) {
			throw new DuplicateTerminalKeyExceptionCore(id);
		}
		if (!(_clients.containsKey(clientKey))) {
			throw new UnknownClientKeyExceptionCore(clientKey);
		}

		Terminal terminal;

		if(type.equals("BASIC")){
			terminal = new Basic(id, getClient(clientKey));
		}
		else if(type.equals("FANCY")) {
			terminal = new Fancy(id, getClient(clientKey));
		}
		else {
			throw new TerminalTypeNotSupportedException();
		}

		if(state.equals("ON")) {
			terminal.setState(new Idle(terminal, true));
		}
		else if(state.equals("OFF")) {
			terminal.setState(new Off(terminal, true));
		}
		else if(state.equals("SILENCE")) {
			terminal.setState(new Silence(terminal, false));
			terminal.getState().setPreviousIdle(false);
		}


		SwitchState(state, terminal);

		// Registers the terminal in the client _terminals list
		getClient(clientKey).insertTerminal(terminal);
		
		//register the terminal in the network list
		_terminals.put(id, terminal);

		return terminal;
	}

	/**
	 * Gets the terminal with the given id.
	 * 
	 * @param id the terminal id
	 	*@throws UnknownTerminalKeyExceptionCore if the terminal does not exist	
	*@return terminal with the given id
	 */
	public Terminal getTerminal(String id) throws UnknownTerminalKeyExceptionCore {
		Terminal terminal = _terminals.get(id);
		if (terminal == null) {
			throw new UnknownTerminalKeyExceptionCore(id);
		}
		return terminal;
	}

/**
	 * Gets all the terminals that have not received nor sent communication;
	 * 
	 * @return Collection of terminals
	 */	public Collection<Terminal> getUnusedTerminals() {
		List<Terminal> terminal_list = new ArrayList<Terminal>();
		for(Map.Entry<String,Terminal> entry : _terminals.entrySet()){
			if(entry.getValue().NoCommunications()){
				terminal_list.add(entry.getValue());
			}
		}
		return terminal_list;
	}

	/**
	 * Gets all terminals
	 * 
	 * @return Collection of all terminals
	 */
	public Collection<Terminal> getAllTerminals() {
		return Collections.unmodifiableCollection(_terminals.values());
	}

	/**
	 * Adds a friend to the terminal; a terminal cannot be added as a friend of itself
	 * 
	 * @param terminal1 is the terminal thats going to add a friend
	 * @param terminal2 is the terminal thats going to be added as a friend
	 */
	public void makeFriends(Terminal terminal1, Terminal terminal2) {
		if (terminal1.equals(terminal2)) {
			return;
		}
		terminal1.AddFriend(terminal2);
	}

	/**
	 * Removes a terminal friend 
	 * 
	 * @param terminal1 is the terminal thats going to remove a friend
	 * @param terminal2 is the terminal thats going to be removed 

	 */
	public void deMakeFriends( Terminal terminal1, Terminal terminal2){
		terminal1.RemoveFriend(terminal2);
	}

/* *************************Communication Methods**************************** */
	/**
	 *Gets all the communications by all terminals;;
	 * @return Collection of all communications
	 */

	public Collection<Communication> getAllCommunications() {
		ArrayList<Communication> al = new ArrayList<>();
		for (Terminal t: _terminals.values()) {
			al.addAll(t.getAllTerminalCommunications());
		}
		return al;
	}

	/**
	 * Gets all the communications this client has sent;
	 * 
	 * @param clientKey is the client key
         * @throws UnknownClientKeyExceptionCore when client key does not exist
	 */
	public Collection<Communication> getAllCommunicationsFromClient(String key) throws UnknownClientKeyExceptionCore{
		ArrayList<Communication> al = new ArrayList<>();
		for(Terminal t: getClient(key).getAllTerminals()) {
			al.addAll(t.getAllTerminalCommunications());
		}
		return al;
	}

	/**
	 * Gets all the communications that were sent to this client.
	 * @param key The client key.
         * @throws UnknownClientKeyExceptionCore if the client key is unknown.
	 */
	public Collection<Communication> getAllCommunicationsToClient(String key) throws UnknownClientKeyExceptionCore {
		ArrayList<Communication> al = new ArrayList<>();
		Client receiver = getClient(key);

		for (Terminal t: _terminals.values()) {
			for (Communication c: t.getAllTerminalCommunications()) {
				if (c.getReceiver().getClient().equals(receiver)) {
					al.add(c);
				}
			}
		}
		return al;
	}
	/**
	 * Gets the Communication ID for the communication that's being registered;
	 * 
	 * @return the communication ID
	 */

	public int getCommunicationId() {
		_communicationNumber++;
		return _communicationNumber;
	}


/****************LOOKUPS ******** */
/**
	 * Gets terminals with positive balance;
	 * 
	 * @return Collection of terminals with positive balance
	 */

public Collection<Terminal> getPositiveTerminals(){
	List<Terminal> terminal_list = new ArrayList<Terminal>();
		for(Map.Entry<String,Terminal> entry : _terminals.entrySet()){
			if(entry.getValue().getAllPayments() > entry.getValue().getAllDebts()){
				terminal_list.add(entry.getValue());
			}
		}
		return terminal_list;
	}
	/**
	 * Gets clients with no debts;
	 * @return Collection of clients with no debts   
	 */
public Collection<Client> getNoDebtsClient(){
	List<Client> client_list = new ArrayList<Client>();
	for(Map.Entry<String,Client> entry : _clients.entrySet()){
		if(entry.getValue().Debts() == 0){
			client_list.add(entry.getValue());
		}
	}
	return client_list;
}
/**
	 *Gets clients with debts;
	 * 
	 * @return Collection of clients with debts
	 */
public Collection<Client> getYesDebtsClient(){
	List<Client> client_list = new ArrayList<Client>();
	for(Map.Entry<String,Client> entry : _clients.entrySet()){
		if(entry.getValue().Debts() != 0){
			client_list.add(entry.getValue());
		}
	}

	//to sort by debt and client id
	Collections.sort(client_list, new DebtComparator());
	return client_list;
}	

}