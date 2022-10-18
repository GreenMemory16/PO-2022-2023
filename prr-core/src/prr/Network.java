package prr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import prr.clients.Client;
import prr.exceptions.DuplicateClientKeyExceptionCore;
import prr.exceptions.DuplicateTerminalKeyExceptionCore;
import prr.exceptions.UnknownClientKeyExceptionCore;
import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.InvalidTerminalKeyExceptionCore;
import prr.exceptions.UnknownTerminalKeyExceptionCore;
import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private boolean _changed = false; 

	private Map<String, Client> _clients = new HashMap<>();

	private Map<String, Terminal> _terminals = new TreeMap<String, Terminal>();


        // FIXME define attributes
        // FIXME define contructor(s)
        // FIXME define methods

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
         * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException if there is an IO erro while processing the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\\|");

				try {
					registerEntry(fields);
				} catch (DuplicateClientKeyExceptionCore | UnknownClientKeyExceptionCore e) {
					e.printStackTrace();
				}
			} 
		} catch (IOException e1) {
			throw new UnrecognizedEntryException(filename);
		} 
    	
	}

	public void registerEntry(String... fields) throws DuplicateClientKeyExceptionCore, 
		UnknownClientKeyExceptionCore {
		int taxId = Integer.parseInt(fields[3]);

		switch(fields[0]) {
			case "CLIENT" -> registerClient(fields[1], fields[2], taxId);
		}

	}

	public void setChanged(boolean v) {
		_changed = v;
	}

	public void changed() {
		_changed = true;
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
/** *********************************************** */
	public static boolean isNumeric(String str) { 
		try {  
	  		Double.parseDouble(str);  
	  		return true;
		} catch(NumberFormatException e){  
	  	return false;  
		}  
	  }

	public Terminal registerTerminal(String id, String clientkey, String type) throws InvalidTerminalKeyExceptionCore,
	DuplicateTerminalKeyExceptionCore ,UnknownClientKeyExceptionCore {
		if(id.length() != 6 || !(isNumeric(id))){
			throw new InvalidTerminalKeyExceptionCore(id);
		}

		if(_terminals.containsKey(id)){
			throw new DuplicateTerminalKeyExceptionCore(id);
		}

		if(!(_clients.containsKey(clientkey))){
			throw new UnknownClientKeyExceptionCore(clientkey);
		}
		/*if(type.equals("BASIC")){
			Terminal terminal = new Basic(id, clientkey);
		}
		else{
			Terminal terminal = new Fancy(id, clientkey);
		}*/
		Terminal terminal = new Basic(id, clientkey);
	//FIX ME, WHYYY CANT THIS WORK WTH?
		
		_terminals.put(id, terminal);
		//this puts in the tree table the terminal with id and the terminal itself

		return terminal;
	}
	public Terminal getTerminal(String id) throws UnknownTerminalKeyExceptionCore {
		Terminal terminal = _terminals.get(id);
		if (terminal == null) {
			throw new UnknownTerminalKeyExceptionCore(id);
		}

		return terminal;
	}

	public Collection<Terminal> getUnusedTerminals() {
		List<Terminal> terminal_list = new ArrayList<Terminal>();
		for(Map.Entry<String,Terminal> entry : _terminals.entrySet()){
			if(entry.getValue().NoCommunications()){
				terminal_list.add(entry.getValue());
			}
		}
		return terminal_list;
		//FIX ME put exception here!!
	}

	public Collection<Terminal> getAllTerminals() {
		return Collections.unmodifiableCollection(_terminals.values());
	}


}

