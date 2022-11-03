package prr.communication;

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
    

    public String toString() {
        return getType() + "|" + getId() + "|" + getSender().getId() + "|" + getReceiver().getId() + "|" + getDuration() + "|" + Math.round(getCost()) + "|" + getStatusToString();
    }

}