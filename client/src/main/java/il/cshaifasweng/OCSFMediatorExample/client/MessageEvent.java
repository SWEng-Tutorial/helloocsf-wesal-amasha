package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.entities.Grade;
import il.cshaifasweng.OCSFMediatorExample.entities.entities.Student;

import java.util.List;

public class MessageEvent {
    private Message message;



    public Message getMessage() {
        return message;
    }


    public MessageEvent(Message message) {
        this.message = message;
    }
}
