package prr.terminals;

import java.io.Serializable;

public class Busy extends State implements Serializable{

    public Busy(){
        super();
    }

    @Override 
    public boolean startCommunication(){
        return false;
    }

    @Override
    public boolean receiveCommunication(){
        //só de texto
        return true;
    }

    /***** **************** */
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
        return "BUSY";
    }
}
