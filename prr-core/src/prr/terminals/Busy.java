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

    @Override
    public String toString(){
        return "BUSY";
    }
}
