package prr.exceptions;

public class DestinationIsOffException extends Exception {
    
    private String id;

    /**
     * 
     * @param id
     */
    public DestinationIsOffException(String id) {
        this.id = id;
    }

    /**
     * 
     * @return id
     */
    public String getId() {
        return id;
    }
}
