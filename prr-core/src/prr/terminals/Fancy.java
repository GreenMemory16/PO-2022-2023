package prr.terminals;

import prr.clients.Client;

public class Fancy extends Terminal {

    
    //no extra atributes
    //no extra functions
    public Fancy(String id, Client client){
        super(id, client);
    }

     //function for friends function later on;
     public boolean equals(Terminal other){
         return (this.getId().equals(other.getId()) && 
         this.getClient().getKey().equals(other.getClient().getKey())
         && (other instanceof Fancy));
     }

    @Override
    public String toStringType(){
        return "FANCY";
    }

    public boolean canSupportVideoCommunication() {
        return true;
    }

}