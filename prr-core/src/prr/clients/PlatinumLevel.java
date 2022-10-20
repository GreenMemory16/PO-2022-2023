package prr.clients;

import java.io.Serializable;

//client type
public class PlatinumLevel extends ClientLevel implements Serializable {

    public PlatinumLevel(Client client) {
        super();
    }

    public String getLevel() {
        return "PLATINUM";
    }

}
