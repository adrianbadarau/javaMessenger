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
public class SentMessage {
    public static String table = "APP.SENT_MESSAGES";
    public String title,body;
    public int contact_id,id;
    public Timestamp sent_date;

    public SentMessage(String title, String body, int contact_id ) {
        this.title = title;
        this.body = body;
        this.contact_id = contact_id;
    }

    public SentMessage(String title, String body, int contact_id, int id, Timestamp sent_date) {
        this.title = title;
        this.body = body;
        this.contact_id = contact_id;
        this.id = id;
        this.sent_date = sent_date;
    }

    public boolean save() throws SQLException {
        HashMap<String, String> attr = new HashMap<>();
        attr.put("TITLE", this.title);
        attr.put("BODY", this.body);
        attr.put("CONTACT_ID", String.valueOf((this.contact_id)));
        attr.put("SENT_DATE",DataBaseConnection.generateTimeStamp());
        boolean saved = false;
        saved = !DataBaseConnection.insert(table, attr);
        return saved;
    }
    public static ArrayList<SentMessage> all() throws SQLException {
        ResultSet getMessages = DataBaseConnection.getAll(table);
        ArrayList<SentMessage> all = new ArrayList<>();
        while (getMessages.next()){
            SentMessage message = new SentMessage(getMessages.getString("TITLE"),getMessages.getString("BODY"),getMessages.getInt("CONTACT_ID"),getMessages.getInt("ID"),getMessages.getTimestamp("SENT_DATE"));
            all.add(message);
        }
        return all;
    }
    public SentMessage find(int id) throws SQLException {
        ResultSet getMessage = DataBaseConnection.find(table,this.id);
        SentMessage message=null;
        while (getMessage.next()){
            message = new SentMessage(getMessage.getString("TITLE"),getMessage.getString("BODY"),getMessage.getInt("CONTACT_ID"),getMessage.getInt("ID"),getMessage.getTimestamp("SENT_DATE"));
        }
        return message;
    }
    public SentMessage findWhere(String colName, String value) throws SQLException {
        ResultSet getMessage= DataBaseConnection.findWhere(table,colName,value);
        SentMessage message=null;
        while (getMessage.next()){
            message = new SentMessage(getMessage.getString("TITLE"),getMessage.getString("BODY"),getMessage.getInt("CONTACT_ID"),getMessage.getInt("ID"),getMessage.getTimestamp("SENT_DATE"));
        }
        return message;
    }
    public static SentMessage findBySender(int value) throws SQLException {
        ResultSet getMessage = DataBaseConnection.findWhere(table,"CONTACT_ID", String.valueOf(value));
        SentMessage message = null;
        while (getMessage.next()){
            message = new SentMessage(getMessage.getString("TITLE"),getMessage.getString("BODY"),getMessage.getInt("CONTACT_ID"),getMessage.getInt("ID"),getMessage.getTimestamp("SENT_DATE"));
        }
        return  message;
    }
    public boolean update() throws SQLException {
        HashMap<String, String> attr = new HashMap<>();
        attr.put("TITLE", this.title);
        attr.put("BODY", this.body);
        attr.put("CONTACT_ID", String.valueOf((this.contact_id)));
        attr.put("SENT_DATE",DataBaseConnection.generateTimeStamp());
        return DataBaseConnection.update(table,attr,this.id);
    }

}
