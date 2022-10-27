package prr.communication;

import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;

public class VideoCommunication extends Communication {

    private int _duration = 0;

    public VideoCommunication(int id, Terminal sender, Terminal receiver, int duration) {
        super(id, sender, receiver);
        _duration = duration;
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
            case "NOTMAL": value = (20 * getDuration());
            case "GOLD": value = (10 * getDuration());
            case "PLATINUM": value = (10 * getDuration());
        }

        if (senderRecieverFriendship()) {
            value *= 0.5;
        }
        return value;
    }

}