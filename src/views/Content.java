package views;

import models.Contact;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by adrian on 18.02.2015.
 */
public class Content {
    public static Panel body,inboxP,contactsP,composeP,contactsIndexP;
    public static Button sendMessage,addUser;
    public static TextArea messageBody;
    public static Label usersInfo,showConnection,showIP,newUserNameL,newUserIpL,messageTitleL,contactNameL;
    public static TextField newUserName,newUserIP,messageTitle;
    public static Choice users;
    public static CardLayout bodyLayout;
    
    public static Panel createContent(){
        ArrayList<Contact> contacts = null;
        try {
            contacts = Contact.all();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        body = new Panel();
        bodyLayout = new CardLayout();
        body.setLayout(bodyLayout);
        inboxP = new Panel();
        LayoutManager inboxLayout = new GridLayout(0,1,10,10);
        inboxP.setLayout(inboxLayout);
        for(int i=0;i<10;i++){
            final Button message = new Button("Message"+i+"Title");
            inboxP.add(message);
            message.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showMessage("hello world!"+message.getLabel());
                }
            });
        }
        contactsP = new Panel();
        newUserNameL = new Label("Numele Contactului");
        newUserIpL = new Label("Adressa IP a contactului");
        newUserName = new TextField(10);
        newUserIP = new TextField(10);
        addUser = new Button("Add Contact");
        addElementTo(contactsP,newUserNameL,newUserName,newUserIpL,newUserIP,addUser);


        contactsIndexP = new Panel();
        LayoutManager contactsIndexLayout = new GridLayout(0,3,10,10);
        contactsIndexP.setLayout(contactsIndexLayout);
        for (Contact contact : contacts){
            contactNameL = new Label(contact.name);
            TextField address = new TextField(contact.address);
            Button button= new Button("SAVE");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (address.getText().matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")){
                        contact.address = address.getText();
                        try {
                            contact.update();
                            Content.showMessage("Contact "+contact.name+" successfully updated");
                        } catch (SQLException e1) {
                            Content.showMessage(e1.getMessage());
                        }
                    }else {
                        Content.showMessage("Invalid IP, please try again");
                    }
                }
            });
            addElementTo(contactsIndexP,contactNameL,address,button);
        }

        
        composeP = new Panel();
        usersInfo = new Label("Select User");
        users = new Choice();
        for(Contact contact:contacts) {
            users.add(contact.name);
        }
        showIP = new Label("Your IP address");
        showConnection = new Label("Idle");
        messageTitleL = new Label("Title:");
        messageTitle = new TextField(20);
        messageBody = new TextArea();
        sendMessage = new Button("SendMessage");
        addElementTo(composeP,usersInfo,users,messageTitleL,messageTitle,messageBody,sendMessage,showConnection,showIP);

        body.add(inboxP,"inbox");
        body.add(contactsP,"contacts");
        body.add(composeP,"compose");
        body.add(contactsIndexP,"contactsIndex");
        bodyLayout.show(body,"compose");
        return body;
    }

    public static void showMessage(String text) {
        final Frame messageBody = new Frame();
        Label cont = new Label(text);
        messageBody.add(cont);
        messageBody.setSize(200,200);
        messageBody.setVisible(true);
        messageBody.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                messageBody.setVisible(false);
            }
        });
    }

    public static Panel addElementTo(Panel container, Component... elems){
        for(Component elem:elems){
            container.add(elem);
        }
        return container;
    }
}
