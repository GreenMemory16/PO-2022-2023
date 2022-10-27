package prr.communication;

import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;

//to be fully implemented later on
public abstract class Communication{

    private int _id;
    private Terminal _receiver;
    private Terminal _sender;


    public Communication(int id, Terminal sender, Terminal receiver){
        _id = id;
        _sender = sender;
        _receiver = receiver;
    }

    public abstract String getType();
    
    public int getId() {
        return _id;
    }

    public Terminal getReceiver() {
        return _receiver;
    }
    public Terminal getSender() {
        return _sender;
    }


    public abstract double calculateCost();

    public String toString() {
        return getType() + "|" + getId() + "|" + getSender().getId() + "|" + getReceiver().getId() + "|" + 0 + "|" + 0 + "|" + "FIXME";
    }
    
}