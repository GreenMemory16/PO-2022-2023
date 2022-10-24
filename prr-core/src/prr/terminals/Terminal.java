package prr.terminals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import prr.exceptions.InvalidTerminalKeyExceptionCore;

import prr.exceptions.AlreadySilentExceptionCore;
import prr.exceptions.AlreadyInStateException;
import prr.communication.Communication;

/**
 * Abstract terminal.
 */
public abstract class Terminal implements Serializable{ 


	/** Serial number for serialization. */
        private static final long serialVersionUID = 202208091753L;

        //define attributes
        private String id;

        private State state;

        private String clientKey;

        //map with all communications made by this terminal
        private Map<Integer, Communication> _communications;

        //amigos; usar um map: TReeMap in this case
        //a key será o id do terminal
        private Map<String,Terminal> friends ;

        //private lista de dividas e pagamentos
        //enunciado diz que o terminal deve ter contabilidade propria
        private List<Integer> payments;
        private List<Integer> debts;

        //define contructor(s)
        public Terminal (String id, String clientKey)throws InvalidTerminalKeyExceptionCore{
                if (id.length() != 6 || !(isNumeric(id))) {
	        	throw new InvalidTerminalKeyExceptionCore(id);
	        }     
        

                this.id = id;
                this.state = new Idle(this);
                this.clientKey = clientKey;


                this.friends = new TreeMap<String,Terminal>();

                this.payments = new ArrayList<Integer>();
                this.payments.add(0);
                this.debts = new ArrayList<Integer>();
                this.debts.add(0);

                this._communications = new TreeMap<Integer, Communication>();
        }

         //auxiliary function
	public static boolean isNumeric(String str) { 
		try {  
			Double.parseDouble(str);  
			return true;
		} catch(NumberFormatException e){  
		return false;  
		}
	}

        //getters: not setters tho
        public String getId(){
                return this.id;
        }

        /*communications methods*/
        public Communication getCommunication(int key){
                return this._communications.get(key);
        }
        public Map<Integer,Communication> getAllCommunications(){
                return this._communications;
        }

        //returns true if terminal is unused
        public boolean noCommunications(){
                return(this._communications.size() == 0);
        }
        /* state related function */
        public State getState(){
                return this.state;
        }

        public String getClientKey(){
                return clientKey;
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

        public void addFriend(Terminal terminal) {
              //should make an exception for this later on
              //in case tjhe terminal is already a friend
              //or if the terminal is the same as the one calling the function
                friends.put(terminal.getId(), terminal);
        }
        public void removeFriend(Terminal terminal) {
                //should make an exception for this later on
                //in case tjhe terminal is already a friend
                //or if the terminal is the same as the one calling the function
                  friends.remove(terminal.getId(), terminal);
          }
        
        //useful to check if the terminal is already a friend
        public boolean isFriend(String Id){
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
                return this.toStringType() + "|" + this.getId() + "|" + this.getClientKey() + "|" 
                +  this.getState() + 
                "|" + getAllPayments() + "|" + getAllDebts() + toStringFriends();
    }
    
        //to string of terminal type: BASIC OR FANCY
        abstract public String toStringType();

        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive communication) and
         *          it was the originator of this communication.
         **/

        public boolean canEndCurrentCommunication() {
                // FIXME add implementation code
                //only next entrega

                return false;
        }

        /**
         * Checks if this terminal can start a new communication.
         *
         * @return true if this terminal is neither off neither busy, false otherwise.
         **/


        public boolean canStartCommunication() {
                return true;
        }

        public boolean canReceiveCommunication(){
                return true;
        }

        public void setState(State newState) {
                this.state = newState;
        }


        public void turnOn() throws AlreadyInStateException{ state.turnOn(); }
        public void turnOff() throws AlreadyInStateException { state.turnOff(); }
        public void switchToSilence() throws AlreadyInStateException {state.goToSilence(); }
        public void endOfComm() {state.endOfComm(); }
        public void startOfComm() {state.startOfComm(); } 

        public boolean canDoTextCommunication() {
                return getState().statePermitsCommunication();
        }

        public abstract boolean canDoInteractiveCommunication();
        
}
