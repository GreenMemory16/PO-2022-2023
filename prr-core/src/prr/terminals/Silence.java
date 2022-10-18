package prr.terminals;

public class Silence extends State{

    public Silence(Terminal terminal){
        super(terminal);
    }

    @Override 
    public boolean startCommunication(){
        return true;
    }

    @Override
    public boolean receiveCommunication(){
        // só de texto
        return true;
    }

     /***** **************** */
    //to change states
    //can
    @Override
    public void idle() {
        setState(new Idle(getTerminalState()));
    }

    //
    @Override
    public void off() {
        setState(new Off(getTerminalState()));
    }

    //cant
    @Override
    public void silence() {
    }

    //cant
    @Override
    public void busy() {
        setState(new Busy(getTerminalState()));
    }
//************************* */

    @Override
    public String toString(){
        return "SILENCE";
    }
}
