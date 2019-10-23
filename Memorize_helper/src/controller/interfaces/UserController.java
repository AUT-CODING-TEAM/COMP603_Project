/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import database.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

/**
 *
 * @author Yun_c
 */
public class UserController {

    /**
     * @param username user's username
     * @return the instance of user if user exist
     */
    public User getUser(String username) {
        Database db = Database.getInstance();
        ResultSet res = db.get("USERS", "USERNAME", username);
        User user = null;
        try {
            if (res.next()) {
                String name = res.getString("USERNAME");
                String pass = res.getString("PASSWORD");
                int id = res.getInt("ID");
                user = new User(name, pass, id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    /**
     * @param username new user's username
     * @param password new user's password
     * @return the success situation of register
     */
    public boolean register(String username, String password) {
        Database db = Database.getInstance();
        SHA256Util sha256 = new SHA256Util();
        String seq_pass = sha256.SHA256(String.valueOf(password));
        String[] col = {"username", "password"};
        String[] val = {username, seq_pass};
        boolean res = db.add("users", col, val);
        return res;
    }

    /**
     * @param user the instance of user
     * @param password the raw password which was input by user
     * @return if the password correct
     */
    public boolean checkPassword(User user, String password) {
        SHA256Util sha256 = new SHA256Util();
        String seq_pass = sha256.SHA256(password);
        if (seq_pass.equals(user.getSeqPassword())) {
            return true;
        }
        return false;
    }

    /**
     * @param username user's username
     * @param password the raw password which was input by user
     * @return if the password correct
     */
    public boolean checkPassword(String username, char[] password) {
        String str = String.valueOf(password);
        return this.checkPassword(username, str);
    }

    /**
     * @param username user's username
     * @param password the raw password which was input by user
     * @return if the password correct
     */
    public boolean checkPassword(String username, String password) {
        Database db = Database.getInstance();
        SHA256Util sha256 = new SHA256Util();
        String seq_pass = sha256.SHA256(password);
        String[] keys = {"username", "password"};
        String[] vals = {username, seq_pass};
        ResultSet res = db.get("users", keys, vals);
        try {
            if (res.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * @param username user's username
     * @param word the word wait to be memorized
     * @param source the word belong to which source (eg. CET4 or CET6 or IELTS)
     * @return if a new memorize clause added into database
     */
    public boolean memorize(String username, String word, String source) {
        Database db = Database.getInstance();
        try {
            ResultSet user = db.get("users", "username", username);
            if (!user.next()) {
                return false;
            }
            String user_id = user.getString("ID");
            
            ResultSet wd = db.get(source, "word", word);
            if (!wd.next()) {
                return false;
            }
            String word_id = wd.getString("ID");

            return this.memorize(
                    Integer.valueOf(user_id),
                    Integer.valueOf(word_id),
                    source
            );

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * @param userid the user's ID
     * @param wordid the word's ID
     * @param source the word belong to which source (eg. CET4 or CET6 or IELTS)
     * @return if a new memorize clause added into database
     */
    public boolean memorize(int userid, int wordid, String source) {
        Database db = Database.getInstance();
        String[] col = {"user_id", "word_id", "word_source"};
        String[] val = {
            String.valueOf(userid),
            String.valueOf(wordid),
            source
        };

        ResultSet check = db.get("memorize", col, val);
        try {
            if (check.next()) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] col2 = {"user_id", "word_id", "word_source", "last_mem_time"};
        String[] val2 = {
            String.valueOf(userid),
            String.valueOf(wordid),
            source,
            String.valueOf(System.currentTimeMillis())
        };
        boolean res = db.add("memorize", col2, val2);
        return res;

    }

    /**
     * @param user the instance of class User
     * @param wd the instance of class Word
     * @return if a new memorize clause added into database
     */
    public boolean memorize(User user, Word wd) {
        return this.memorize(user.getID(), wd.getID(), wd.getSource());
    }

    /**
     * @param user the instance of class User
     * @param wd the instance of class Word
     * @return if the correct count of this user memorize this word is added
     */
    public boolean correct(User user, Word wd) {
        Database db = Database.getInstance();
        MemorizeController mct = new MemorizeController();
        return mct.correct(user, wd);
    }

    /**
     * @param user the instance of class User
     * @param wd the instance of class Word
     * @return if the wrong count of this user memorize this word is added
     */
    public boolean wrong(User user, Word wd) {
        Database db = Database.getInstance();
        MemorizeController mct = new MemorizeController();
        return mct.wrong(user, wd);
    }

    /**
     * help build sha256 string
     */
    class SHA256Util {

        public String SHA256(String str) {
            MessageDigest messageDigest;
            String encodeStr = "";
            try {
                messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(str.getBytes("UTF-8"));
                encodeStr = byte2Hex(messageDigest.digest());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return encodeStr;
        }

        private String byte2Hex(byte[] bytes) {
            StringBuffer stringBuffer = new StringBuffer();
            String temp = null;
            for (int i = 0; i < bytes.length; i++) {
                temp = Integer.toHexString(bytes[i] & 0xFF);
                if (temp.length() == 1) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(temp);
            }
            return stringBuffer.toString();
        }
    }

    public static void main(String[] args) {
        UserController uct = new UserController();
        WordController wct = new WordController();
        System.out.println(uct.register("test", "test"));
        User user = uct.getUser("yyz");
        System.out.println(uct.checkPassword(user, "123456"));
        System.out.println(uct.checkPassword(user, "456789"));
        Word wd = wct.getBookWordByName("cet4", "able");
        System.out.println(uct.memorize(user, wd));
        boolean bl = uct.memorize(user.getUsername(), wd.getWord(), wd.getSource());
        System.out.println(bl);
        uct.correct(user, wd);
        uct.wrong(user, wd);
    }
}
