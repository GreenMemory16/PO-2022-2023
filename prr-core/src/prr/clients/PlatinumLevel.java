package prr.clients;

//client type
public class PlatinumLevel extends ClientLevel {

    public PlatinumLevel(Client client) {
        super(client);
    }

    //a platinium level client cant be promoted; does nothing
    public void promote() {
    }

    //to be demoted to gold level client
    public void demote() {
        if (shouldDemote()) {
            getClient().setLevel(new GoldLevel(getClient()));
        }
        else {
            doubleDemote();
        }
    }

    //to be demoted to normal level client
    public void doubleDemote() {
        if (getClient().getBalance() < 0) {
            getClient().setLevel(new NormalLevel(getClient()));
        }
    }

    //a platinium level client cant be promoted
    public boolean shouldPromote() {
        return false;
    }

    //the rules for demotion are:
    public boolean shouldDemote() {
        return getConsecutiveTextComms() >= 2 && getClient().getBalance() >= 0;
    }

    public String levelToString() {
        return "PLATINUM";
    }

}
