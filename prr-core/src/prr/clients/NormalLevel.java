package prr.clients;

import java.io.Serializable;

//client type
public class NormalLevel extends ClientLevel implements Serializable{

    public NormalLevel(Client client) {
        super(client);
    }

    public void promote() {
        if (shouldPromote()) {
            getClient().setLevel(new GoldLevel(getClient()));
        }
    }

    public void demote() {
        /*nothing*/
    }

    public void doubleDemote() {/*nothing*/}

    public boolean shouldPromote() {
        return getClient().getBalance() > 500;
    }

    public boolean shouldDemote() {
        return false;
    }

    public String levelToString() {
        return "NORMAL";
    }

    
    
}
