package prr.terminals;

public class Off extends State{

    public Off(Terminal terminal){
        super(terminal);
    }

    @Override 
    public boolean startCommunication(){
        return false;
    }

    @Override
    public boolean receiveCommunication(){
        return false;
    }

      /***** **************** */
    //to change states
    //can
    @Override
    public void idle() {
        setState(new Idle(getTerminalState()));
    }

    //cant
    @Override
    public void off() {
    }

    //can
    @Override
    public void silence() {
        setState(new Silence(getTerminalState()));
    }

    //cant
    @Override
    public void busy() {
    }
//************************* */

    @Override
    public String toString(){
        return "OFF";
    }
}
