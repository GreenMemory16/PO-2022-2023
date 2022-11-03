package prr.tarifs;

import java.io.Serializable;

public abstract class ClientTarif implements Serializable{

    public abstract long calculateTextCost(int characters);

    public abstract long calculateVideoCost(int duration);

    public abstract long calculateVoiceCost(int duration);
}
