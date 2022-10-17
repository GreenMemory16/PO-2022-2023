package prr.clients;

public class PlatinumLevel extends Client.Level {

    public PlatinumLevel(Client client) {
        client.super();
    }

    public String getLevel() {
        return "PLATINUM";
    }

}
