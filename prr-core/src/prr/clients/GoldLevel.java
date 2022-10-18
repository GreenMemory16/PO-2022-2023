package prr.clients;

import java.io.Serial;
import java.io.Serializable;

public class GoldLevel extends ClientLevel implements Serializable {

    public GoldLevel(Client client) {
        super();
    }

    public String getLevel() {
        return "GOLD";
    }
    
}
