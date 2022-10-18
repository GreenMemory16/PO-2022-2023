package prr.exceptions;

public class UnknownTerminalKeyExceptionCore extends Exception{
    
    private static final long serialVersionUID = 202208091753L;

    private String id;

    public UnknownTerminalKeyExceptionCore(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
