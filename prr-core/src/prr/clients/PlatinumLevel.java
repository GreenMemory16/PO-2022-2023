package prr.clients;

//client type
public class PlatinumLevel extends ClientLevel {

    public PlatinumLevel(Client client) {
        super(client);
    }

    public void promote() {
        /*nothing*/
    }

    public void demote() {
        if (shouldDemote()) {
            getClient().setLevel(new GoldLevel(getClient()));
        }
        else {
            doubleDemote();
        }
    }

    public void doubleDemote() {
        if (getClient().getBalance() < 0) {
            getClient().setLevel(new NormalLevel(getClient()));
        }
    }

    public boolean shouldPromote() {
        return false;
    }

    public boolean shouldDemote() {
        return getConsecutiveTextComms() >= 2 && getClient().getBalance() >= 0;
    }

    public String levelToString() {
        return "PLATINUM";
    }

}
