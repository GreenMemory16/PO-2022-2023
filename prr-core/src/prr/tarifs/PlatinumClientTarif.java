package prr.tarifs;

public class PlatinumClientTarif extends ClientTarif {
    
    public long calculateTextCost(int characters) {
        if (characters < 50) {
            return 0;
        }
        else if (characters >= 50 && characters < 100) {
            return 4;
        }
        else {
            return 4;
        }
    }

    public long calculateVoiceCost(int duration) {
        return 10*duration;
    }

    public long calculateVideoCost(int duration) {
        return 10*duration;
    }
}
