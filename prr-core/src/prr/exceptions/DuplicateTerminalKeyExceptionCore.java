package prr.exceptions;

public class DuplicateTerminalKeyExceptionCore extends Exception{

    /** Serial number for serialization. */

    private static final long serialVersionUID = 202208091753L;

    //terminal id
    private String id;

    /**
     * 
     * @param id
     */
    public DuplicateTerminalKeyExceptionCore(String id) {
        this.id = id;
    }

    /**
     * 
     * @return the id
     */
    public String getId() {
        return this.id;
    }
    
}
