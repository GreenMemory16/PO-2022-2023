package prr.exceptions;

public class InvalidFriendExceptionCore extends Exception{
    
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    private String id = "";

    public InvalidFriendExceptionCore(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

}