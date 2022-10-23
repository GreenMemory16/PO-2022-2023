package prr.terminals;

import java.io.Serializable;

//class Silence
public class Silence extends State implements Serializable{

    public Silence(Terminal terminal) {
        super(terminal);
        setPreviousIdle(false);
    }

    //changing state rules
    public void goToSilence() {}

    public void turnOn() {
        getTerminal().setState(new Idle(getTerminal()));
    }

    public void turnOff() {
        setPreviousIdle(false);
        getTerminal().setState(new Off(getTerminal()));
    }

    public void startOfComm() {
        setPreviousIdle(false);
        getTerminal().setState(new Busy(getTerminal()));
    }

    public void endOfComm() {}


    @Override
    public String toString(){
        return "SILENCE";
    }
}
