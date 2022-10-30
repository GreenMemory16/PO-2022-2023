package prr.exceptions;

public class DestinationIsBusyException extends Exception {
    
    private String id;

    public DestinationIsBusyException(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}