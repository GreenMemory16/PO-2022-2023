package prr.terminals;

public abstract class State{

    public abstract boolean startCommunication();
    public abstract boolean receiveCommunication();

    public abstract boolean switchToBusy();
    public abstract boolean switchToOff();
    public abstract boolean switchToSilence();
    public abstract boolean switchToIdle();


}