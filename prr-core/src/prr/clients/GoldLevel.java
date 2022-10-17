package prr.clients;

public class GoldLevel extends Client.Level {

    public GoldLevel(Client client) {
        client.super();
    }

    public String getLevel() {
        return "GOLD";
    }
    
}
