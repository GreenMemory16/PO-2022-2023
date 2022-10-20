package prr.exceptions;

public class InvalidTerminalKeyExceptionCore extends Exception{

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    //terminal id
    private String id;

    public InvalidTerminalKeyExceptionCore(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
    
}