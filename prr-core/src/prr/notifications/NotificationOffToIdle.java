package prr.notifications;

import prr.terminals.Terminal;

public class NotificationOffToIdle extends Notification{

    public NotificationOffToIdle(Terminal sender) {
        super(sender);
    } 

    public String getType() {
        return "O2I";
    }

    public String toString() {
        return getType() + "|" + getTerminal().getId();
    }
    
}
