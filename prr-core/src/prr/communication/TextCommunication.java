package prr.communication;

import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;

public class TextCommunication extends Communication {

    private String _textMessage;

    public TextCommunication(int id, Terminal sender, Terminal reciever, String textMessage) {
        super(id, sender, reciever);
        _textMessage = textMessage;
    }
}