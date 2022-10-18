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

    @Override
    public String toString(){
        return "IDLE";
    }
}

