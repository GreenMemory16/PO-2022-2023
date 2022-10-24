package prr.terminals;

import java.io.Serializable;
import prr.exceptions.AlreadyInStateException;

public class Idle extends State implements Serializable{

    public Idle(Terminal terminal) {
        super(terminal);
        setPreviousIdle(true);
    }

     //changing state rules

    public void goToSilence() {
        getTerminal().setState(new Silence(getTerminal()));
    }

    public void turnOn() throws AlreadyInStateException{
        throw new AlreadyInStateException();
    }

    public void turnOff() {
        setPreviousIdle(true);
        getTerminal().setState(new Off(getTerminal()));
    }

    public void endOfComm() {}

    public void startOfComm() {
        setPreviousIdle(true);
        getTerminal().setState(new Busy(getTerminal()));
    }

    public boolean statePermitsCommunication() {
        return true;
    }

    @Override
    public String toString(){
        return "IDLE";
    }
}

