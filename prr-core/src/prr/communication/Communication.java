package prr.communication;

import prr.terminals.Terminal;

//to be fully implemented later on
public abstract class Communication{

    private int _id;
    private Terminal _receiver;
    private Terminal _sender;
    private long _cost = 0;

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

    public long getCost() {
        return _cost;
    }


    public void setCost(long cost) {
        _cost = cost;
    }
    public boolean getStatus() {
        return _status;
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


    public boolean equals(Communication com){
        return com != null && this._id == com.getId() && this._receiver.equals(com.getReceiver()) 
        && this._sender.equals(com.getSender())
        && this._cost == com.getCost() && this._status == com.getStatus();
    }
    public abstract long calculateCost();
    
}