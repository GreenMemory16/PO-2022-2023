package prr.terminals;

import java.io.Serializable;

public class Off extends State implements Serializable{

    public Off(){
        super();
    }

    @Override 
    public boolean startCommunication(){
        return false;
    }

    @Override
    public boolean receiveCommunication(){
        return false;
    }

      /***** **************** */
//i must check this later!! FIX ME
    @Override
    public boolean switchToIdle() {
        return true;
    }

    @Override
    public boolean switchToOff() {
        return false;
    }

    //can
    @Override
    public boolean switchToSilence() {
        return true;
    }

    
    @Override
    public boolean switchToBusy() {
        return false;
    }
//************************* */

    @Override
    public String toString(){
        return "OFF";
    }
}
