package prr.communication;

import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;

//to be fully implemented later on
public abstract class Communication{

    private int _id;
    private Terminal _receiver;
    private Terminal _sender;

    private boolean _status = true;


    public Communication(int id, Terminal sender, Terminal receiver){
        _id = id;
        _sender = sender;
        _receiver = receiver;
    }

    public abstract String getType();

    public int getId() {
        return _id;
    }

    public String getStatusToString() {
        if(_status) {
            return "ONGOING";
        }
        return "FINISHED";
    }

    public void setStatus(boolean value) {
        _status = value;
    }

    public Terminal getReceiver() {
        return _receiver;
    }
    public Terminal getSender() {
        return _sender;
    }


    public abstract double calculateCost();
    
}