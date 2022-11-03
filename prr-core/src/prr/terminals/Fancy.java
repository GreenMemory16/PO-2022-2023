package prr.terminals;

import prr.clients.Client;

public class Fancy extends Terminal {

    public Fancy(String id, Client client){
        super(id, client);
    }

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