package prr.notifications;

import prr.terminals.Terminal;

public class NotificationBusyToIdle extends Notification {

    public NotificationBusyToIdle(Terminal sender) {
        super(sender);
    }
    
    public String getType() {
        return "B2I";
    }

    public String toString() {
        return getType() + "|" + getTerminal().getId();
    }
}
