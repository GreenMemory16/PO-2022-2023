package prr.terminals;

import java.io.Serializable;

public class Busy extends State implements Serializable{

    public Busy(Terminal terminal) {
        super(terminal);
    }

     //changing state rules

    public void goToSilence() {
        getTerminal().setState(new Silence(getTerminal()));
    }

    public void turnOn() {}

    public void turnOff() {}

    public void startOfComm() {}

    public void endOfComm() {
        if (getPreviousIdle()) {
            getTerminal().setState(new Idle(getTerminal()));
        }
        goToSilence();
    }
    

    @Override
    public String toString(){
        return "BUSY";
    }
}
