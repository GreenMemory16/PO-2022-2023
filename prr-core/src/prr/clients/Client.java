package prr.clients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import prr.clients.ClientLevel;
import prr.exceptions.ClientNotificationsAlreadyDefinedException;
import prr.NetworkManager;
import prr.terminals.Terminal;
import prr.notifications.Notification;

public class Client implements Serializable {

    //each client has it's own terminals
    private Map<String, Terminal> _terminals = new TreeMap<>();

    private ArrayList<Notification> _notifications = new ArrayList<>();

    //client's identifiers
    private String _key;
    private String _name;
    private int _taxId;

    //client's characteristics
    private boolean _activeNotifications = true;
    private ClientLevel _level;

    //constructor
    public Client(String key, String name, int taxId) {
        _key = key;
        _name = name;
        _taxId = taxId;
        _level = new NormalLevel(this);
    }

    //getters
    public String getKey() { return _key; }
    public String getName() { return _name; }
    public int getTaxId() { return _taxId; }

    //setters
    public void setActiveNotifications(Boolean value) throws ClientNotificationsAlreadyDefinedException {
        if (value == _activeNotifications) {
            throw new ClientNotificationsAlreadyDefinedException();
        }
        _activeNotifications = value;
    }

    //add a terminal to the client's terminals
    public void insertTerminal(Terminal t) {
        _terminals.put(t.getId(), t);
    }

    //to change notifictions status
    public String getNotificationSwitch_String() {
        if (_activeNotifications) {
            return "YES";
        }
        return "NO";
    }

    public boolean canReceiveNotifications() {
        return _activeNotifications;
    }

    public String getClientLevel() {
        return _level.getLevel();
    }

    public Collection<Terminal> getAllTerminals() {
        return Collections.unmodifiableCollection(_terminals.values());
    }

    public void addNotification(Notification notification) {
        _notifications.add(notification);
    }

    public ArrayList<Notification> getNotifications() {
        return _notifications;
    }

    //client's toString
    @Override
    public String toString() {
        return "CLIENT|" + getKey() + "|" + getName() + "|" + getTaxId() + "|" + 
                _level.getLevel() + "|" + getNotificationSwitch_String() + "|" +
                _terminals.size() + "|" + "0" + "|" + "0";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Client) {
            Client client = (Client) o;
            return getKey().equals(client.getKey()) && getName().equals(client.getName()) && getTaxId() == client.getTaxId();
        }
        return false;
    }

}
