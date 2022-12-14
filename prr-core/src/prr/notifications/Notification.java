package prr.notifications;

import java.io.Serializable;

import prr.terminals.Terminal;

public abstract class Notification implements Serializable{
    
    Terminal _sender;

    public Notification(Terminal sender) {
        _sender = sender;
    }

    public Terminal getTerminal() {
        return _sender;
    }

    public abstract String getType();

    public boolean equals(Object o) {
        if (o instanceof Notification) {
            Notification n = (Notification) o;
            return getTerminal().equals(n.getTerminal()) && getType().equals(n.getType());
        }
        return false;
    }
}
