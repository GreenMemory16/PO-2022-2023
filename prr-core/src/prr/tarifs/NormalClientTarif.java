package prr.tarifs;

public class NormalClientTarif extends ClientTarif {
    
    public long calculateTextCost(int characters) {
        if (characters < 50) {
            return 10;
        }
        else if (characters >= 50 && characters < 100) {
            return 16;
        }
        else {
            return characters*2;
        }
    }

    public long calculateVoiceCost(int duration) {
        return 20*duration;
    }

    public long calculateVideoCost(int duration) {
        return 30*duration;
    }
}
