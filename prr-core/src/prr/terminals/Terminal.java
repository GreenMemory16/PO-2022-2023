package prr.terminals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


import prr.communication.Communication;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable{ 


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
        public Terminal(String id, String clientKey){
                this.state = new Idle();
                this.id = id;
                this.clientKey = clientKey;


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
        public Map<Integer,Communication> getAllCommunications(){
                return this._communications;
        }

        //returns true if terminal is unused
        public boolean NoCommunications(){
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
        public void AddFriend(Terminal terminal) /*throws InvalidFriendException*/ {
                /*
                if(this.equals(terminal)){
                        throw new InvalidFriendException() ;
                }*/
                friends.put(terminal.getId(), terminal);
        }
        
        public boolean IsFriend(String Id){
                Terminal terminal = friends.get(Id);
                return terminal == null;
                        

        }

        //State functions;
        //cada state é que sabe se pode mudar para o newstate
        //se o state(class) retornar falso então o state(var) fica igual
        public void switchToIdleState(){
                if(state.switchToIdle()){
                        this.state = new Idle();
                }
        }
        public void switchToSilenceState(){
                if(state.switchToSilence()){
                        this.state = new Silence();
                }
        }
        public void switchToOffState(){
                if(state.switchToOff()){
                        this.state = new Off();
                }

        }
        public void switchToBusyState(){
                if(state.switchToBusy()){
                        this.state = new Busy();                
                }
        }


        //to enumerate all friends that terminal has
        private String toStringFriends(){
                Set<Map.Entry<String, Terminal>> entrySet = friends.entrySet();
                 // Convert entrySet to Array using toArray method
                Map.Entry<Integer, String>[] entryArray = entrySet.toArray(new Map.Entry[entrySet.size()]);
                String returnString = "";
                
                for(int i = 0; i < entryArray.length ; i++){
                        returnString += "|" + entryArray[i].getKey();
                }
                return returnString;
                
        }
        
        @Override
        public String toString(){
                return this.toStringType() + "|" + this.getId() + "|" + this.getClientKey() + "|" 
                +  this.getState() + 
                "|" + getAllPayments() + "|" + getAllDebts() + toStringFriends();
    }
    
        //tostring do tipo de terminal: BASIC OR FANCY
        abstract public String toStringType();

        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive communication) and
         *          it was the originator of this communication.
         **/

         //ist gonna be abstract bc it depoends on the terminal type
         //or maybe do another function for that ~
         //this is to check if the terminal is ready or not

        /*abstract*/public boolean canEndCurrentCommunication() {
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
                return state.startCommunication();
        }

        public boolean canReceiveCommunication(){
                return state.receiveCommunication();
        }

        
}
