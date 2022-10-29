package prr.terminals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import prr.clients.Client;
import prr.Network;
import prr.exceptions.AlreadySilentExceptionCore;
import prr.exceptions.SenderEqualsReceiverException;
import prr.exceptions.UnknownTerminalKeyExceptionCore;
import prr.exceptions.AlreadyInStateException;
import prr.communication.Communication;
import prr.communication.TextCommunication;
import prr.communication.VideoCommunication;
import prr.communication.VoiceCommunication;


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

        private Communication _onGoingComm;

        //amigos; usar um map: TReeMap in this case
        //a key será o id do terminal
        private Map<String,Terminal> friends ;

        //private lista de dividas e pagamentos
        //enunciado diz que o terminal deve ter contabilidade propria
        private List<Integer> payments;
        private List<Integer> debts;

        //define contructor(s)
        public Terminal(String id, Client client){
                this.state = new Idle(this);
                this.id = id;
                this.client = client;


                this.friends = new TreeMap<String,Terminal>();

                this.payments = new ArrayList<Integer>();
                this.payments.add(0);
                this.debts = new ArrayList<Integer>();
                this.debts.add(0);

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
                return(this._communications.size() == 0);
        }
        /* state related function */
        public State getState(){
                return this.state;
        }

        public Client getClient(){
                return client;
        }

        public int getAllPayments(){
                return getAllSomething(this.payments);
        }
        public int getAllDebts(){
                return getAllSomething(this.debts);
        }
        //for abstração sake
        public int getAllSomething(List list){
                int total = 0;
                for(int i = 0; i < list.size() ; i++){
                        total += (Integer) list.get(i);
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
                return terminal == null;           
        }

        //to enumerate all friends that terminal has
        private String toStringFriends(){
                Set<Map.Entry<String, Terminal>> entrySet = friends.entrySet();
                Map.Entry<Integer, String>[] entryArray = entrySet.toArray(new Map.Entry[entrySet.size()]);
                String returnString = "";
                
                for(int i = 0; i < entryArray.length ; i++){
                        returnString += "|" + entryArray[i].getKey();
                }
                return returnString;
                
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

        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive communication) and
         *          it was the originator of this communication.
         **/

        public boolean canEndCurrentCommunication() {
                // IDK HOW TO CHECK IF IT WAS THE ORIGINATOR OF THE COMM
                if (this.getState().toString().equals("BUSY")) {
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
                // DONT DELETE, IT CAME WITH THE CODE!
                return this.senderAvailableForCommunication();
        }

        public void setState(State newState) {
                this.state = newState;
        }


        public void turnOn() throws AlreadyInStateException{ state.turnOn(); }
        public void turnOff() throws AlreadyInStateException { state.turnOff(); }
        public void switchToSilence() throws AlreadyInStateException {state.goToSilence(); }
        public void endOfComm() {state.endOfComm(); }
        public void startOfComm() {state.startOfComm(); } 

        public boolean senderAvailableForCommunication() {
                return this.getState().statePermitsCommunication();
        }

        public boolean receiverAvailableForCommunication(Terminal receiver) {
                return receiver.getState().statePermitsCommunication();
        }

        public abstract boolean canSupportVideoCommunication();

        public void insertCommunication(Communication comm) {
                _communications.put(comm.getId(), comm);
        }

        public Collection<Communication> getAllTerminalCommunications() {
                return Collections.unmodifiableCollection(_communications.values());
        }

        public void makeTextCommunication(Network network, String id, String message) throws  UnknownTerminalKeyExceptionCore{
                Terminal receiver = network.getTerminal(id);

                if (senderAvailableForCommunication() && receiverAvailableForCommunication(receiver)) {
                        int commId = network.getCommunicationId();
                        Communication comm = new TextCommunication(commId, this, receiver, message);
                        comm.setStatus(false);
                        insertCommunication(comm);
                }
        }

        public void makeInteractiveCommunication(Network network, String id, String type) throws UnknownTerminalKeyExceptionCore{
                Terminal receiver = network.getTerminal(id);

                if (senderAvailableForCommunication()) {
                        Communication comm;
                        if(type.equals("VOICE")) {
                                comm = new VoiceCommunication(network.getCommunicationId(), this, receiver);
                                comm.setStatus(true);
                                startOfComm();
                                _onGoingComm = comm;
                                insertCommunication(comm);
                        }
                        else if (type.equals("VIDEO")) {
                                if (canSupportVideoCommunication()) {
                                        comm = new VideoCommunication(network.getCommunicationId(), this, receiver);
                                        comm.setStatus(true);
                                        startOfComm();
                                        _onGoingComm = comm;
                                        insertCommunication(comm);
                                }
                        }
                        
                }
        }
        
        public boolean equals(Object o) {
                if (o instanceof Terminal) {
                        Terminal t = (Terminal) o;
                        return (getId().equals(t.getId()));
                }
                return false;
        }
}
