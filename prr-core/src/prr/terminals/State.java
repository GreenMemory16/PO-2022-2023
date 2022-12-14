package prr.terminals;

import java.io.Serializable;
import prr.exceptions.AlreadyInStateException;

public abstract class State implements Serializable{

    private Terminal _terminal;
    private boolean _previousIdle = true;

    public State(Terminal terminal, boolean previousIdle) {
        _terminal = terminal;
        _previousIdle = previousIdle;
    }

    public Terminal getTerminal() {
        return _terminal;
    }
    public Boolean getPreviousIdle() {
        return _previousIdle;
    }

    public void setPreviousIdle(Boolean value) {
        _previousIdle = value;
    }

    public abstract void goToSilence() throws AlreadyInStateException;
    public abstract void turnOn() throws AlreadyInStateException;
    public abstract void turnOff() throws AlreadyInStateException;
    public abstract void endOfComm();
    public abstract void startOfComm();

    public abstract boolean statePermitsInteractiveCommunication();

    public abstract boolean statePermitsTextCommunication();
    
}