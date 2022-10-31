package prr.notifications;

import prr.terminals.Terminal;

public abstract class Notification {
    
    Terminal _sender;

    public Notification(Terminal sender) {
        _sender = sender;
    }

    public Terminal getTerminal() {
        return _sender;
    }

    public abstract String getType();
}
