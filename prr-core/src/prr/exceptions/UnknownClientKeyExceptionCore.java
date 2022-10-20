package prr.exceptions;

public class UnknownClientKeyExceptionCore extends Exception{
    
    	/** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    //client key
    private String _key;

    public UnknownClientKeyExceptionCore(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
}
