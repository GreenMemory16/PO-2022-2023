package prr.terminals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

import prr.clients.Client;
import prr.Network;
import prr.exceptions.CantStartCommunicationException;
import prr.exceptions.DestinationIsBusyException;
import prr.exceptions.DestinationIsOffException;
import prr.exceptions.DestinationIsSilentException;
import prr.exceptions.NoOnGoingCommunicationException;
import prr.exceptions.UnsupportedAtDestinationException;
import prr.exceptions.UnsupportedAtOriginException;
import prr.exceptions.InvalidCommunicationExceptionCore;
import prr.exceptions.SenderEqualsReceiverException;
import prr.exceptions.UnknownTerminalKeyExceptionCore;
import prr.exceptions.AlreadyInStateException;
import prr.communication.Communication;
import prr.communication.InteractiveCommunication;
import prr.communication.TextCommunication;
import prr.communication.VideoCommunication;
import prr.communication.VoiceCommunication;
import prr.notifications.Notification;
import prr.notifications.NotificationOffToIdle;
import prr.notifications.NotificationOffToSilent;
import prr.notifications.NotificationBusyToIdle;
import prr.notifications.NotificationSilentToIdle;

/**
 * Abstract terminal.
 */
public abstract class Terminal implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    // define attributes
    private String id;

    private State state;

    private Client client;

    // map with all communications made by this terminal
    private Map<Integer, Communication> _communications;

    private int recievedCommunications;

    private ArrayList<Communication> _commAttempts = new ArrayList<>();

    private Communication _onGoingComm;

    // amigos; usar um map: TreeMap in this case
    // a key será o id do terminal
    private Map<String, Terminal> friends;

    // private lista de dividas e pagamentos
    // enunciado diz que o terminal deve ter contabilidade propria
    private List<Communication> payments;
    private List<Communication> debts;

    // define contructor(s)
    public Terminal(String id, Client client) {
        this.state = new Idle(this, true);
        this.id = id;
        this.client = client;
        this.recievedCommunications = 0;

        this.friends = new TreeMap<String, Terminal>();

        this.payments = new ArrayList<Communication>();
        this.debts = new ArrayList<Communication>();

        this._communications = new TreeMap<Integer, Communication>();
    }

    public String getId() {
        return this.id;
    }

    public Communication getCommunication(int key) {
        return this._communications.get(key);
    }

    public boolean NoCommunications() {
        return (this._communications.size() == 0 && this.recievedCommunications == 0);
    }

    public State getState() {
        return this.state;
    }

    public Client getClient() {
        return client;
    }

    public int getBalance() {
        int sum = Math.round(getAllPayments() - getAllDebts());
        return sum;
    }

    public int getAllPayments() {
        return (int) Math.round(getAllOf(this.payments));
    }

    public int getAllDebts() {
        return (int) Math.round(getAllOf(this.debts));
    }

    public int getAllOf(List<Communication> list) {
        int total = 0;
        for (int i = 0; i < list.size(); i++) {
            Communication com = list.get(i);

            total += com.getCost();
        }
        return total;
    }

    public Client getClientSender(Communication comm) {
        return comm.getSender().getClient();
    }

    public Client getClientReceiver(Communication comm) {
        return comm.getReceiver().getClient();
    }

    public boolean hasCommunicationAttempts() {
        return _commAttempts.size() > 0;
    }

    public void AddFriend(Terminal terminal) {
        friends.put(terminal.getId(), terminal);
    }

    public void RemoveFriend(Terminal terminal) {
        friends.remove(terminal.getId(), terminal);
    }

    public boolean IsFriend(String Id) {
        Terminal terminal = friends.get(Id);
        return !(terminal == null);
    }

    public String toStringFriends() {
        String friendsString = "";

        if (friends.size() > 0) {
            friendsString += "|" + String.join(",", friends.keySet());
        }

        return friendsString;
    }

    public void makeFriends(Network network, String id) throws UnknownTerminalKeyExceptionCore {
        network.makeFriends(this, network.getTerminal(id));
    }

    public void removeFriend(Network network, String id) throws UnknownTerminalKeyExceptionCore {
        network.removeFriends(this, network.getTerminal(id));
    }

    public void addRecievedCommunications() {
        this.recievedCommunications++;
    }

    /**
     * Checks if this terminal can end the current interactive communication.
     *
     * @return true if this terminal is busy (i.e., it has an active interactive
     *         communication) and
     *         it was the originator of this communication.
     **/

    public boolean canEndCurrentCommunication() {
        if (this.getState().toString().equals("BUSY") && _onGoingComm != null) {
            return true;
        }
        return false;
    }

    /**
     * Checks if this terminal can start a new communication.
     *
     * @return true if this terminal is neither off neither busy, false otherwise.
     **/

    public boolean canStartCommunication() {
        return this.AvailableForInteractiveCommunication(this);
    }

    public void setState(State newState) {
        this.state = newState;
    }

    public void sendNotification(Client client, Communication comm, Notification notif, String notifType) {
        if (client.canReceiveNotifications()) {
            if (getState().toString().equals(notifType)) {
                getClientSender(comm).addNotification(notif);
            }
        }
    }

    public void turnOn() throws AlreadyInStateException {
        toIdleNotificationSending();
        state.turnOn();
    }

    public void turnOff() throws AlreadyInStateException {
        state.turnOff();
    }

    public void switchToSilence() throws AlreadyInStateException {
        toSilenceNotificationSending();
        state.goToSilence();
    }

    public void endOfComm() {
        NotBusyNotificationSending();
        state.endOfComm();
    }

    public void startOfComm() {
        state.startOfComm();
    }

    public void toIdleNotificationSending() {
        if (hasCommunicationAttempts()) {
            ListIterator<Communication> iter = _commAttempts.listIterator();
            while (iter.hasNext()) {
                Communication c = iter.next();
                if (getClientSender(c).canReceiveNotifications()) {
                    if (getState().toString().equals("OFF")) {
                        getClientSender(c).addNotification(new NotificationOffToIdle(c.getReceiver()));
                        iter.remove();
                    } else if (getState().toString().equals("SILENCE") && !c.getType().equals("TEXT")) {
                        getClientSender(c).addNotification(new NotificationSilentToIdle(c.getReceiver()));
                        iter.remove();

                    }
                }
            }
        }
    }

    public void toSilenceNotificationSending() {
        if (hasCommunicationAttempts()) {
            ListIterator<Communication> iter = _commAttempts.listIterator();
            while (iter.hasNext()) {
                Communication c = iter.next();
                if (getClientSender(c).canReceiveNotifications()) {
                    if (getState().toString().equals("OFF")) {
                        getClientSender(c).addNotification(new NotificationOffToSilent(c.getReceiver()));
                        iter.remove();
                    }
                }
            }
        }
    }

    public void NotBusyNotificationSending() {
        if (hasCommunicationAttempts()) {
            ListIterator<Communication> iter = _commAttempts.listIterator();
            while (iter.hasNext()) {
                Communication c = iter.next();
                if (getClientSender(c).canReceiveNotifications()) {
                    if (getState().toString().equals("BUSY") && !c.getType().equals("TEXT")) {
                        getClientSender(c).addNotification(new NotificationBusyToIdle(c.getReceiver()));
                        iter.remove();
                    }
                }
            }
        }
    }

    public boolean AvailableForTextCommunication(Terminal terminal) {
        return terminal.getState().statePermitsTextCommunication();
    }

    public boolean AvailableForInteractiveCommunication(Terminal terminal) {
        return terminal.getState().statePermitsInteractiveCommunication();
    }

    public abstract boolean canSupportVideoCommunication();

    public void insertCommunication(Communication comm) {
        _communications.put(comm.getId(), comm);
    }

    public Collection<Communication> getAllTerminalCommunications() {
        return Collections.unmodifiableCollection(_communications.values());
    }

    public String getOnGoingCommunication() throws NoOnGoingCommunicationException {
        if (_onGoingComm == null) {
            throw new NoOnGoingCommunicationException();
        }
        return _onGoingComm.toString();
    }

    public void makeTextCommunication(Network network, String id, String message)
            throws UnknownTerminalKeyExceptionCore, DestinationIsOffException, CantStartCommunicationException {
        Terminal receiver = network.getTerminal(id);

        if (receiver.getState().toString().equals("OFF")) {
            receiver.addCommunicationAttempt(new VoiceCommunication(network.getCommunicationId(), this, receiver));
            throw new DestinationIsOffException(id);
        }
        else if (AvailableForTextCommunication(this) && AvailableForTextCommunication(receiver)) {
            int commId = network.getCommunicationId();
            TextCommunication comm = new TextCommunication(commId, this, receiver, message);
            setTextCommunicationStandards(comm);

            getClientSender(comm).getLevel().incrementConsecutiveTextComms();
            getClientSender(comm).getLevel().resetConsecutiveVideoComms();

            updateClientLevel(comm.getSender().getClient());
        }
        else {
            throw new CantStartCommunicationException();
        }
    }

    public void setTextCommunicationStandards(TextCommunication comm) {
        comm.setCost(getClientSender(comm).getLevel().calculateTextCost(comm));
        comm.setStatus(false);
        insertCommunication(comm);
        this.addDebt(comm);
        comm.getReceiver().addRecievedCommunications();
    }

    public void makeInteractiveCommunication(Network network, String id, String type)
            throws UnknownTerminalKeyExceptionCore,
            DestinationIsOffException, DestinationIsBusyException, DestinationIsSilentException,
            UnsupportedAtDestinationException, UnsupportedAtOriginException, SenderEqualsReceiverException,
            CantStartCommunicationException {
        Terminal receiver = network.getTerminal(id);

        if (receiver.getState().toString().equals("OFF")) {
            receiver.addCommunicationAttempt(new VoiceCommunication(network.getCommunicationId(), this, receiver));
            throw new DestinationIsOffException(id);
        } else if (receiver.getState().toString().equals("BUSY")) {
            receiver.addCommunicationAttempt(new VoiceCommunication(network.getCommunicationId(), this, receiver));
            throw new DestinationIsBusyException(id);
        } else if (receiver.getState().toString().equals("SILENCE")) {
            receiver.addCommunicationAttempt(new VoiceCommunication(network.getCommunicationId(), this, receiver));
            throw new DestinationIsSilentException(id);
        } else if (this.equals(receiver)) {
            throw new SenderEqualsReceiverException(id);
        }
        else if (AvailableForInteractiveCommunication(this)) {
            Communication comm;
            if (type.equals("VOICE")) {
                comm = new VoiceCommunication(network.getCommunicationId(), this, receiver);
                setInteractiveCommunicationStandards(comm, receiver);

                getClientSender(comm).getLevel().resetConsecutiveTextComms();
                getClientSender(comm).getLevel().resetConsecutiveVideoComms();
            } else if (type.equals("VIDEO")) {

                if (!receiver.canSupportVideoCommunication()) {
                    throw new UnsupportedAtDestinationException(id, "VIDEO");
                }
                if (!this.canSupportVideoCommunication()) {
                    throw new UnsupportedAtOriginException(getId(), "VIDEO");
                }

                comm = new VideoCommunication(network.getCommunicationId(), this, receiver);
                setInteractiveCommunicationStandards(comm, receiver);

                getClientSender(comm).getLevel().resetConsecutiveTextComms();
                getClientSender(comm).getLevel().incrementConsecutiveVideoComms();
            }
        }
        else {
            throw new CantStartCommunicationException();
        }
    }

    public void setInteractiveCommunicationStandards(Communication comm, Terminal receiver) {
        comm.setStatus(true);
        this.startOfComm();
        receiver.startOfComm();
        _onGoingComm = comm;
        insertCommunication(comm);
    }

    public void updateClientLevel(Client client) {
        client.tryPromotingClient();
        client.tryDemotingClient();
    }

    public int endInteractiveCommunication(Network network, int duration) {
        this.endOfComm();
        _onGoingComm.getReceiver().endOfComm();
        InteractiveCommunication communication = (InteractiveCommunication) _onGoingComm;
        _onGoingComm = null;

        setCommunicationEndUpdates(communication, duration);

        _communications.replace(communication.getId(), communication);
        addDebt(communication);
        communication.getReceiver().addRecievedCommunications();

        return (int) Math.round(communication.getCost());
    }

    public void setCommunicationEndUpdates(InteractiveCommunication comm, int duration) {
        comm.setDuration(duration);
        if (comm.getType().equals("VOICE")) {
            comm.setCost(getClientSender(comm).getLevel().calculateVoiceCost(comm));
        } else if (comm.getType().equals("VIDEO")) {
            comm.setCost(getClientSender(comm).getLevel().calculateVideoCost(comm));
        }
        comm.setStatus(false);
    }

    public void addCommunicationAttempt(Communication communication) {
        _commAttempts.add(communication);
    }

    /************************** Payment methods ****************************** */

    public void performPayment(int commId) throws InvalidCommunicationExceptionCore {
        Communication comm = this.getCommunication(commId);
        if (comm == null || comm.getCost() == 0 || !this.debts.contains(comm)) {
            throw new InvalidCommunicationExceptionCore();
        }
        if (comm.getStatusToString().equals("ONGOING") || !comm.getSender().equals(this)) {
            throw new InvalidCommunicationExceptionCore();
        }

        this.addPayment(comm);

        updateClientLevel(getClientSender(comm));
    }

    public void addDebt(Communication com) {
        this.debts.add(com);
    }

    public void addPayment(Communication com) {
        this.payments.add(com);
        this.debts.remove(com);
    }

    public boolean equals(Object o) {
        if (o != null && o instanceof Terminal) {
            Terminal t = (Terminal) o;
            return (getId().equals(t.getId()));
        }
        return false;
    }

    abstract public String toStringType();

    public String toString() {
        return this.toStringType() + "|" + this.getId() + "|" + this.getClient().getKey() + "|"
                + this.getState() +
                "|" + getAllPayments() + "|" + getAllDebts() + toStringFriends();
    }
}