package prr.exceptions;

public class DestinationIsSilentException extends Exception{
    //terminal id
    private String id;

    /**
     * 
     * @param id
     */
    public DestinationIsSilentException(String id) {
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
