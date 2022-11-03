package prr.terminals;

import prr.exceptions.AlreadyInStateException;

public class Idle extends State {

    public Idle(Terminal terminal, boolean previousIdle) {
        super(terminal, previousIdle);
    }

    public void goToSilence() {
        getTerminal().setState(new Silence(getTerminal(), true));
    }

    public void turnOn() throws AlreadyInStateException{
        throw new AlreadyInStateException();
    }

    public void turnOff() {
        getTerminal().setState(new Off(getTerminal(), true));
    }

    public void endOfComm() {}

    public void startOfComm() {
        getTerminal().setState(new Busy(getTerminal(), true));
    }

    public boolean statePermitsInteractiveCommunication() {
        return true;
    }

    public boolean statePermitsTextCommunication() {
        return true;
    }

    @Override
    public String toString(){
        return "IDLE";
    }
}

