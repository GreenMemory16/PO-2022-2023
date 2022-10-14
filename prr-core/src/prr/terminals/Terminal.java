package prr.terminals;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.io.Serializable;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 lets do it after; lets see if this works yet tho
 */
 public /*abstract*/ class Terminal implements Serializable{ 
 /* FIXME maybe addd more interfaces */

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

        //define attributes
        private String id;

        private String clientkey;

        //private Estado _estado;

        //lista de amigos; mudar para um map maybe ou probably treemap
        //a key será o id do terminal
        private Map<String,Terminal> amigos;

        //private lista ou map de dividas e pagamentos
        //enunciado diz que o terminal deve ter contabilidade propria
        private List<int> payments;
        private List<int> debts;

        //define contructor(s)
        public Terminal(String id, String clientkey){
                //state = new Idle(); 
                //fazer estados;
                this.id = id;
                this.clientkey = clientkey;
                this.amigos = new TreeMap<String,Terminal>();
                this.payments = new ArrayList<int>();
                this.payments.add(0);
                this.debts = new ArrayList<int>();
                debts.add(0);
        }
        //getters: not setters tho
        public String getId(){
                return this.id;
        }
        /* state related functions */
        public State getState(){
                return this.state;
        }
        /* 
        public boolean Isbusy(){
                return estado.some function
        }
*/
        public String getClientKey(){
                return this.clientkey;
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
                        total+=list[i];
                }
                return total;
        }

        public void AddFriend(Terminal terminal) throws InvalidFriendException{
                if(this.equals(terminal)){
                        throw new InvalidFriendException() ;
                }
                _amigos.put(terminal.getId(), terminal);
        }
        
        public boolean IsFriend(String Id){
                Terminal terminal = _amigos.get(Id);
                if(terminal == null){
                        return false;
                }
        }
        
        abstract public boolean sameType(Terminal other){
        }
        
        @Override
        public String toString(){
                return "Terminal ID: " + this.getId() + " | Belongs to Client: " + this.getClientKey();
    }
        @Override
        abstract public boolean equals(){
        }
        // FIXME define methods

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

                return false;
        }

        /**
         * Checks if this terminal can start a new communication.
         *
         * @return true if this terminal is neither off neither busy, false otherwise.
         **/


        public boolean canStartCommunication() {
                // FIXME add implementation code
                return false;

        }
}
