package prr.exceptions;

public class DestinationIsBusyException extends Exception {
    
    private String id;

    /**
     * @param id
     */
    public DestinationIsBusyException(String id) {
        this.id = id;
    }

    /** @return the id*/
    public String getId() {
        return id;
    }
}
