package prr.communication;

import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;

public class VoiceCommunication extends Communication {

    private int _duration;

    public VoiceCommunication(int id, Terminal sender, Terminal reciever, int duration) {
        super(id, sender, reciever);
        _duration = duration;
    }
}