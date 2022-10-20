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


/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private Map<String, Client> _clients = new TreeMap<>();

	private Map<String, Terminal> _terminals = new TreeMap<String, Terminal>();

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
		 		UnknownTerminalKeyExceptionCore | InvalidTerminalKeyExceptionCore | DuplicateTerminalKeyExceptionCore e ) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new ImportFileException(filename);
		}
	}

	

	public Client registerClient(String key, String name, int taxId) throws DuplicateClientKeyExceptionCore {
		if (_clients.containsKey(key)) {
			throw new DuplicateClientKeyExceptionCore(key);
		}

		Client client = new Client(key, name, taxId);
		_clients.put(key, client);

		return client;
	}

	public Client getClient(String key) throws UnknownClientKeyExceptionCore {
		Client c = _clients.get(key);
		if (c == null) {
			throw new UnknownClientKeyExceptionCore(key);
		}
		return c;
	}

	public Collection<Client> getAllClients() {
		return Collections.unmodifiableCollection(_clients.values());
	}
/** ********************Terminal related methods*************************** */

//auxiliary function
public static boolean isNumeric(String str) { 
	try {  
		  Double.parseDouble(str);  
		  return true;
	} catch(NumberFormatException e){  
	  return false;  
	}  
  }


//registerTerminal
public Terminal registerTerminal(String id, String clientKey, String type, String state) 
			throws UnknownClientKeyExceptionCore, TerminalTypeNotSupportedException, 
			InvalidTerminalKeyExceptionCore, DuplicateTerminalKeyExceptionCore, 
			UnknownTerminalKeyExceptionCore {

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
			terminal = new Basic(id, clientKey);
		}
		else if(type.equals("FANCY")) {
			terminal = new Fancy(id, clientKey);
		}
		else {
			throw new TerminalTypeNotSupportedException();
		}

		switch (state) {
			case "ON" -> terminal.switchToIdleState();
			case "OFF" -> terminal.switchToOffState();
			case "SILENCE" -> terminal.switchToSilenceState();
			case "BUSY" -> terminal.switchToBusyState();
		}

		// Registers the terminal in the client _terminals list
		getClient(clientKey).insertTerminal(terminal);
	
		//register the terminal in the network list
		_terminals.put(id, terminal);

		return terminal;
	}
	//terminal getter
	public Terminal getTerminal(String id) throws UnknownTerminalKeyExceptionCore {
		Terminal terminal = _terminals.get(id);
		if (terminal == null) {
			throw new UnknownTerminalKeyExceptionCore(id);
		}
		return terminal;
	}

	//returns the list of terminals that have no communications
	public Collection<Terminal> getUnusedTerminals() {
		List<Terminal> terminal_list = new ArrayList<Terminal>();
		for(Map.Entry<String,Terminal> entry : _terminals.entrySet()){
			if(entry.getValue().NoCommunications()){
				terminal_list.add(entry.getValue());
			}
		}
		return terminal_list;
	}

	//terminal getter
	public Collection<Terminal> getAllTerminals() {
		return Collections.unmodifiableCollection(_terminals.values());
	}

	//makeFRiends calls the add friend from the terminal
	public void makeFriends(Terminal t1, Terminal t2) {
		t1.AddFriend(t2);
	}

}

