package server;

import java.sql.*;
import java.util.Date;
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
    }

    public static boolean insert(String table, HashMap<String,String> values) throws SQLException {
        dbCon  = DriverManager.getConnection("jdbc:derby:LocalDB;create=true");
        StringBuilder sql = new StringBuilder();
        StringBuilder vars = new StringBuilder();
        sql.append("INSERT INTO "+table+" (");
//        vars.append("(");
        for(Map.Entry<String, String> value :values.entrySet()){
            sql.append(value.getKey()+",");
            if( value.getKey().matches("[a-zA-Z]+_ID")){
                vars.append(value.getValue()+",");
            }else{
                vars.append("'" + value.getValue() + "',");
            }
        }
        sql.deleteCharAt(sql.length()-1);
        vars.deleteCharAt(vars.length()-1);
        sql.append(") VALUES (" + vars.toString() + ")");
        Statement query = dbCon.createStatement();
        boolean rez = query.execute(sql.toString());
//        dbCon.close();
        return rez;
    }
    public static ResultSet getAll(String table) throws SQLException {
        dbCon = DriverManager.getConnection("jdbc:derby:LocalDB;create=true");
        Statement sql = dbCon.createStatement();
        sql.executeQuery("SELECT * FROM "+table);
        ResultSet out = sql.getResultSet();
//        dbCon.close();
        return out;
    }
    public static ResultSet find(String table,int id) throws SQLException {
        dbCon = DriverManager.getConnection("jdbc:derby:LocalDB;create=true");
        Statement sql = dbCon.createStatement();
        sql.executeQuery("SELECT * FROM "+table+" WHERE "+table+".ID="+id);
        ResultSet out = sql.getResultSet();
//        dbCon.close();
        return out;
    }
    public static ResultSet findWhere(String table,String colName,String value) throws SQLException {
        dbCon = DriverManager.getConnection("jdbc:derby:LocalDB;create=true");
        Statement sql = dbCon.createStatement();
        sql.executeQuery("SELECT * FROM "+table+" WHERE "+table+"."+colName+"='"+value+"'");
        ResultSet out = sql.getResultSet();
//        dbCon.close();
        return out;
    }
    public static boolean update(String table, HashMap<String,String> values, int id) throws SQLException {
        dbCon = DriverManager.getConnection("jdbc:derby:LocalDB;create=true");
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE "+table+" SET ");
        for(Map.Entry<String,String> value : values.entrySet() ){
            sql.append(value.getKey()+"='"+value.getValue()+"',");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(" WHERE "+table+".ID="+id);
        Statement query = dbCon.createStatement();
        boolean rez = query.execute(sql.toString());
//        dbCon.close();
        return !rez;
    }
    public static String generateTimeStamp(){
        Date now = new Date();
        String timeStamp = new Timestamp(now.getTime()).toString();
        return timeStamp;
    }
}
