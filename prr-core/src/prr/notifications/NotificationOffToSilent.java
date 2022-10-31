package prr.notifications;

import prr.terminals.Terminal;

public class NotificationOffToSilent extends Notification{

    public NotificationOffToSilent(Terminal sender) {
        super(sender);
    } 

    public String getType() {
        return "O2S";
    }

    public String toString() {
        return getType() + "|" + getTerminal().getId();
    }
    
}
