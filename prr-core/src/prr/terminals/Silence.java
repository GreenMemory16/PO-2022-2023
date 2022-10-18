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

    @Override
    public String toString(){
        return "SILENCE";
    }
}
