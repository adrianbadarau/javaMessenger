package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by adrian on 18.02.2015.
 */
public class Content {
    public static Panel body,inboxP,contactsP,composeP;
    public static Button sendMessage,addUser;
    public static TextArea messageBody;
    public static Label usersInfo,showConnection,showIP,newUserNameL,newUserIpL;
    public static TextField newUserName,newUserIP;
    public static Choice users;
    public static CardLayout bodyLayout;
    
    public static Panel createContent(){
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
        
        
        composeP = new Panel();
        body.add(inboxP,"inbox");
        body.add(contactsP,"contacts");
        body.add(composeP,"compose");
        bodyLayout.show(body,"compose");
        
        usersInfo = new Label("Select User");
        users = new Choice();
//        Populate with dummy data
        users.add("Gigel");
        users.add("Fanel");
        showIP = new Label("192.168.5.10:5050");
        showConnection = new Label("Connecting..");
        messageBody = new TextArea();
        sendMessage = new Button("SendMessage");
        addElementTo(composeP,usersInfo,users,messageBody,sendMessage,showConnection,showIP);
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
