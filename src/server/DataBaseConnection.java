package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by adib on 2/20/15.
 */
public class DataBaseConnection implements Runnable{
    public Connection dbCon;
    @Override
    public void run() {
        System.out.print("New thread");
        try  {
            dbCon = DriverManager.getConnection("jdbc:derby:LocalDB");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
