package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adib on 2/20/15.
 */
public class DataBaseConnection implements Runnable{
    public static Connection dbCon;
    @Override
    public void run() {
        System.out.print("New thread");
//        try  {
//            dbCon = DriverManager.getConnection("jdbc:derby:LocalDB");
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try(Connection cn = DriverManager.getConnection("jdbc:derby:LocalDB;create=true")) {
//            dbCon = cn;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public boolean insert(String table, HashMap<String,String> values) throws SQLException {
        dbCon  = DriverManager.getConnection("jdbc:derby:LocalDB;create=true");
        StringBuilder sql = new StringBuilder();
        StringBuilder vars = new StringBuilder();
        sql.append("INSERT INTO "+table+" (");
//        vars.append("(");
        for(Map.Entry<String, String> value :values.entrySet()){
            sql.append(value.getKey()+",");
            vars.append(value.getValue()+",");
        }
        sql.deleteCharAt(sql.length()-1);
        vars.deleteCharAt(vars.length()-1);
        sql.append(") VALUES (" + vars.toString() + ")");
        Statement query = dbCon.createStatement();
        boolean rez = query.execute(sql.toString());
        dbCon.close();
        return rez;

    }
}
