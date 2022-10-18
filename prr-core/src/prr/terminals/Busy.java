package prr.terminals;

public class Busy extends State{

    public Busy(Terminal terminal){
        super(terminal);
    }

    @Override 
    public boolean startCommunication(){
        return false;
    }

    @Override
    public boolean receiveCommunication(){
        //só de texto
        return true;
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
        return "BUSY";
    }
}
