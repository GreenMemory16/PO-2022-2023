package prr.exceptions;

public class InvalidTerminalKeyExceptionCore extends Exception{

    private static final long serialVersionUID = 202208091753L;

    private String id;

    public InvalidTerminalKeyExceptionCore(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
    
}