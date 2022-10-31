package prr.communication;

import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;

public class VideoCommunication extends InteractiveCommunication {

    public VideoCommunication(int id, Terminal sender, Terminal receiver) {
        super(id, sender, receiver);
    }

    public String getType() {
        return "VIDEO";
    }


    public boolean senderRecieverFriendship() {
        return (getSender().IsFriend(getReceiver().getId()));
    }
    
    public long calculateCost() {
        long value = 0;
        switch(getSender().getClient().getClientLevel()) {
            case "NORMAL": 
                value = (long) (30 * getDuration());
                break;
            case "GOLD": 
                value = (long)(20 * getDuration());
                break;
            case "PLATINUM": 
                value = (long) (10 * getDuration());
                break;
        }

        if (senderRecieverFriendship()) {
            value *= 0.5;
        }
        this.setCost((long) value);
        return (long) value;
    }

    public String toString() {
        return getType() + "|" + getId() + "|" + getSender().getId() + "|" + getReceiver().getId() + "|" + getDuration() + "|" + Math.round(getCost()) + "|" + getStatusToString();
    }

}