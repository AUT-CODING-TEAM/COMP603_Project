package database;

import java.io.File;
import java.sql.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yun_c
 */
public class Database {

    private volatile static Database instance = null;
    private Connection conn;
    private DatabaseMetaData meta;
    private String username;
    private String password;
    private static final String CONFIG = "vocabulary_config";
    private Statement controller;

    /**
     * @param args the command line arguments
     */
    private Database(String host, String user, String pass) {
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

    private Database() {
        this("jdbc:derby:Memorize_helper;create=true",
                "root", "root");
    }

    public static Database getInstance() {
        if (Database.instance == null) {
            synchronized (Database.class) {
                if (Database.instance == null) {
                    Database.instance = new Database();
                }
            }
        }
        return Database.instance;
    }

    public void init() {
        try {
            File[] files = this.getConfigFiles();

            if (files == null || files.length == 0) {
                System.out.println("No config file found");
                return;
            }
            String[] type = {"TABLE"};
            ResultSet user_table = this.meta.getTables(null, null, "USERS", null);
            if (!user_table.next()) {
                String str1 = "create table users\n"
                        + "(\n"
                        + "	ID int generated always as identity,\n"
                        + "	username varchar(20) not null,\n"
                        + "	password long varchar not null\n"
                        + ")";
                String str2 = "create unique index users_ID_uindex\n"
                        + "	on users (ID)";

                String str3 = "create unique index users_username_uindex\n"
                        + "	on users (username)";
                String str4 = "alter table users\n"
                        + "	add constraint users_pk\n"
                        + "		primary key (ID)";

                this.controller.execute(str1);
                this.controller.execute(str2);
                this.controller.execute(str3);
                this.controller.execute(str4);
            }
            ResultSet mem_table = this.meta.getTables(null, null, "MEMORIZE", null);
            if (!mem_table.next()) {
                String str1 = "create table memorize\n"
                        + "(\n"
                        + "	ID int generated always as identity,\n"
                        + "	user_id varchar(5) not null,\n"
                        + "	word_id varchar(5) not null,\n"
                        + "	word_source varchar(20) not null,\n"
                        + "	correct int default 0 not null,\n"
                        + "	wrong int default 0 not null,\n"
                        + "	last_mem_time varchar(40) not null,\n"
                        + "	aging int default 0 not null\n"
                        + ")";
                String str2 = "create unique index memorize_ID_uindex\n"
                        + "	on memorize (ID)";
                String str3 = "alter table memorize\n"
                        + "	add constraint memorize_pk\n"
                        + "		primary key (ID)";
                this.controller.execute(str1);
                this.controller.execute(str2);
                this.controller.execute(str3);
            }

            for (File f : files) {
                this.checkAndCreate(f);
            }
//            ResultSet tables = meta.getTables(conn.getCatalog(), null, "user_info", null);
//            if (!tables.next()) {
//                // table doesnt exist so create it
//            }
            System.out.println("Done!");
        } catch (Exception e) {
            e.printStackTrace();
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
        ResultSet tables = meta.getTables(null, null, table_name.toUpperCase(), null);
        if (!tables.next()) {
            //TODO: create the table
            String sql_str1 = "create table " + table_name + "\n"
                    + "(\n"
                    + "	ID int generated always as identity,\n"
                    + "	word varchar(50) not null,\n"
                    + "	chinese varchar(200) not null,\n"
                    + "	phonetic varchar(50) not null\n"
                    + ")";
            String sql_str2 = "create unique index " + table_name + "_ID_uindex\n"
                    + "	on " + table_name + " (ID)";

            String sql_str3 = "create unique index " + table_name + "_word_uindex\n"
                    + "	on " + table_name + " (word)";

            String sql_str4 = "alter table " + table_name + "\n"
                    + "	add constraint " + table_name + "_pk\n"
                    + "		primary key (ID)";

            this.controller.execute(sql_str1);
            this.controller.execute(sql_str2);
            this.controller.execute(sql_str3);
            this.controller.execute(sql_str4);

            try {

                InputStreamReader isr = new InputStreamReader(new FileInputStream("vocabulary_config/" + f.getName()), "UTF-8");
                BufferedReader bf = new BufferedReader(isr);

                String line = "";
                while ((line = bf.readLine()) != null) {
                    if (line.equals("")) {
                        continue;
                    }
                    String[] info = line.split("\t");
                    System.out.println(info[0]);

                    String[] col = {"word", "phonetic", "chinese"};
                    info[1] = info[1].replace("'", "''");
                    if (info.length > 3) {
                        for (int i = 3; i < info.length; i++) {
                            info[2] += info[i];
                        }
                    }
                    info[2] = info[2].replace("'", "''");
                    this.add(table_name, col, info);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public ArrayList<String> getAllTable() {
        String[] type = {"TABLE"};
        ArrayList<String> result = new ArrayList<String>();
        try {
            ResultSet rs = this.meta.getTables(null, null, "%", type);
            while (rs.next()) {
                result.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public ResultSet get(String table_name, String key, String condition) {
        String sql_str = "select * from " + table_name.toUpperCase() + " where \""
                + key.toUpperCase() + "\" = \'" + condition + "\'";
        try {
            ResultSet res = this.controller.executeQuery(sql_str);
            return res;
        } catch (Exception e) {
            System.err.println("SQLException from method get: " + e.getMessage());
        }
        return null;
    }

    public ResultSet get(String table_name, String[] key, String[] condition) {
        StringBuilder str_bd = new StringBuilder("select * from ");
        str_bd.append(table_name.toUpperCase());
        str_bd.append(" where ");
        int index = 0;
        for (String k : key) {
            str_bd.append("\"");
            str_bd.append(k.toUpperCase());
            str_bd.append("\"").append(" = ").append("\'");
            str_bd.append(condition[index]);
            str_bd.append("\'");
            if (index != key.length - 1) {
                str_bd.append(" and ");
            }
            index += 1;
        }
        try {
            ResultSet res = this.controller.executeQuery(str_bd.toString());
            return res;
        } catch (Exception e) {
            System.err.println("SQLException from method get: " + e.getMessage());
        }
        return null;
    }

    public boolean set(String table_name, String[] key, String[] condition,
            String column, String value) {
        StringBuilder str_bd = new StringBuilder("update ");
        str_bd.append(table_name.toUpperCase()).append(" set \"");
        str_bd.append(column.toUpperCase()).append("\"").append(" = \'");
        str_bd.append(value).append("\'").append(" where ");
        int index = 0;
        for (String k : key) {
            str_bd.append("\"");
            str_bd.append(k.toUpperCase());
            str_bd.append("\"").append(" = ").append("\'");
            str_bd.append(condition[index]);
            str_bd.append("\'");
            if (index != key.length - 1) {
                str_bd.append(" and ");
            }
            index += 1;
        }
        try {
            boolean res = this.controller.execute(str_bd.toString());
            return res;
        } catch (Exception e) {
            System.err.println("SQLException from method get: " + e.getMessage());
        }
        return false;
    }
    public boolean set(String table_name, String[] key, String[] condition,
            String column, int value) {
        StringBuilder str_bd = new StringBuilder("update ");
        str_bd.append(table_name.toUpperCase()).append(" set \"");
        str_bd.append(column.toUpperCase()).append("\"").append(" = ");
        str_bd.append(value).append(" where ");
        int index = 0;
        for (String k : key) {
            str_bd.append("\"");
            str_bd.append(k.toUpperCase());
            str_bd.append("\"").append(" = ").append("\'");
            str_bd.append(condition[index]);
            str_bd.append("\'");
            if (index != key.length - 1) {
                str_bd.append(" and ");
            }
            index += 1;
        }
        try {
            boolean res = this.controller.execute(str_bd.toString());
            return res;
        } catch (Exception e) {
            System.err.println("SQLException from method get: " + e.getMessage());
        }
        return false;
    }

    public boolean set(String table_name, String key, String condition,
            String column, String value) {
        String sql_str = "update " + table_name.toUpperCase() + " set \""
                + column.toUpperCase() + "\" = \'"
                + value + "\' where \"" + key.toUpperCase() + "\" = \'"
                + condition + "\'";

        try {
            boolean res = this.controller.execute(sql_str);
            return res;
        } catch (Exception e) {
            System.err.println("SQLException from method set: " + e.getMessage());
        }
        return false;
    }
    public boolean set(String table_name, String key, String condition,
            String column, int value) {
        String sql_str = "update " + table_name.toUpperCase() + " set \""
                + column.toUpperCase() + "\" = \'"
                + value + "\' where \"" + key.toUpperCase() + "\" = "
                + condition;

        try {
            boolean res = this.controller.execute(sql_str);
            return res;
        } catch (Exception e) {
            System.err.println("SQLException from method set: " + e.getMessage());
        }
        return false;
    }

    public boolean add(String table_name, String[] columns, String[] values) {
        StringBuilder str_bd = new StringBuilder("insert into ");
        str_bd.append(table_name.toUpperCase());
        str_bd.append(" (");
        for (int i = 0; i < columns.length; i++) {
            str_bd.append(" \"");
            str_bd.append(columns[i].toUpperCase());
            str_bd.append("\"");
            if (i == columns.length - 1) {
                break;
            }
            str_bd.append(",");
        }
        str_bd.append(") values (");

        for (int i = 0; i < columns.length; i++) {
            if (i >= values.length) {
                System.out.println(values.toString());
            }
            str_bd.append(" \'");
            str_bd.append(values[i]);
            str_bd.append("\'");
            if (i == columns.length - 1) {
                break;
            }
            str_bd.append(",");
        }
        str_bd.append(")");
        try {
            boolean res = this.controller.execute(str_bd.toString());
            return true;
        } catch (Exception e) {
            System.out.println(str_bd.toString());
            System.err.println("SQLException from method add: " + e.getMessage());
        }
        return false;
    }

    public boolean delete(String table_name, String key, String condition) {
        String sql_str = "delete from " + table_name.toUpperCase() + " where \""
                + key.toUpperCase() + "\" = \'" + condition + "\'";
        try {
            boolean res = this.controller.execute(sql_str);
            return res;
        } catch (Exception e) {
            System.err.println("SQLException from method delete: " + e.getMessage());
        }
        return false;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws SQLException {
        Database t = Database.getInstance();
        t.init();
        ResultSet res = t.get("MEMORIZE", "last_mem_time", "123456");
        if (res.next()) {
            int id = res.getInt("LAST_MEM_TIME");
            System.out.println(id);
        }
//        }
    }

}
