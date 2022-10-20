package prr.terminals;
import java.io.Serializable;

public class Fancy extends Terminal implements Serializable {

    
    //no extra atributes~
    //no extra functions
    //the type of object is what matters
    public Fancy(String id, String clientkey){
        super( id, clientkey);
    }

    
     public boolean sameType(Terminal other){
        return (other instanceof Fancy);
    }

    //to see if there's two terminals equal; might be useful for 
    //friends function
     public boolean equals(Terminal other){
         return (this.getId().equals(other.getId()) && 
         this.getClientKey().equals(other.getClientKey())
         && this.sameType(other));
     }

    @Override
    public String toStringType(){
        return "FANCY";
    }

}