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
                        + "	password long varchar not null,\n"
                        + "	study_plan varchar(20) not null\n"
                        + ")";
                String str2 = "create unique index users_ID_uindex\n"
                        + "	on users (ID)";

                String str3 = "create unique index users_username_uindex\n"
                        + "	on users (username)";
                String str4 = "alter table users\n"
                        + "	add constraint users_pk\n"
                        + "		primary key (ID)";

                this.controller.executeUpdate(str1);
                this.controller.executeUpdate(str2);
                this.controller.executeUpdate(str3);
                this.controller.executeUpdate(str4);
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
                this.controller.executeUpdate(str1);
                this.controller.executeUpdate(str2);
                this.controller.executeUpdate(str3);
            }
            ResultSet plan_table = this.meta.getTables(null, null, "PLAN", null);
            if (!plan_table.next()) {
                String str1 = "create table plan\n"
                        + "(\n"
                        + "	ID int generated always as identity,\n"
                        + "	user_id varchar(10) not null,\n"
                        + "	book varchar(20) not null,\n"
                        + "	total_day int not null,\n"
                        + "	start_time bigint not null,\n"
                        + "	today_target_number int not null,\n"
                        + "	finish varchar(1) not null\n"
                        + ")";
                String str2 = "create unique index plan_ID_uindex\n"
                        + "	on plan (ID)";
                String str3 = "alter table plan\n"
                        + "	add constraint plan_pk\n"
                        + "		primary key (ID)";

                this.controller.executeUpdate(str1);
                this.controller.executeUpdate(str2);
                this.controller.executeUpdate(str3);
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

            this.controller.executeUpdate(sql_str1);
            this.controller.executeUpdate(sql_str2);
            this.controller.executeUpdate(sql_str3);
            this.controller.executeUpdate(sql_str4);

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
                    info[0] = info[0].replace("'", "''");
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

    public int count(String table_name, String key, String condition) {
//        String sql_str = "select count(*) as \"NUMBER\" from " + table_name.toUpperCase();
        StringBuilder strbd = new StringBuilder("select count(*) as \"NUMBER\" from ");
        strbd.append(table_name.toUpperCase());
        int num = 0;
        if (!key.equals("")) {
            strbd.append(" where ").append(key.toUpperCase()).append(" = ?");
        }
        try {
            PreparedStatement pst = this.conn.prepareStatement(strbd.toString());
            if (!key.equals("")) {
                pst.setString(1, condition);
            }
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                num = res.getInt("NUMBER");
            }
        } catch (Exception e) {
            System.err.println("SQLException from method get: " + e.getMessage());
        }
        return num;
    }

    public int count(String table_name) {
        StringBuilder strbd = new StringBuilder("select count(*) as \"NUMBER\" from ");
        strbd.append(table_name.toUpperCase());
        int num = 0;
        try {
            PreparedStatement pst = this.conn.prepareStatement(strbd.toString());
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                num = res.getInt("NUMBER");
            }
        } catch (Exception e) {
            System.err.println("SQLException from method get: " + e.getMessage());
        }
        return num;
    }

    public int count(String table_name, String[] key, String[] condition) {
        int num = 0;
        StringBuilder bd = new StringBuilder("select count(*) as NUMBER from \"");
        bd.append(table_name.toUpperCase()).append("\"").append(" where ");
        for (int i = 0; i < key.length; i++) {
            bd.append("\"").append(key[i].toUpperCase()).append("\"").append(" = ");
            bd.append("\'").append(condition[i]).append("\'");
            if (i < key.length - 1) {
                bd.append(" and ");
            }
        }
        try {
            ResultSet res = this.controller.executeQuery(bd.toString());
            if (res.next()) {
                num = res.getInt("NUMBER");
            }
        } catch (Exception e) {
            System.err.println("SQLException from method get: " + e.getMessage());
        }
        return num;
    }

    public ResultSet get(String table_name, String key, String condition) {
//        String sql_str = "select * from " + table_name.toUpperCase();
//
//        if (!key.equals("")) {
//            sql_str += " where \"" + key.toUpperCase() + "\" = \'" + condition + "\'";
//        }
        StringBuilder strbd = new StringBuilder("select * from ");
        strbd.append(table_name.toUpperCase());
        if (!key.equals("")) {
            strbd.append(" where ");
            strbd.append(key.toUpperCase()).append(" = ?");
        }

        try {
            PreparedStatement pst = this.conn.prepareStatement(strbd.toString());
            if (!key.equals("")) {
                pst.setString(1, condition);
            }
            ResultSet res = pst.executeQuery();
            return res;
        } catch (Exception e) {
            System.err.println("SQLException from method get: " + e.getMessage());
        }
        return null;
    }

    public ResultSet get(String table_name, String key, int condition) {
//        String sql_str = "select * from " + table_name.toUpperCase();
//
//        if (!key.equals("")) {
//            sql_str += " where \"" + key.toUpperCase() + "\" = " + condition;
//        }
        StringBuilder strbd = new StringBuilder("select * from ");
        strbd.append(table_name.toUpperCase());
        if (!key.equals("")) {
            strbd.append(" where ");
            strbd.append(key.toUpperCase()).append(" = ?");
        }

        try {
            PreparedStatement pst = this.conn.prepareStatement(strbd.toString());
            if (!key.equals("")) {
                pst.setInt(1, condition);
            }
            ResultSet res = pst.executeQuery();
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

    public ResultSet search(String table_name, String key, String condition) {
        StringBuilder strbd = new StringBuilder("select * from ");
        strbd.append(table_name.toUpperCase()).append(" where ").append("\"");
        strbd.append(key.toUpperCase()).append("\"").append(" like ?");
        ResultSet res = null;
        try {
            PreparedStatement pst = this.conn.prepareStatement(strbd.toString());
            pst.setString(1, condition + "%");
            res = pst.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public ResultSet SQLqr(String sql) {
        ResultSet res = null;
        try {
            res = this.controller.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public boolean SQL(String sql) {
        boolean res = false;
        try {
            this.controller.execute(sql);
            res = true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
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
            int res = this.controller.executeUpdate(str_bd.toString());
            return res != 0;
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
            int res = this.controller.executeUpdate(str_bd.toString());
            return res != 0;
        } catch (Exception e) {
            System.err.println("SQLException from method get: " + e.getMessage());
        }
        return false;
    }

    public boolean set(String table_name, String key, String condition,
            String column, String value) {
        StringBuilder strbd = new StringBuilder("update ");
        strbd.append(table_name.toUpperCase()).append(" set ").append(column.toUpperCase());
        strbd.append(" = ? where ").append(key.toUpperCase()).append(" = ?");
        try {
            PreparedStatement pst = this.conn.prepareStatement(strbd.toString());
            pst.setString(1, value);
            pst.setString(2, condition);
            int res = pst.executeUpdate();
            return res != 0;
        } catch (Exception e) {
            System.err.println("SQLException from method set: " + e.getMessage());
        }
        return false;
    }

    public boolean set(String table_name, String key, int condition,
            String column, String value) {
        StringBuilder strbd = new StringBuilder("update ");
        strbd.append(table_name.toUpperCase()).append(" set ").append(column.toUpperCase());
        strbd.append(" = ? where ").append(key.toUpperCase()).append(" = ?");
        try {
            PreparedStatement pst = this.conn.prepareStatement(strbd.toString());
            pst.setString(1, value);
            pst.setInt(2, condition);
            int res = pst.executeUpdate();
            return res != 0;
        } catch (Exception e) {
            System.err.println("SQLException from method set: " + e.getMessage());
        }
        return false;
    }

    public boolean set(String table_name, String key, String condition,
            String column, int value) {
//        String sql_str = "update " + table_name.toUpperCase() + " set \""
//                + column.toUpperCase() + "\" = \'"
//                + value + "\' where \"" + key.toUpperCase() + "\" = "
//                + condition;
        StringBuilder strbd = new StringBuilder("update ");
        strbd.append(table_name.toUpperCase()).append(" set ").append(column.toUpperCase());
        strbd.append(" = ? where ").append(key.toUpperCase()).append(" = ?");
        try {
            PreparedStatement pst = this.conn.prepareStatement(strbd.toString());
            pst.setInt(1, value);
            pst.setString(2, condition);
            int res = pst.executeUpdate();
            return res != 0;
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
            int res = this.controller.executeUpdate(str_bd.toString());
            return res != 0;
        } catch (Exception e) {
            System.out.println(str_bd.toString());
            System.err.println("SQLException from method add: " + e.getMessage());
        }
        return false;
    }

    /**
     *
     * @param table_name The name of target table.
     *
     * @param columns Columns hold the data inserted.
     *
     * @param values values[i] holds each lines of record. values[i][j] holds
     * data of each column.
     *
     * @return True : Succeed, False : Failed.
     */
    public boolean add(String table_name, String[] columns, String[][] values) {
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
        str_bd.append(") values ");
        for (int i = 0; i < values.length; i++) {
            str_bd.append("(");
            for (int j = 0; j < columns.length; j++) {
                if (j >= values[i].length) {
                    System.out.println(values.toString());
                }
                str_bd.append(" \'");
                str_bd.append(values[i][j]);
                str_bd.append("\'");
                if (j == columns.length - 1) {
                    break;
                }
                str_bd.append(",");
            }
            str_bd.append(")");
            if (i < values.length - 1) {
                str_bd.append(",");
            }
        }
        try {
            int res = this.controller.executeUpdate(str_bd.toString());
            return res != 0;
        } catch (Exception e) {
            System.out.println(str_bd.toString());
            System.err.println("SQLException from method add: " + e.getMessage());
        }
        return false;
    }

    public boolean delete(String table_name, String key, String condition) {
//        String sql_str = "delete from " + table_name.toUpperCase() + " where \""
//                + key.toUpperCase() + "\" = \'" + condition + "\'";
        StringBuilder strbd = new StringBuilder("delete from ");
        strbd.append(table_name.toUpperCase());
        strbd.append(" where ").append(key.toUpperCase()).append(" = ?");
        try {
            PreparedStatement pst = this.conn.prepareStatement(strbd.toString());
            pst.setString(1, table_name.toUpperCase());
            pst.setString(2, key.toUpperCase());
            pst.setString(3, condition);
            int res = pst.executeUpdate();
            return res != 0;
        } catch (Exception e) {
            System.err.println("SQLException from method delete: " + e.getMessage());
        }
        return false;
    }

    public void reset() {
        ArrayList<String> table = this.getAllTable();
        for (String str : table) {
            try {
                this.controller.executeUpdate("drop table " + str.toUpperCase());
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        t.reset();
        t.init();
//        String[] col = {
//            "username",
//            "password",
//            "study_plan"
//        };
//        String[][] values = {
//            {
//                "yyz",
//                "yyz_pw",
//                "1"
//            },
//            {
//                "hpc",
//                "hpc_pw",
//                "2"
//            }
//        };
//        t.add("users", col, values);
    }
}
