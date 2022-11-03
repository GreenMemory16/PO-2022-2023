package prr.terminals;

import prr.exceptions.AlreadyInStateException;

public class Off extends State {
    
    public Off(Terminal terminal, boolean previousIdle) {
        super(terminal, previousIdle);
    }

     //changing state rules

    public void goToSilence() {
        getTerminal().setState(new Silence(getTerminal(), getPreviousIdle()));
    }

    public void turnOn() {
        getTerminal().setState(new Idle(getTerminal(), getPreviousIdle()));
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
