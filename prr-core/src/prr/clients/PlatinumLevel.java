package prr.clients;

import java.io.Serializable;

public class PlatinumLevel extends ClientLevel implements Serializable {

    public PlatinumLevel(Client client) {
        super();
    }

    public String getLevel() {
        return "PLATINUM";
    }

}
