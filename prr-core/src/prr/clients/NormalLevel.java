package prr.clients;

import java.io.Serializable;

//client type
public class NormalLevel extends ClientLevel implements Serializable{

    public NormalLevel(Client client) {
        super();
    }

    public String getLevel() {
        return "NORMAL";
    }

    
    
}
