package prr.clients;

public class NormalLevel extends Client.Level {

    public NormalLevel(Client client) {
        client.super();
    }

    public String getLevel() {
        return "NORMAL";
    }

    
    
}
