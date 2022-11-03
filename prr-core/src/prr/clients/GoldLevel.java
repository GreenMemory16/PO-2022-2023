package prr.clients;

import prr.tarifs.GoldClientTarif;

//client type
public class GoldLevel extends ClientLevel {

    public GoldLevel(Client client) {
        super(client, new GoldClientTarif());
    }

    //to be promoted to platinium level client
    public void promote() {
        if (shouldPromote()) {
            getClient().setLevel(new PlatinumLevel(getClient()));
        }
    }

    //to be demoted to normal level client
    public void demote() {
        if (shouldDemote()) {
            getClient().setLevel(new NormalLevel(getClient()));
        }
    }

    //a gold level client cant be demoted twice; does nothing
    public void doubleDemote() {}

    //the rules for promotion are:
    public boolean shouldPromote() {
        return getConsecutiveVideoComms() >= 5 && getClient().getBalance() >= 0;
    }

    //the rules for demotion are:
    public boolean shouldDemote() {
        return getClient().getBalance() < 0;
    }

    public String levelToString() {
        return "GOLD";
    }
    
}
