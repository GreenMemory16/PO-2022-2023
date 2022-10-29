package prr.exceptions;

public class SenderEqualsReceiverException extends Exception{

    private String _id;

    public SenderEqualsReceiverException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }
}
