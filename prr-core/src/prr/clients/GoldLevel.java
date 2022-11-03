package prr.clients;

import java.io.Serial;
import java.io.Serializable;

//client type
public class GoldLevel extends ClientLevel implements Serializable {

    public GoldLevel(Client client) {
        super(client);
    }

    public void promote() {
        if (shouldPromote()) {
            getClient().setLevel(new PlatinumLevel(getClient()));
        }
    }

    public void demote() {
        if (shouldDemote()) {
            getClient().setLevel(new NormalLevel(getClient()));
        }
    }

    public void doubleDemote() {/*nothing*/}

    public boolean shouldPromote() {
        return getConsecutiveVideoComms() >= 5 && getClient().getBalance() >= 0;
    }

    public boolean shouldDemote() {
        return getClient().getBalance() < 0;
    }

    public String levelToString() {
        return "GOLD";
    }
    
}
