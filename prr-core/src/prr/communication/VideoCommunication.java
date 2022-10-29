package prr.communication;

import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;

public class VideoCommunication extends Communication {

    private int _duration = 0;

    public VideoCommunication(int id, Terminal sender, Terminal receiver) {
        super(id, sender, receiver);
    }

    public int getDuration() {
        return _duration;
    }

    public String getType() {
        return "VIDEO";
    }


    public boolean senderRecieverFriendship() {
        return (getSender().IsFriend(getReceiver().getId()));
    }
    
    public double calculateCost() {
        double value = 0;
        switch(getSender().getClient().getClientLevel()) {
            case "NOTMAL": 
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
        return getType() + "|" + getId() + "|" + getSender().getId() + "|" + getReceiver().getId() + "|" + getDuration() + "|" + 0 + "|" + getStatusToString();
    }

}