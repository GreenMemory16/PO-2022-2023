package prr.exceptions;

public class DuplicateClientKeyExceptionCore extends Exception{

        /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    //client key
    private String _key;

    /**
     * 
     * @param key
     */
    public DuplicateClientKeyExceptionCore(String key) {
        _key = key;
    }

    /**
     * 
     * @return _key
     */
    public String getKey() {
        return _key;
    }
    
}
