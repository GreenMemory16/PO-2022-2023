package prr.communication;

import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;

public class VoiceCommunication extends InteractiveCommunication {

    public VoiceCommunication(int id, Terminal sender, Terminal receiver) {
        super(id, sender, receiver);
    }

    public String getType() {
        return "VOICE";
    }


    public boolean senderRecieverFriendship() {
        return (getSender().IsFriend(getReceiver().getId()));
    }

    public double calculateCost() {
        double value = 0;
        switch(getSender().getClient().getClientLevel()) {
            case "NORMAL": 
                value = (20 * getDuration());
                break;
            case "GOLD": 
                value = (10 * getDuration());
                break;
            case "PLATINUM": 
                value = (10 * getDuration());
                break;
        }

        if (senderRecieverFriendship()) {
            value *= 0.5;
        }
        return value;
    }

    public String toString() {
        return getType() + "|" + getId() + "|" + getSender().getId() + "|" + getReceiver().getId() + "|" + getDuration() + "|" + Math.round(getCost()) + "|" + getStatusToString();
    }

}