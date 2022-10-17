package prr.terminals;

import prr.clients.Client;

import java.io.Serializable;

public class Basic extends Terminal implements Serializable {

    
    //no extra atributes~
    //no extra functions
    //the type of object is what matters
    public Basic(String id, Client client){
        super(id, client);
    }

   /*public boolean canMakeThisTypeOfCom(Communication com){

    }*/

    //in this case its not an equals but a its the same type?
    public boolean sameType(Terminal other){
        return (other instanceof Basic);
    }

    //this one really is an equals; for friends function;
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