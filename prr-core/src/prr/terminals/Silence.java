package prr.terminals;

import java.io.Serializable;

//class Silence
public class Silence extends State implements Serializable{

    //every method depends on the state
    @Override 
    public boolean startCommunication(){
        return true;
    }

    @Override
    public boolean receiveCommunication(){
        //only text communications tho
        //to be implemented later on
        return true;
    }

   

    //changing state rules
    @Override
    public boolean switchToIdle() {
        return true;
    }

    @Override
    public boolean switchToOff() {
        return true;
    }

    @Override
    public boolean switchToSilence() {
        return false;
    }

    
    @Override
    public boolean switchToBusy() {
        return true;
    }

    @Override
    public String toString(){
        return "SILENCE";
    }
}
