package prr.clients;

import java.io.Serializable;

import prr.communication.TextCommunication;
import prr.communication.InteractiveCommunication;
import prr.tarifs.ClientTarif;

public abstract class ClientLevel implements Serializable{

    //a client level has a client
    private Client _client;
    
    //counters
    private int _consecutiveVideoComms = 0;
    private int _consecutiveTextComms = 0;
    private ClientTarif _tarif;

    public ClientLevel(Client client, ClientTarif tarif) {
        _client = client;
        _tarif = tarif;
    }

    public Client getClient() {
        return _client;
    }

    public ClientTarif getClientTarif() {
        return _tarif;
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

    public long calculateTextCost(TextCommunication comm) { return getClientTarif().calculateTextCost(comm.getMessageSize());}

    public long calculateVoiceCost(InteractiveCommunication comm) { return getClientTarif().calculateVoiceCost(comm.getDuration());}

    public long calculateVideoCost(InteractiveCommunication comm) { return getClientTarif().calculateVideoCost(comm.getDuration());}
}
