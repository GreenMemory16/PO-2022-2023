package prr.exceptions;

    //to be implemented later on;
    //when a terminal can't be added as a friend
    //because it's already a friend or it is the terminal itself
public class InvalidFriendExceptionCore extends Exception{
    
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    private String id = "";

    public InvalidFriendExceptionCore(String id){
        //super("This terminal can't be friends with itself.");
        this.id = id;
    }

    public String getId() {
        return id;
    }


}