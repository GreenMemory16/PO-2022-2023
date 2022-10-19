package prr.terminals;

import java.io.Serializable;

public class Basic extends Terminal implements Serializable {

    
    //no extra atributes
    //no extra functions
    //the type of object is what matters
    public Basic(String id, String clientKey){
        super(id, clientKey);
    }

    public boolean sameType(Terminal other){
        return (other instanceof Basic);
    }

    //this one really is an equals; for friends function later on
    //@Override 
    public boolean equals(Terminal other){
        return (this.getId().equals(other.getId()) && 
        this.getClientKey().equals(other.getClientKey())
        && this.sameType(other));
    }

    @Override
    public String toStringType(){
        return"BASIC";
    }
}