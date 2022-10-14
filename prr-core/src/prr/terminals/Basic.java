package prr.terminals;
import java.io.Serializable;

public class Basic extends Terminal {

    
    //no extra atributes~
    //no extra functions
    //the type of object is what matters
    public Basic(String id, String clientkey){
        super(String id, String clientkey);
    }

    public boolean canMakeThisTypeOfCom(Communication com)

    //in this case its not an equals but a its the same type?
    @Override
    public boolean sameType(Terminal other){
        return (other instanceof Basic);
    }

    //this one really is an equals; for friends function;
    @Override 
    public boolean equals(Terminal other){
        return (this.getId().equals(other.getId()) && 
        this.getClientKey().equals(other.getClientKey())
        && this.sameType(other))
    }
}