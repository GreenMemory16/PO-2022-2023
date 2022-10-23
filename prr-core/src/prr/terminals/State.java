package prr.terminals;

import java.io.Serializable;

//abstract class fthat implements methods for all states
public abstract class State implements Serializable{

    private Terminal _terminal;
    private boolean _previousIdle;

    public State(Terminal terminal) {
        _terminal = terminal;
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

    public abstract void goToSilence();
    public abstract void turnOn();
    public abstract void turnOff();
    public abstract void endOfComm();
    public abstract void startOfComm();
    
}