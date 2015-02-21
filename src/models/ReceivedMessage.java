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
    public String title,body,senderName,senderIP;
    public int id;
    public Timestamp receivedDate;

    public ReceivedMessage(String title, String body, String senderName, String senderIP, int id, Timestamp receivedDate) {
        this.title = title;
        this.body = body;
        this.senderName = senderName;
        this.senderIP = senderIP;
        this.id = id;
        this.receivedDate = receivedDate;
    }

    public static ArrayList<ReceivedMessage> all() throws SQLException {
        ResultSet getMessages = DataBaseConnection.getAll(table);
        ArrayList<ReceivedMessage> messages = new ArrayList<>();
        while (getMessages.next()){
            ReceivedMessage message = new ReceivedMessage(getMessages.getString("TITLE"),getMessages.getString("BODY"),getMessages.getString("SENDER_NAME"),getMessages.getString("SENDER_IP"),getMessages.getInt("ID"),getMessages.getTimestamp("RECEIVED_DATE"));
            messages.add(message);
        }
        return messages;
    }
    public boolean save() throws SQLException {
        HashMap<String,String> attr = new HashMap<>();
        attr.put("TITLE",this.title);
        attr.put("BODY",this.body);
        attr.put("SENDER_NAME",this.senderName);
        attr.put("SENDER_IP",this.senderIP);
        attr.put("RECEIVED_DATE",this.receivedDate.toString());
        attr.put("ID",String.valueOf(this.id));
        return  !DataBaseConnection.insert(table,attr);
    }
}
