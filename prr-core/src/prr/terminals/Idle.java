package prr.terminals;

public class Idle extends State{

    public Idle(Terminal terminal){
        super(terminal);
    }

    @Override 
    public boolean startCommunication(){
        return true;
    }

    @Override
    public boolean receiveCommunication(){
        return true;
    }

      /***** **************** */
    //to change states
    //cant
    @Override
    public void idle() {
    }

    @Override
    public void off() {
        setState(new Off(getTerminalState()));
    }

    //can
    @Override
    public void silence() {
        setState(new Silence(getTerminalState()));
    }

    
    @Override
    public void busy() {
        setState(new Busy(getTerminalState()));
    }
//************************* */

    @Override
    public String toString(){
        return "IDLE";
    }
}

