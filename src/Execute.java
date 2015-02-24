import controllers.*;
import server.DataBaseConnection;
import server.MyServer;
import views.*;

import java.net.SocketException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by adrian on 18.02.2015.
 */
public class Execute {
    public static DataBaseConnection con;
    public static MyServer server;
    public static Thread dbThread,srvThread;
    public static void main(String[] args) {
        MainWindow.init();
        MainWindowController.run();
        ContentController.run();
        HeaderController.run();
        server = new MyServer();
        try {
            server.run();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        con = new DataBaseConnection();
        dbThread = new Thread(con);
        dbThread.run();
//        HashMap<String,String> doSql = new HashMap<>();
//        doSql.put("CONTACT_ID","33");
//        doSql.put("TITLE","TESTSQL");
//        doSql.put("BODY","LALALALALAALALALA");
//        doSql.put("SENT_DATE",DataBaseConnection.generateTimeStamp());
//        try {
//            System.out.println(con.insert("APP.SENT_MESSAGES",doSql));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
