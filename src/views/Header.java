package views;

import java.awt.*;

/**
 * Created by adrian on 18.02.2015.
 */
public class Header {
    public static Button inboxB,contactsB,composeB;
    public static Panel createHeader(){
        LayoutManager headerLayout = new FlowLayout(FlowLayout.LEFT);
        Panel header = new Panel();
        header.setLayout(headerLayout);
        inboxB = new Button("INBOX");
        header.add(inboxB);
        contactsB = new Button("CONTACTS");
        header.add(contactsB);
        composeB = new Button("COMPOSE");
        header.add(composeB);
        return header;
    }
}
