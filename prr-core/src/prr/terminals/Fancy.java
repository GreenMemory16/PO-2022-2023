package prr.terminals;

import java.io.Serializable;

public class Fancy extends Terminal implements Serializable {

    
    //no extra atributes
    //no extra functions
    public Fancy(String id, String clientKey){
        super(id, clientKey);
    }

     //function for friends function later on;
     public boolean equals(Terminal other){
         return (this.getId().equals(other.getId()) && 
         this.getClientKey().equals(other.getClientKey())
         && (other instanceof Fancy));
     }

    @Override
    public String toStringType(){
        return "FANCY";
    }

    public boolean canDoInteractiveCommunication() {
        return getState().statePermitsCommunication();
    }

}