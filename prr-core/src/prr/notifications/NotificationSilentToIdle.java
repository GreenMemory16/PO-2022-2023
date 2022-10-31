package prr.notifications;

import prr.terminals.Terminal;

public class NotificationSilentToIdle extends Notification{

    public NotificationSilentToIdle(Terminal sender) {
        super(sender);
    }
    
    public String getType() {
        return "S2I";
    }

    public String toString() {
        return getType() + "|" + getTerminal().getId();
    }
}