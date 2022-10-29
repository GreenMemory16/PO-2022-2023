package prr.exceptions;

public class UnsupportedAtDestinationException extends Exception {

    private String id;
    private String type;

    public UnsupportedAtDestinationException(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
    
}
