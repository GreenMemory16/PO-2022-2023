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

    @Override
    public String toString(){
        return "OFF";
    }
}
