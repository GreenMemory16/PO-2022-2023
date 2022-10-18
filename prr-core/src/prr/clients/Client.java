package prr.clients;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import prr.clients.ClientLevel;
import prr.NetworkManager;
import prr.terminals.Terminal;

public class Client implements Serializable {

    private Map<String, Terminal> _terminals = new TreeMap<>();

    private String _key;
    private String _name;
    private int _taxId;
    private boolean _activeNotifications = true;
    private ClientLevel _level;

    public Client(String key, String name, int taxId) {
        _key = key;
        _name = name;
        _taxId = taxId;
        _level = new NormalLevel(this);
    }

    public String getKey() { return _key; }
    public String getName() { return _name; }
    public int getTaxId() { return _taxId; }

    public static final Comparator<Client> KEY_COMPARATOR = new Comparator<>() {
        @Override
        public int compare(Client c1, Client c2) {
          return c1.getKey().compareToIgnoreCase(c2.getKey());
        }    
    };

    public void insertTerminal(Terminal t) {
        _terminals.put(t.getId(), t);
    }

    public String getNotificationSwitch_String() {
        if (_activeNotifications) {
            return "YES";
        }
        return "NO";
    }

    @Override
    public String toString() {
        return "CLIENT|" + getKey() + "|" + getName() + "|" + getTaxId() + "|" + 
                _level.getLevel() + "|" + getNotificationSwitch_String() + "|" +
                _terminals.size() + "|" + "0" + "|" + "0";
    }

}
