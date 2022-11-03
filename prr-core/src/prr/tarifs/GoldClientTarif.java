package prr.tarifs;

public class GoldClientTarif extends ClientTarif{
    
    public long calculateTextCost(int characters) {
        if (characters < 50) {
            return 10;
        }
        else if (characters >= 50 && characters < 100) {
            return 10;
        }
        else {
            return characters*2;
        }
    }

    public long calculateVoiceCost(int duration) {
        return 10*duration;
    }

    public long calculateVideoCost(int duration) {
        return 10*duration;
    }
}
