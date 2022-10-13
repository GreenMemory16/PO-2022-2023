package prr.terminals;

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

        //private Terminal _amigos[];

        //define contructor(s)
        public Terminal(String id, String clientkey){
                _id = id;
                _clientkey = clientkey;
        }
        //getters: not setters tho
        public String getId(){
                return _id;
        }
        public String getClientKey(){
                return _clientkey;
        }


        @Override
        public String toString() {
                return "Terminal ID: " + getId() + " | Belongs to Client: " + getClientKey();
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
