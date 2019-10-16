package database;

import java.io.File;
import java.sql.*;
import static java.util.Collections.list;

/**
 *
 * @author Yun_c
 */
public class Database {

    private Connection conn;
    private DatabaseMetaData meta;
    private String username;
    private String password;
    private static final String CONFIG = "vocabulary_config";
    private Statement controller;

    /**
     * @param args the command line arguments
     */
    public Database(String host, String user, String pass) {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            this.conn = DriverManager.getConnection(host);
            this.controller = this.conn.createStatement();
            this.meta = this.conn.getMetaData();
            System.out.println("connect success");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init() {
        try {
            File[] files = this.getConfigFiles();
            
            if(files == null || files.length == 0){
                System.out.println("No config file found");
                return;
            }
            
            for(File f:files){
                this.checkAndCreate(f);
            }
//            ResultSet tables = meta.getTables(conn.getCatalog(), null, "user_info", null);
//            if (!tables.next()) {
//                // table doesnt exist so create it
//            }

        } catch (Exception e) {

        }
    }

    private File[] getConfigFiles() {
        File file = new File(Database.CONFIG);
        File[] files = file.listFiles();
        return files;
    }

    private void checkAndCreate(File f) throws SQLException {
        String table_name = f.getName();
        System.out.println("Config file found: " + table_name);
        table_name = table_name.substring(0, table_name.lastIndexOf("."));
        String file_dir = Database.CONFIG + f.getName();
        ResultSet tables = meta.getTables(conn.getCatalog(), null, table_name, null);
        if (!tables.next()) {
            //TODO: create the table
            String sql_str = "create ";
        }

    }

    public ResultSet get(String table_name, String key, String condition) {
        String sql_str = "select * from " + table_name + " where " + key
                + " = \"" + condition + "\"";
        try {
            ResultSet res = this.controller.executeQuery(sql_str);
            return res;
        } catch (Exception e) {
            System.err.println("SQLException from getAllData: " + e.getMessage());
        }
        return null;
    }

    public boolean set(String table_name, String key, String condition,
            String column, String value) {
        String sql_str = "update " + table_name + " set " + column + " = \""
                + value + "\" where " + key + " = \"" + condition + "\"";
        try {
            ResultSet res = this.controller.executeQuery(sql_str);
            return true;
        } catch (Exception e) {
            System.err.println("SQLException from getAllData: " + e.getMessage());
        }
        return false;
    }

    public boolean add(String table_name, String[] columns, String[] values) {
        String sql_str = "insert into " + table_name + " (";
        for (int i = 0; i < columns.length; i++) {
            sql_str += columns[i];
            if (i == columns.length - 1) {
                break;
            }
            sql_str += ",";
        }
        sql_str += ") values (";

        for (int i = 0; i < values.length; i++) {
            sql_str += "\"" + values[i] + "\"";
            if (i == columns.length - 1) {
                break;
            }
            sql_str += ",";
        }
        try {
            ResultSet res = this.controller.executeQuery(sql_str);
            return true;
        } catch (Exception e) {
            System.err.println("SQLException from getAllData: " + e.getMessage());
        }
        return false;
    }

    public boolean delete(String table_name, String key, String condition) {
        String sql_str = "delete from " + table_name + " where " + key
                + " = \"" + condition + "\"";
        try {
            ResultSet res = this.controller.executeQuery(sql_str);
            return true;
        } catch (Exception e) {
            System.err.println("SQLException from getAllData: " + e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        Database t = new Database("jdbc:derby:Memorize_helper;create=true",
                "root", "root");
        t.init();
//        ResultSet res = t.get("student", "name", "黄平川");
//        try{
//            while(res.next()){
//                System.out.println(res.getString("name"));
//                System.out.println(res.getString("gender"));
//            }
//        }catch(Exception e){
//            
//        }
    }

}
