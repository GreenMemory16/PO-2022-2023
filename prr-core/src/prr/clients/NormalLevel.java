package prr.clients;

public class NormalLevel extends ClientLevel {

    public NormalLevel(Client client) {
        super(client);
    }

    //to be promoted to gold level client
    public void promote() {
        if (shouldPromote()) {
            getClient().setLevel(new GoldLevel(getClient()));
        }
    }

    //a normal type client cannot be demoted; does nothing
    public void demote() {
    }

    //a normal type client cannot be demoted; does nothing
    public void doubleDemote() {}

    //the rules for promotion are:
    public boolean shouldPromote() {
        return getClient().getBalance() > 500;
    }

    //cant be demoted
    public boolean shouldDemote() {
        return false;
    }

    public String levelToString() {
        return "NORMAL";
    }

}
