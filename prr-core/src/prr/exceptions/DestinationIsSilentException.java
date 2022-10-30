
package prr.exceptions;

public class DestinationIsSilentException extends Exception{
    
    private String id;

    public DestinationIsSilentException(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}