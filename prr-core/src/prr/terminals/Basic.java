package prr.terminals;

import prr.clients.Client;

public class Basic extends Terminal {

    
    //no extra atributes
    //no extra methods
    public Basic(String id, Client client){
        super(id, client);
    }
     //function for friends function later on;
    public boolean equals(Terminal other){
        return (this.getId().equals(other.getId()) && 
        this.getClient().getKey().equals(other.getClient().getKey())
        &&  (other instanceof Basic));
    }

    @Override
    public String toStringType(){
        return"BASIC";
    }

    public boolean canSupportVideoCommunication() {
        return false;
    }
}