package controllers;

import views.Content;
import views.Header;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by adrian on 18.02.2015.
 */
public class HeaderController {
    public static void populateContent(){
        Header.inboxB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Content.bodyLayout.show(Content.body,"inbox");
            }
        }); 
        Header.contactsB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Content.bodyLayout.show(Content.body,"contacts");
            }
        });
        Header.composeB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Content.bodyLayout.show(Content.body,"compose");
            }
        });
    }
    
    public static void run(){
        populateContent();
        
    }
}
