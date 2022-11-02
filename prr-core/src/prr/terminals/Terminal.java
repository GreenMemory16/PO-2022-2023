package prr.terminals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import prr.clients.Client;
import prr.Network;
import prr.exceptions.AlreadySilentExceptionCore;
import prr.exceptions.DestinationIsBusyException;
import prr.exceptions.DestinationIsOffException;
import prr.exceptions.DestinationIsSilentException;
import prr.exceptions.NoOnGoingCommunicationException;
import prr.exceptions.UnsupportedAtDestinationException;
import prr.exceptions.UnsupportedAtOriginException;
import prr.exceptions.NoOnGoingCommunicationException;
import prr.exceptions.InvalidCommunicationExceptionCore;
import prr.exceptions.InvalidFriendExceptionCore;
import prr.exceptions.SenderEqualsReceiverException;
import prr.exceptions.UnknownTerminalKeyExceptionCore;
import prr.exceptions.DestinationIsBusyException;
import prr.exceptions.DestinationIsOffException;
import prr.exceptions.DestinationIsSilentException;
import prr.exceptions.NoOnGoingCommunicationException;
import prr.exceptions.UnsupportedAtDestinationException;
import prr.exceptions.UnsupportedAtOriginException;
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
import prr.clients.NormalLevel;
import prr.clients.PlatinumLevel;
import prr.clients.GoldLevel;


/**
 * Abstract terminal.
 */
public abstract class Terminal implements Serializable{ 


	/** Serial number for serialization. */
        private static final long serialVersionUID = 202208091753L;

        //define attributes
        private String id;

        private State state;

        private Client client;

        //map with all communications made by this terminal
        private Map<Integer, Communication> _communications;

        private int recievedCommunications;

        private ArrayList<Communication> _commAttempts = new ArrayList<>();

        private Communication _onGoingComm;

        //amigos; usar um map: TReeMap in this case
        //a key será o id do terminal
        private Map<String,Terminal> friends ;

        //private lista de dividas e pagamentos
        //enunciado diz que o terminal deve ter contabilidade propria
        private List<Communication> payments;
        private List<Communication> debts;

        //define contructor(s)
        public Terminal(String id, Client client){
                this.state = new Idle(this, true);
                this.id = id;
                this.client = client;
                this.recievedCommunications = 0;


                this.friends = new TreeMap<String,Terminal>();

                this.payments = new ArrayList<Communication>();
                //this.payments.add(0);
                this.debts = new ArrayList<Communication>();
                //this.debts.add(0);

                this._communications = new TreeMap<Integer, Communication>();
        }

        //getters: not setters tho
        public String getId(){
                return this.id;
        }

        /*communications methods*/
        public Communication getCommunication(int key){
                return this._communications.get(key);
        }

        //returns true if terminal is unused
        public boolean NoCommunications(){
                return(this._communications.size() == 0 && this.recievedCommunications == 0);
        }
        /* state related function */
        public State getState(){
                return this.state;
        }

        public Client getClient(){
                return client;
        }

        public int getBalance(){
                int sum = Math.round(getAllPayments() - getAllDebts());
                return sum;
        }

        public int getAllPayments(){
                return (int) Math.round(getAllSomething(this.payments));
        }

        public int getAllDebts(){
                return (int) Math.round(getAllSomething(this.debts));
        }

        //for abstração sake
        public int getAllSomething(List list) {
                int total = 0;
                for(int i = 0; i < list.size() ; i++){
                        Communication com = (Communication) list.get(i);

                        total += com.getCost();
                }
                return total;
        }

        //functions related to Friends settings

        public void AddFriend(Terminal terminal) {
              //should make an exception for this later on
              //in case tjhe terminal is already a friend
              //or if the terminal is the same as the one calling the function
                friends.put(terminal.getId(), terminal);
        }
        public void RemoveFriend(Terminal terminal) {
                //should make an exception for this later on
                //in case tjhe terminal is already a friend
                //or if the terminal is the same as the one calling the function
                  friends.remove(terminal.getId(), terminal);
          }
        
        //useful to check if the terminal is already a friend
        public boolean IsFriend(String Id){
                Terminal terminal = friends.get(Id);
                return !(terminal == null);           
        }

        //to enumerate all friends that terminal has
        public String toStringFriends(){
                Set<Map.Entry<String, Terminal>> entrySet = friends.entrySet();
                Map.Entry<Integer, String>[] entryArray = entrySet.toArray(new Map.Entry[entrySet.size()]);
                String returnString = "|";
                
                for(int i = 0; i < entryArray.length ; i++){
                        returnString += entryArray[i].getKey() + ",";
                }
                return returnString.substring(0, returnString.length()-1);
                
        }

        //to String method
        @Override
        public String toString(){
                return this.toStringType() + "|" + this.getId() + "|" + this.getClient().getKey() + "|" 
                +  this.getState() + 
                "|" + getAllPayments() + "|" + getAllDebts() + toStringFriends();
    }
    
