import controllers.*;
import server.DataBaseConnection;
import server.MyServer;
import views.*;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by adrian on 18.02.2015.
 */
public class Execute {
    public static void main(String[] args) {
        MainWindow.init();
        MainWindowController.run();
        ContentController.run();
        HeaderController.run();
        MyServer server = new MyServer();
        Thread thread = new Thread(server);
        thread.start();
        DataBaseConnection con = new DataBaseConnection();
        Thread dbThread = new Thread(con);
        dbThread.run();
        HashMap<String,String> doSql = new HashMap<>();
        doSql.put("CONTACT_ID","33");
        doSql.put("TITLE","'TESTSQL'");
        doSql.put("BODY","'LALALALALAALALALA'");
        try {
            System.out.println(con.insert("APP.SENT_MESSAGES",doSql));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
