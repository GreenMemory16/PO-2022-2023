package prr.terminals;

import java.io.Serializable;

public class Idle extends State implements Serializable{

    public Idle(){
        super();
    }

    @Override 
    public boolean startCommunication(){
        return true;
    }

    @Override
    public boolean receiveCommunication(){
        return true;
    }

      /***** **************** */
    //to change states

//i must check this later!! FIX ME
    //cant
    @Override
    public boolean switchToIdle() {
        return false;
    }

    @Override
    public boolean switchToOff() {
        return true;
    }

    //can
    @Override
    public boolean switchToSilence() {
        return true;
    }

    
    @Override
    public boolean switchToBusy() {
        return true;
    }
//************************* */

    @Override
    public String toString(){
        return "IDLE";
    }
}

