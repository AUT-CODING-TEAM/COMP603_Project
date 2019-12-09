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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
        PlanController pct = new PlanController();

        ResultSet res = db.get("USERS", "USERNAME", username);
        User user = null;
        try {
            if (res.next()) {
                String name = res.getString("USERNAME");
                String pass = res.getString("PASSWORD");
                int id = res.getInt("ID");
                int pid = res.getInt("STUDY_PLAN");
                user = new User(name, pass, id);
                user.setCurrentStudyPlan(pct.getPlan(pid));
            }
            pct.updateTodayPlanInfo(user);

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
        String[] col = {"username", "password", "study_plan"};
        String[] val = {username, seq_pass, "0"};
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
     * @return the rank list of memorize number
     */
    public final ArrayList<User> getMemorizeRank(){
        MemorizeController mct = new MemorizeController();
        ArrayList<User> arr = new ArrayList<User>();
        Map<String,Integer> datas = mct.getAllUserMemorizedNum();
        for(Entry<String, Integer> dt : datas.entrySet()){
            if(arr.size() == 0){
                arr.add(new User(dt.getKey(),dt.getValue()));
            }else{
                for(int i=0; i< arr.size(); i++){
                    if(dt.getValue() >= arr.get(i).getFinishedNumberInTotal()){
                         arr.add(i,new User(dt.getKey(),dt.getValue()));
                         break;
                    }
                }
                //if is smallest, add to end
                arr.add(new User(dt.getKey(),dt.getValue()));
            }
        }
        return arr;
    }
    /**
     * @param user who want to activate a new plan
     * @param book which book param.user want to choose
     * @param everyday_num how many words param.user want to memorize everyday
     * @return if plan is activated
     */
    public boolean activateStudyPlanByNum(User user, String book, int everyday_num) {
        PlanController pct = new PlanController();
        MemorizeController mct = new MemorizeController();

        int res = pct.addPlan(book, everyday_num, user.getID());
        if (res == 0) {
            return false;
        }
        pct.setPlan(user, res);
        user.setCurrentStudyPlan(pct.getPlan(res));
        mct.putMemorize(user);
        pct.updateTodayPlanInfo(user);

        return true;
    }

    /**
     * @param user who want to activate a new plan
     * @param book which book param.user want to choose
     * @param totalDay how many days param.user want to spend memorizing all
     * words
     * @return if plan is activated
     */
    public boolean activateStudyPlanByDay(User user, String book, int totalDay) {
        PlanController pct = new PlanController();
        MemorizeController mct = new MemorizeController();

        int res = pct.addPlan(totalDay, book, user.getID());
        if (res == 0) {
            return false;
        }
        pct.setPlan(user, res);
        user.setCurrentStudyPlan(pct.getPlan(res));
        mct.putMemorize(user);
        pct.updateTodayPlanInfo(user);

        return true;
    }

    /**
     * @param user
     * @param book the name of book
     * @return if the plan change successfully
     */
    public boolean changeStudyPlan(User user, String book) {
        PlanController pct = new PlanController();
        MemorizeController mct = new MemorizeController();
        if (pct.isPlan(user, book)) {
            StudyPlan spl = pct.getPlan(user, book);
            pct.setPlan(user, spl.getID());
            user.setCurrentStudyPlan(spl);
            pct.updateTodayPlanInfo(user);
            return true;
        }
        return false;
    }

    /**
     * @param user the user
     * @return all the plan this user has choosen
     *
     */
    public ArrayList<StudyPlan> bookPlanList(User user) {
        PlanController pct = new PlanController();
        return pct.getAllPlanByUser(user);
    }

    /**
     * @param user the user
     * @return all book info including book name, the number of words and if
     * this book is user's plan
     *
     */
    public Map<String, String> AllBookInfo(User user) {
        PlanController pct = new PlanController();
        WordController wct = new WordController();
        ArrayList<String> books = wct.getAllBook();
        Map<String, String> result = new HashMap<String, String>();
        for (String book : books) {
            int num = wct.getWordNumber(book);
            boolean isp = pct.isPlan(user, book);
            result.put(book, String.valueOf(num) + "," + isp);
        }
        return result;
    }

    /**
     * @param user who learn the word
     * @param wd which word be learnt
     * @param chosen which chinese meanning user choose
     * @return if param.chosen is the correct meaning of param.wd
     */
    public boolean learn(User user, Word wd, String chosen) {
        String chinese = wd.getChinese();
        boolean result;
        if (chinese.equals(chosen)) {
            this.correct(user, wd);
            result = true;
        } else {
            this.wrong(user, wd);
            result = false;
        }
        return result;
    }

    /**
     * @param user the instance of class User
     * @param wd the instance of class Word
     * @return if the correct count of this user memorize this word is added
     */
    private boolean correct(User user, Word wd) {
        Database db = Database.getInstance();
        MemorizeController mct = new MemorizeController();
        return mct.correct(user, wd);
    }

    /**
     * @param user the instance of class User
     * @param wd the instance of class Word
     * @return if the wrong count of this user memorize this word is added
     */
    private boolean wrong(User user, Word wd) {
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
        MemorizeController mct = new MemorizeController();
        System.out.println(uct.register("yyz", "123456"));
        User user = uct.getUser("yyz");
        System.out.println(uct.checkPassword(user, "123456"));
        System.out.println(uct.checkPassword(user, "456789"));
        uct.activateStudyPlanByNum(user, "CET4", 15);
        Word wd = wct.getBookWordByName("cet4", "able");
        uct.learn(user, wd, "不知道");

    }
}
