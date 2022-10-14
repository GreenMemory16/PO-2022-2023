package prr.exceptions;

public class InvalidFriendException extends Exception{
    
	private static final long serialVersionUID = 202208091753L;

    public InvalidFriendException(){
        super("This terminal can't be friends with itself.");
    }
}