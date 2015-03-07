package models;

import server.DataBaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by adib on 2/21/15.
 */
public class ReceivedMessage {
    public static String table="APP.RECEIVED_MESSAGES";
    public String title,body,senderIP;
    public int id;
    public Timestamp receivedDate;

    public ReceivedMessage() {
        this.receivedDate = DataBaseConnection.generateTimeStampT();
    }

    public void setAttribute(String attrName, String attrValue){
        switch (attrName){
            case "TITLE":
                this.title = attrValue;
                break;
            case "BODY":
                this.body = attrValue;
                break;
            case "FROM":
                this.senderIP = attrValue;
                break;
        }
    }

    public ReceivedMessage(String title, String body, String senderIP, int id, Timestamp receivedDate) {
        this.title = title;
        this.body = body;
        this.senderIP = senderIP;

        this.id = id;
        this.receivedDate = receivedDate;
    }

    public static ArrayList<ReceivedMessage> all() throws SQLException {
        ResultSet getMessages = DataBaseConnection.getAll(table);
        ArrayList<ReceivedMessage> messages = new ArrayList<>();
        while (getMessages.next()){
            ReceivedMessage message = new ReceivedMessage(getMessages.getString("TITLE"),getMessages.getString("BODY"),getMessages.getString("SENDER_IP"),getMessages.getInt("ID"),getMessages.getTimestamp("RECEIVED_DATE"));
            messages.add(message);
        }
        return messages;
    }
    public boolean save() throws SQLException {
        HashMap<String,String> attr = new HashMap<>();
        attr.put("TITLE",this.title);
        attr.put("BODY",this.body);
        attr.put("SENDER_IP",this.senderIP);
        attr.put("RECEIVED_DATE",this.receivedDate.toString());
        return  !DataBaseConnection.insert(table,attr);
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message.append("From: ");
        message.append(this.senderIP).append("\n");
        message.append("Title: ");
        message.append(this.title).append("\n");
        message.append("Message: ");
        message.append(this.body).append("\n");
        message.append("Date: ");
        message.append(this.receivedDate);
        return message.toString();
    }
}
