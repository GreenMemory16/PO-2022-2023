package prr.terminals;

import java.io.Serializable;

//abstract class fthat implements methods for all states
public abstract class State implements Serializable{

    public abstract boolean startCommunication();
    public abstract boolean receiveCommunication();

    public abstract boolean switchToBusy();
    public abstract boolean switchToOff();
    public abstract boolean switchToSilence();
    public abstract boolean switchToIdle();


}