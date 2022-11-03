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

    public long calculateCost() {
        if(getMessageSize() < 50) {
            long cost = calculateSmallText();
            this.setCost(cost);
            return cost;
        }
        else if (getMessageSize() >= 50 && getMessageSize() < 100) {
            long cost = calculateMediumText();
            this.setCost(cost);
            return cost;
        } 
        else {
            long cost = calculateBigText();
            this.setCost(cost);
            return cost;
        }
    }

    public long calculateSmallText() { 
        switch(getSender().getClient().getClientLevel()) {
            case "NORMAL": return 10;
            case "GOLD": return 10;
            case "PLATINUM": return 0;
            default: return 0;
        }
    }

    public long calculateMediumText() {
        switch(getSender().getClient().getClientLevel()) {
            case "NORMAL": return 16;
            case "GOLD": return 10;
            case "PLATINUM": return 4;
            default: return 0;
        }
    }

    public long calculateBigText() { 
        switch(getSender().getClient().getClientLevel()) {
            case "NORMAL": return (getMessageSize() * 2);
            case "GOLD": return (getMessageSize() * 2);
            case "PLATINUM": return 4;
            default: return 0;
        }
    }

    @Override
    public String toString() {
        return getType() + "|" + getId() + "|" + getSender().getId() + "|" + getReceiver().getId() + "|" + getMessageSize() + "|" + Math.round(getCost()) + "|" + getStatusToString();
    }

    
}