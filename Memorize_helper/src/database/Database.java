package database;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Yun_c
 */
public class Database {
    private Connection conn;
    private String username;
    private String password;
    
    private Statement controller;
    /**
     * @param args the command line arguments
     */
    
    public Database(String host, String user, String pass){
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            Properties props = new Properties();
            props.put("user",user);
            props.put("password",pass);
            this.conn = DriverManager.getConnection(host,props);
            this.controller = this.conn.createStatement();
            System.out.println("success");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public ResultSet get(String table_name, String key, String condition){
        String sql_str = "select * from `" + table_name + "` where " + key
                            + " = \"" + condition + "\"";
        try{
            ResultSet res = this.controller.executeQuery(sql_str);
            return res;
        }catch(Exception e){
            System.err.println("SQLException from method get: " + e.getMessage());
        }
        return null;
    }
    public boolean set(String table_name, String key, String condition,
            String column, String value){
        String sql_str = "update " + table_name + " set `" + column + "` = \"" +
                        value + "\" where `" + key + "` = \"" + condition + "\"";
        try{
            ResultSet res = this.controller.executeQuery(sql_str);
            return true;
        }catch(Exception e){
            System.err.println("SQLException from method set: " + e.getMessage());
        }
        return false;
    }
    public boolean add(String table_name, String[] columns, String[] values){
        String sql_str = "insert into " + table_name + " (";
        for(int i=0;i<columns.length;i++){
            sql_str += "`" + columns[i] + "`";
            if(i == columns.length -1){
                break;
            }
            sql_str += ",";
        }
        sql_str += ") values (";
        
        for(int i=0;i<values.length;i++){
            sql_str += "\"" + values[i] + "\"";
            if(i == columns.length -1){
                break;
            }
            sql_str += ",";
        }
        try{
            ResultSet res = this.controller.executeQuery(sql_str);
            return true;
        }catch(Exception e){
            System.err.println("SQLException from method add: " + e.getMessage());
        }
        return false;
    }
    
    public boolean delete(String table_name, String key, String condition){
        String sql_str = "delete from " + table_name + " where " + key
                            + " = \"" + condition + "\"";
        try{
            ResultSet res = this.controller.executeQuery(sql_str);
            return true;
        }catch(Exception e){
            System.err.println("SQLException from method delete: " + e.getMessage());
        }
        return false;
    }
    
    
    public static void main(String[] args) {
        Database t = new Database("jdbc:derby:Memorize_helper;create=true",
                "root","root");
        
//        ResultSet res = t.get("student", "name", "黄平川");
//        try{
//            while(res.next()){
//                System.out.println(res.getString("name"));
//                System.out.println(res.getString("gender"));
//            }
//        }catch(Exception e){
//            
//        }

//test
        
    }
    
}
