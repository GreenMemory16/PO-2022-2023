package prr.terminals;

import java.io.Serializable;
import prr.exceptions.AlreadySilentExceptionCore;
import prr.exceptions.AlreadyInStateException;

//class Silence
public class Silence extends State implements Serializable{

    public Silence(Terminal terminal, boolean previousIdle) {
        super(terminal, previousIdle);
    }

    //changing state rules
    public void goToSilence() throws AlreadyInStateException{
        throw new AlreadyInStateException();
    }

    public void turnOn() {
        getTerminal().setState(new Idle(getTerminal(), false));
    }

    public void turnOff() {
        setPreviousIdle(false);
        getTerminal().setState(new Off(getTerminal(), false));
    }

    public void startOfComm() {
        setPreviousIdle(false);
        getTerminal().setState(new Busy(getTerminal(), false));
    }

    public void endOfComm() {}

    public boolean statePermitsInteractiveCommunication() {
        return true;
    }

    public boolean statePermitsTextCommunication() {
        return true;
    }

    @Override
    public String toString(){
        return "SILENCE";
    }
}
