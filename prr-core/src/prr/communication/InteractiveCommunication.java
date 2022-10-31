package prr.communication;

import prr.terminals.Terminal;

public abstract class InteractiveCommunication extends Communication {
    
    private int _duration = 0;

    public InteractiveCommunication(int id, Terminal sender, Terminal receiver) {
        super(id, sender, receiver);
    }

    public int getDuration() {
        return _duration;
    }

    public void setDuration(int duration) {
        _duration = duration;
    }

    public abstract String getType();


    public abstract boolean senderRecieverFriendship();
    
    public abstract long calculateCost(); 
        
}