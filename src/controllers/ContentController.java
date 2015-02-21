package controllers;

import models.Contact;
import server.DataBaseConnection;
import views.Content;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by adrian on 18.02.2015.
 */
public class ContentController {
    public static void populateContent(){

        Content.sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Content.showConnection.setText("Sending Message...");
                HashMap<String,String> data = new HashMap<String, String>();
                data.put("TITLE",Content.messageTitle.getText());
                data.put("BODY",Content.messageBody.getText());
                int contactId = 1;
                try {
                    Contact contact = Contact.findByName(Content.users.getSelectedItem());
                    contactId = contact.id;
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                data.put("CONTACT_ID",""+contactId);
                data.put("SENT_DATE", DataBaseConnection.generateTimeStamp());
                try {
                    System.out.println(DataBaseConnection.insert("APP.SENT_MESSAGES", data));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        Content.addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Content.newUserIP.getText().matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
                    Contact newContact = new Contact(Content.newUserName.getText(), Content.newUserIP.getText());
                    try {
                        if (newContact.save()){
                            Content.newUserIP.setText("");
                            Content.newUserName.setText("");
                            Content.users.add(newContact.name);
                        }
                    } catch (SQLException e1) {
                        Content.showMessage(e1.getMessage());
                    }
                }else{
                    Content.showMessage("Error: The address must be in standard IP format like(192.168.101.111) \n Please try again");
                }
            }
        });
    }
    
    public static void run(){
        populateContent();
    }
}
