package prr.exceptions;

public class DestinationIsOffException extends Exception {
    
    private String id;

    public DestinationIsOffException(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
