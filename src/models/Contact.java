package models;

import server.DataBaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by adib on 2/21/15.
 */
public class Contact {
    public static String table = "APP.CONTACTS";
    public String name, address;
    public int id;

    public Contact() {
    }

    public Contact(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Contact(String name, String address, int id) {
        this.name = name;
        this.address = address;
        this.id = id;
    }

    public static String getTable() {
        return table;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public boolean save() throws SQLException {
        HashMap<String, String> attr = new HashMap<>();
        attr.put("NAME", this.name);
        attr.put("ADDRESS", this.address);
        boolean saved = false;
        saved = !DataBaseConnection.insert(table, attr);
        return saved;
    }
    public static ArrayList<Contact> all() throws SQLException {
        ResultSet getContacts = DataBaseConnection.getAll(table);
        ArrayList<Contact> all = new ArrayList<>();
        while (getContacts.next()){
            Contact contact = new Contact(getContacts.getString("NAME"),getContacts.getString("ADDRESS"),getContacts.getInt("ID"));
            all.add(contact);
        }
        return all;
    }
    public Contact find(int id) throws SQLException {
        ResultSet getContact = DataBaseConnection.find(table,this.id);
        Contact contact=null;
        while (getContact.next()){
            contact = new Contact(getContact.getString("NAME"),getContact.getString("ADDRESS"),getContact.getInt("ID"));
        }
        return contact;
    }
    public Contact findWhere(String colName, String value) throws SQLException {
        ResultSet getContact = DataBaseConnection.findWhere(table,colName,value);
        Contact contact=null;
        while (getContact.next()){
            contact = new Contact(getContact.getString("NAME"),getContact.getString("ADDRESS"),getContact.getInt("ID"));
        }
        return contact;
    }
    public static Contact findByName(String value) throws SQLException {
        ResultSet getContact = DataBaseConnection.findWhere(table,"NAME",value);
        Contact contact = null;
        while (getContact.next()){
            contact = new Contact(getContact.getString("NAME"),getContact.getString("ADDRESS"),getContact.getInt("ID"));
        }
        return  contact;
    }
    public boolean update() throws SQLException {
        HashMap<String,String> attr = new HashMap<>();
        attr.put("Name",this.name);
        attr.put("ADDRESS",this.address);
        return DataBaseConnection.update(table,attr,this.id);
    }
}
