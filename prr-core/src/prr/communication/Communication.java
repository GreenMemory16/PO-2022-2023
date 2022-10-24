package prr.communication;

import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;

//to be fully implemented later on
public abstract class Communication{

    private int _id;
    private Terminal _reciever;
    private Terminal _sender;


    public Communication(int id, Terminal sender, Terminal reciever){
        _id = id;
        _sender = sender;
        _reciever = reciever;
    }

    
    
}