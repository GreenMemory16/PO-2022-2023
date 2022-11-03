package prr.clients;

import java.io.Serializable;

public abstract class ClientLevel implements Serializable{

    private Client _client;
    private int _consecutiveVideoComms = 0;
    private int _consecutiveTextComms = 0;

    public ClientLevel(Client client) {
        _client = client;
    }

    public Client getClient() {
        return _client;
    }

    public int getConsecutiveVideoComms() {return _consecutiveVideoComms;}

    public int getConsecutiveTextComms() {return _consecutiveTextComms;}

    public void incrementConsecutiveVideoComms() {_consecutiveVideoComms++;}

    public void resetConsecutiveVideoComms() {_consecutiveVideoComms = 0;}

    public void incrementConsecutiveTextComms () {_consecutiveTextComms++;} 

    public void resetConsecutiveTextComms() {_consecutiveTextComms = 0;}

    public abstract void promote();

    public abstract void demote();

    public abstract void doubleDemote();

    public abstract boolean shouldPromote();

    public abstract boolean shouldDemote();
    
    public abstract String levelToString();
}
