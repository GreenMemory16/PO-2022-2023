package prr.communication;

import prr.terminals.Terminal;

public class TextCommunication extends Communication {

    private String _textMessage;

    public TextCommunication(int id, Terminal sender, Terminal receiver, String textMessage) {
        super(id, sender, receiver);
        _textMessage = textMessage;
    }

    public int getMessageSize() {
        return _textMessage.length();
    }

    public String getType() {
        return "TEXT";
    }

    @Override
    public String toString() {
        return getType() + "|" + getId() + "|" + getSender().getId() + "|" + getReceiver().getId() + "|" + getMessageSize() + "|" + Math.round(getCost()) + "|" + getStatusToString();
    }

    
}