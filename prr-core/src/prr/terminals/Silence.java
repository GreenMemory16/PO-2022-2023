package prr.terminals;

public class Silence extends State{

    public Silence(){
        super();
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

    @Override
    public boolean switchToIdle() {
        return true;
    }

    @Override
    public boolean switchToOff() {
        return true;
    }

    //can
    @Override
    public boolean switchToSilence() {
        return false;
    }

    
    @Override
    public boolean switchToBusy() {
        return true;
    }
//************************* */

    @Override
    public String toString(){
        return "SILENCE";
    }
}