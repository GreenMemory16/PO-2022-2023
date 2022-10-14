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
        private String _id;

        private String _clientkey;

        //private Estado _estado;

        //lista de amigos; mudar para um map maybe ou probably treemap
        //a key será o id do terminal
        private Map<String,Terminal> _amigos;

        //private lista ou map de dividas e pagamentos
        //enunciado diz que o terminal deve ter contabilidade propria
        private List<int> _payments;
        private List<int> _debts;

        //define contructor(s)
        public Terminal(String id, String clientkey){
                //_estado = new Idle(); 
                //fazer estados;
                _id = id;
                _clientkey = clientkey;
                _amigos = new TreeMap<String,Terminal>();
                _payments = new ArrayList<int>();
                _payments.add(0);
                _debts = new ArrayList<int>();
                debts.add(0);
        }
        //getters: not setters tho
        public String getId(){
                return _id;
        }
        public String getClientKey(){
                return _clientkey;
        }
        public int getAllPayments(){
                return getAllSomething(_payments);
        }
        public int getAllDebts(){
                return getAllSomething(_debts);
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
        
        @Override
        public String toString(){
                return "Terminal ID: " + getId() + " | Belongs to Client: " + getClientKey();
    }
        @Override
        abstract public boolean equals(){
                return false;
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
