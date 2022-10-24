package prr.terminals;

import java.io.Serializable;
import prr.exceptions.InvalidTerminalKeyExceptionCore;

public class Basic extends Terminal implements Serializable {

    
    //no extra atributes
    //no extra methods
    public Basic(String id, String clientKey)throws InvalidTerminalKeyExceptionCore{
        super(id, clientKey);
    }
     //function for friends function later on;
    public boolean equals(Terminal other){
        return (this.getId().equals(other.getId()) && 
        this.getClientKey().equals(other.getClientKey())
        &&  (other instanceof Basic));
    }

    @Override
    public String toStringType(){
        return"BASIC";
    }

    public boolean canDoInteractiveCommunication() {
        return false;
    }
}