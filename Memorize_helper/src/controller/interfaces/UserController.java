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
import model.User;

/**
 *
 * @author Yun_c
 */
public class UserController {

    public User getUser(String username) {
        Database db = Database.getInstance();
        ResultSet res = db.get("USERS", "USERNAME", username);
        User user = null;
        try {
            if (res.next()) {
                String name = res.getString("USERNAME");
                String pass = res.getString("PASSWORD");
                user = new User(name, pass);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public boolean register(String username, String password) {
        Database db = Database.getInstance();
        SHA256Util sha256 = new SHA256Util();
        String seq_pass = sha256.SHA256(String.valueOf(password));
        String[] col = {"username", "password"};
        String[] val = { username, seq_pass};
        boolean res = db.add("users",col, val);
        return res;
    }

    public boolean checkPassword(User user, String password) {

        SHA256Util sha256 = new SHA256Util();
        String seq_pass = sha256.SHA256(password);
        if (seq_pass.equals(user.getSeqPassword())) {
            return true;
        }
        return false;
    }

    public boolean checkPassword(String username, char[] password) {
        String str = String.valueOf(password);
        return this.checkPassword(username, str);
    }

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
}