        //to string of terminal type: BASIC OR FANCY
        abstract public String toStringType();

        public void makeFriends(Network network, String id) throws UnknownTerminalKeyExceptionCore{
                network.makeFriends(this, network.getTerminal(id));
        }

        public void removeFriend(Network network, String id) throws UnknownTerminalKeyExceptionCore {
                network.deMakeFriends(this, network.getTerminal(id));
        }

        public void recievedCommunications(){
                this.recievedCommunications++;
        }

        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive communication) and
         *          it was the originator of this communication.
         **/

        public boolean canEndCurrentCommunication() {
                // IDK HOW TO CHECK IF IT WAS THE ORIGINATOR OF THE COMM
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


        public void turnOn() throws AlreadyInStateException{ 
                if (_commAttempts.size() != 0) {
                        ListIterator<Communication> iter = _commAttempts.listIterator();
                        while(iter.hasNext()) {
                                Communication c = iter.next();
                                if (c.getSender().getClient().canReceiveNotifications()) {
                                        if (getState().toString().equals("OFF")) {
                                                c.getSender().getClient().addNotification(new NotificationOffToIdle(c.getReceiver()));
                                                iter.remove();
                                        }
                                        else if (getState().toString().equals("SILENCE") && !c.getType().equals("TEXT")) {
                                                c.getSender().getClient().addNotification(new NotificationSilentToIdle(c.getReceiver()));
                                                iter.remove();
                                                
                                        }
                                }
                        }
                }
                state.turnOn(); 
        }
        public void turnOff() throws AlreadyInStateException { 
                state.turnOff(); 
        }
        public void switchToSilence() throws AlreadyInStateException {
                if (_commAttempts.size() != 0) {
                        ListIterator<Communication> iter = _commAttempts.listIterator();
                        while(iter.hasNext()) {
                                Communication c = iter.next();
                                if(c.getSender().getClient().canReceiveNotifications()) {
                                        if(getState().toString().equals("OFF")) {
                                                c.getSender().getClient().addNotification(new NotificationOffToSilent(c.getReceiver()));
                                                iter.remove();
                                        }
                                }
                        }
                }
                state.goToSilence(); 
        }
        public void endOfComm() {
                if (_commAttempts.size() != 0) {
                        ListIterator<Communication> iter = _commAttempts.listIterator();
                        while(iter.hasNext()) {
                                Communication c = iter.next();
                                if (c.getSender().getClient().canReceiveNotifications()) {
                                        if (getState().toString().equals("BUSY") && !c.getType().equals("TEXT")) {
                                                c.getSender().getClient().addNotification(new NotificationBusyToIdle(c.getReceiver()));
                                                iter.remove();
                                        }
                                }
                        }
                }
                state.endOfComm(); 
        }
        public void startOfComm() {
                state.startOfComm(); 
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

        public void makeTextCommunication(Network network, String id, String message) throws  UnknownTerminalKeyExceptionCore, 
                                                DestinationIsOffException {
                Terminal receiver = network.getTerminal(id);

                if (receiver.getState().toString().equals("OFF")) {
                        receiver.addCommunicationAttempt(new VoiceCommunication(network.getCommunicationId(), this, receiver));
                        throw new DestinationIsOffException(id);
                }

                if (AvailableForTextCommunication(this) && AvailableForTextCommunication(receiver)) {
                        int commId = network.getCommunicationId();
                        Communication comm = new TextCommunication(commId, this, receiver, message);
                        comm.setCost(comm.calculateCost());
                        comm.setStatus(false);
                        insertCommunication(comm);
                        //here maybe receiver
                        this.addDebt(comm);
                        comm.getReceiver().recievedCommunications();
                        Client client = comm.getSender().getClient();
                        this.changeState(client, this);
                }
        }

        public void changeState(Client client, Terminal terminal){
                goldToNormal(client);
                goldToPlatinium(client, terminal);
                platiniumToGold(client, terminal);
                platiniumToNormal(client);
        }

        public void goldToNormal(Client client){
                if(client.Saldo() < 0 && client.getLevel().getLevel().equals("GOLD")){
                        client.setLevel(new NormalLevel(client));
                }
        }

        public void goldToPlatinium(Client client, Terminal terminal){
                //it might there be a problem if the 5 comms are old and it passed again
                //enunciado não diz se têm de ser as mais recentes ou não RIP
                int commCounter = 0;
                for(Map.Entry<Integer, Communication> entry : terminal._communications.entrySet()){
                        if(entry.getValue().getType().equals("VIDEO")){
                                commCounter ++;
                        }
                        else{
                                commCounter = 0;
                        }
                }
                if(client.Saldo() > 0 && client.getLevel().getLevel().equals("GOLD") && commCounter == 5){
                        client.setLevel(new PlatinumLevel(client));
                }
        }
        public void platiniumToGold(Client client, Terminal terminal){
                int commCounter = 0;
                for(Map.Entry<Integer, Communication> entry : terminal._communications.entrySet()){
                        if(entry.getValue().getType().equals("TEXT")){
                                commCounter ++;
                        }
                        else{
                                commCounter = 0;
                        }
                }
                if(client.Saldo() > 0 && client.getLevel().getLevel().equals("PLATINIUM") && commCounter == 2){
                        client.setLevel(new GoldLevel(client));
                }
        }

        public void platiniumToNormal(Client client){
                if(client.Saldo() < 0 && client.getLevel().getLevel().equals("PLATINIUM")){
                        client.setLevel(new NormalLevel(client));
                }
        }


       

        public void makeInteractiveCommunication(Network network, String id, String type) throws UnknownTerminalKeyExceptionCore,
                                                 DestinationIsOffException, DestinationIsBusyException, DestinationIsSilentException,
                                                 UnsupportedAtDestinationException, UnsupportedAtOriginException, SenderEqualsReceiverException {
                Terminal receiver = network.getTerminal(id);

                if (receiver.getState().toString().equals("OFF")) {
                        receiver.addCommunicationAttempt(new VoiceCommunication(network.getCommunicationId(), this, receiver));
                        throw new DestinationIsOffException(id);
                }
                if (receiver.getState().toString().equals("BUSY")) {
                        receiver.addCommunicationAttempt(new VoiceCommunication(network.getCommunicationId(), this, receiver));
                        throw new DestinationIsBusyException(id);
                }
                if (receiver.getState().toString().equals("SILENCE")) {
                        receiver.addCommunicationAttempt(new VoiceCommunication(network.getCommunicationId(), this, receiver));
                        throw new DestinationIsSilentException(id);
                }
                if (this.equals(receiver)) {
                        throw new SenderEqualsReceiverException(id);
                }

                if (AvailableForInteractiveCommunication(this)) {
                        Communication comm;
                        if(type.equals("VOICE")) {
                                comm = new VoiceCommunication(network.getCommunicationId(), this, receiver);
                                comm.setStatus(true);
                                this.startOfComm();
                                receiver.startOfComm();
                                _onGoingComm = comm;
                                insertCommunication(comm);
                        }
                        else if (type.equals("VIDEO")) {

                                if (!receiver.canSupportVideoCommunication()) {
                                        throw new UnsupportedAtDestinationException(id, "VIDEO");
                                }
                                if (!this.canSupportVideoCommunication()) {
                                        throw new UnsupportedAtOriginException(getId(), "VIDEO");
                                }

                                comm = new VideoCommunication(network.getCommunicationId(), this, receiver);
                                comm.setStatus(true);
                                this.startOfComm();
                                receiver.startOfComm();
                                _onGoingComm = comm;
                                insertCommunication(comm);   
                        }
                }
        }

        public int endInteractiveCommunication(Network network, int duration) {

                //IMPORTANTE! NAO SEI COMO ALTERAR A DURACAO!
                this.endOfComm();
                _onGoingComm.getReceiver().endOfComm();
                InteractiveCommunication communication = (InteractiveCommunication) _onGoingComm;
                _onGoingComm = null;
                communication.setDuration(duration);
                communication.setCost(communication.calculateCost());
                communication.setStatus(false);
                _communications.replace(communication.getId(), communication);
                communication.setCost(communication.calculateCost());
                addDebt(communication);
                communication.getReceiver().recievedCommunications();
                Client client = communication.getSender().getClient();
                changeState(client,this);

                return (int) Math.round(communication.calculateCost());
        }

        public void addCommunicationAttempt(Communication communication) {
                _commAttempts.add(communication);
        }
        
        public boolean equals(Object o) {
                if (o != null && o instanceof Terminal) {
                        Terminal t = (Terminal) o;
                        return (getId().equals(t.getId()));
                }
                return false;
        }

/************************** Payment methods ****************************** */
        public void performPayment(int commId) throws InvalidCommunicationExceptionCore{
                Communication c = this.getCommunication(commId);
                if (c == null || c.calculateCost() == 0  || !this.debts.contains(c)) {
                        throw new InvalidCommunicationExceptionCore();
                }
                if(c.getStatusToString().equals("ONGOING") || !c.getSender().equals(this)) {
                        throw new InvalidCommunicationExceptionCore();
                }
                
                this.addPayment(c);
                //changing from Normal to Gold
                normalToGold(this.getClient());
                
        }

        public void normalToGold(Client client){
                if(client.Saldo() > 500 && client.getLevel().getLevel().equals("NORMAL")){
                        client.setLevel(new GoldLevel(client));
                }
        }

        public void addDebt(Communication com){
                this.debts.add(com);
        }

        public void addPayment(Communication com){
                this.payments.add(com);
                this.debts.remove(com);
        }
        

}