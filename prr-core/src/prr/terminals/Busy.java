package prr.terminals;

public class Busy extends State {

    public Busy(Terminal terminal, boolean previousIdle) {
        super(terminal, previousIdle);
    }

     //changing state rules

    public void goToSilence() {
        getTerminal().setState(new Silence(getTerminal(), getPreviousIdle()));
        setPreviousIdle(false);
    }

    public void turnOn() {}

    public void turnOff() {}

    public void startOfComm() {}

    public void endOfComm() {
        if (getPreviousIdle()) {
            getTerminal().setState(new Idle(getTerminal(),getPreviousIdle()));
        }
        else {
            goToSilence();
        }
    }
    
    public boolean statePermitsInteractiveCommunication() {
        return false;
    }

    public boolean statePermitsTextCommunication() {
        return true;
    }

    @Override
    public String toString(){
        return "BUSY";
    }
}