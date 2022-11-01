package prr.terminals;

import java.io.Serializable;
import prr.exceptions.AlreadyInStateException;

public class Off extends State implements Serializable{
    
    public Off(Terminal terminal) {
        super(terminal);
    }

     //changing state rules

    public void goToSilence() {
        getTerminal().setState(new Silence(getTerminal()));
    }

    public void turnOn() {
        if (getPreviousIdle()) {
            getTerminal().setState(new Idle(getTerminal()));
        }
        goToSilence();
    }

    public void turnOff() throws AlreadyInStateException {
        throw new AlreadyInStateException();
    }

    public void endOfComm() {}

    public void startOfComm() {}

    public boolean statePermitsInteractiveCommunication() {
        return false;
    }

    public boolean statePermitsTextCommunication() {
        return true;
    }

    @Override
    public String toString(){
        return "OFF";
    }
}
