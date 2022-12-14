package prr.clients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import prr.exceptions.ClientNotificationsAlreadyDefinedException;
import prr.terminals.Terminal;
import prr.notifications.Notification;

public class Client implements Serializable {

    //each client has it's own terminals
    private Map<String, Terminal> _terminals = new TreeMap<>();

    //each client has notifications
    private ArrayList<Notification> _notifications = new ArrayList<>();

    //client's identifiers
    private String _key;
    private String _name;
    private int _taxId;

    //client's characteristics
    private boolean _activeNotifications = true;
    //client level
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
    public ClientLevel getLevel() { return _level; }

    public String getClientLevel() {
        return _level.levelToString();
    }

    public Collection<Terminal> getAllTerminals() {
        return Collections.unmodifiableCollection(_terminals.values());
    }

    public ArrayList<Notification> getNotifications() {
        return _notifications;
    }

    //payment getter
    public long Payments(){
        long payments = 0;
        for(Map.Entry<String,Terminal> entry : _terminals.entrySet()){
            payments += entry.getValue().getAllPayments();
        }
        return payments;
    }
    //debt getter
    public long Debts(){
        long debts = 0;
        for(Map.Entry<String,Terminal> entry : _terminals.entrySet()){
            debts += entry.getValue().getAllDebts();
        }
        return debts;
    }

    //balance getter
    public long getBalance(){
        return Payments() - Debts();
    }

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
    public String getNotificationSwitchToString() {
        if (_activeNotifications) {
            return "YES";
        }
        return "NO";
    }


    public void setLevel(ClientLevel level) { _level = level; }

    public void tryPromotingClient() {getLevel().promote();}

    public void tryDemotingClient() {getLevel().demote();}

    public boolean canReceiveNotifications() {
        return _activeNotifications;
    }


    public void addNotification(Notification notification) {
        if (!_notifications.contains(notification)) {
            _notifications.add(notification);
        }
    }


    public void removeNotifications() {
        _notifications.clear();
    }

    //client's toString
    @Override
    public String toString() {
        return "CLIENT|" + getKey() + "|" + getName() + "|" + getTaxId() + "|" + 
                _level.levelToString() + "|" + getNotificationSwitchToString() + "|" +
                _terminals.size() + "|" +this.Payments() + "|" + this.Debts();
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
