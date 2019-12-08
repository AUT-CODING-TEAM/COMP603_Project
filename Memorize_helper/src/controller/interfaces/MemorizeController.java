/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import database.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Memorize;
import model.StudyPlan;
import model.User;
import model.Word;

/**
 *
 * @author Yun_c
 */
public class MemorizeController {

    /**
     * @param user who memorized the word
     * @param wd what word user memorized
     * @return an instance of Memorize
     */
    public Memorize getMemorize(User user, Word wd) {
        Database db = Database.getInstance();
        String[] col = {
            "user_id",
            "word_id",
            "word_source"
        };
        String[] val = {
            String.valueOf(user.getID()),
            String.valueOf(wd.getID()),
            wd.getSource()
        };
        Memorize memo = null;
        ResultSet res = db.get("memorize", col, val);
        try {
            if (!res.next()) {
                return memo;
            }
            memo = new Memorize(
                    res.getInt("ID"),
                    res.getInt("WORD_ID"),
                    res.getInt("USER_ID"),
                    res.getString("WORD_SOURCE"),
                    res.getInt("CORRECT"),
                    res.getInt("WRONG"),
                    res.getLong("LAST_MEM_TIME"),
                    res.getInt("AGING")
            );

        } catch (SQLException ex) {
            Logger.getLogger(MemorizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return memo;
    }

    /**
     * @param user who want initialize a new plan
     * @return if init success
     */
    public boolean initMemorize(User user) {
        boolean result = true;
        Database db = Database.getInstance();
        StudyPlan plan = user.getCurrentStudyPlan();
        String uid = String.valueOf(user.getID());
        WordController wct = new WordController();
        if (plan == null) {
            return false;
        }
        String book = plan.getStudyPlanName();
        ArrayList<Word> words = wct.getBookContent(book);
        String[] col = {"user_id", "word_id", "word_source", "last_mem_time"};

        int len = words.size();
        String[][] val = new String[len][4];

        for (int i = 0; i < len; i++) {
            Word wd = words.get(i);
            String[] temp = {uid, String.valueOf(wd.getID()), book, "0"};
            val[i] = temp;
        }
        result = db.add("memorize", col, val);
        return result;
    }

    /**
     * @param userid the user's ID
     * @param wordid the word's ID
     * @param source the word belong to which source (eg. CET4 or CET6 or IELTS)
     * @return if the last memory time are updated
     */
    public boolean updateTime(int userid, int wordid, String source) {
        Database db = Database.getInstance();
        String[] col2 = {"user_id", "word_id", "word_source"};
        String[] val2 = {
            String.valueOf(userid),
            String.valueOf(wordid),
            source
        //String.valueOf(System.currentTimeMillis())
        };
        boolean res = db.set("memorize", col2, val2,
                "last_mem_time", String.valueOf(System.currentTimeMillis()));
        return res;

    }

    /**
     * @param username user's username
     * @param word the word wait to be memorized
     * @param source the word belong to which source (eg. CET4 or CET6 or IELTS)
     * @return if the last memory time are updated
     */
    public boolean updateTime(String username, String word, String source) {
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

            return this.updateTime(
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
     * @param user the instance of class User
     * @param wd the instance of class Word
     * @return if the last memory time are updated
     */
    public boolean updateTime(User user, Word wd) {
        return this.updateTime(user.getID(), wd.getID(), wd.getSource());
    }

    /**
     * @param userid user's id
     * @param wordid word's id
     * @param source which book word belongs to
     * @param ag the after +1 aging number
     * @return if age updated success
     */
    public boolean aging(int userid, int wordid, String source, int ag) {
        Database db = Database.getInstance();
        String[] col2 = {"user_id", "word_id", "word_source"};
        String[] val2 = {
            String.valueOf(userid),
            String.valueOf(wordid),
            source
        //String.valueOf(System.currentTimeMillis())
        };
        boolean res = db.set("memorize", col2, val2,
                "aging", ag);
        return res;
    }

    /**
     * @param user whose memorize situation shoud be search
     * @param wd what word's correct count shoud be search
     * @return the correct count of this user memorize this word
     */
    public int getCorrectCount(User user, Word wd) {
        int count = -1;
        Memorize memo = this.getMemorize(user, wd);
        if (memo == null) {
            return count;
        }
        count = memo.getCorrect();
        return count;
    }

    /**
     * @param user whose memorize situation shoud be search
     * @param wd what word's memorize info should be search
     * @return the wrong count of this user memorize this word
     */
    public int getWrongCount(User user, Word wd) {
        int count = -1;
        Memorize memo = this.getMemorize(user, wd);
        if (memo == null) {
            return count;
        }
        count = memo.getWrong();
        return count;
    }

    /**
     * @param memo a memorize instance whose correct count will be added
     * @return if the correct count updated
     */
    public boolean correct(Memorize memo) {
        Database db = Database.getInstance();
        memo.addCorrect();
        String[] key = {"user_id", "word_id", "word_source"};
        String[] con = {
            String.valueOf(memo.getUserID()),
            String.valueOf(memo.getWordID()),
            memo.getWordSource()
        };
        boolean res = db.set("memorize", key, con, "correct", memo.getCorrect());
        this.updateTime(memo.getUserID(), memo.getWordID(), memo.getWordSource());
        this.aging(
                memo.getUserID(),
                memo.getWordID(),
                memo.getWordSource(),
                memo.getAge() + 1
        );
        return res;
    }

    /**
     * @param memo a memorize instance whose wrong count will be added
     * @return if the wrong count updated
     */
    public boolean wrong(Memorize memo) {
        Database db = Database.getInstance();
        memo.addWrong();
        String[] key = {"user_id", "word_id", "word_source"};
        String[] con = {
            String.valueOf(memo.getUserID()),
            String.valueOf(memo.getWordID()),
            memo.getWordSource()
        };
        boolean res = db.set("memorize", key, con, "wrong", memo.getWrong());
        return res;
    }

    /**
     * @param user whose memorize situation shoud be search
     * @param wd what word's memorize info should be update
     * @return if the correct count updated
     */
    public boolean correct(User user, Word wd) {
        Database db = Database.getInstance();
        Memorize memo = this.getMemorize(user, wd);
        return this.correct(memo);
    }

    /**
     * @param user whose memorize situation shoud be search
     * @param wd what word's memorize info should be update
     * @return if the wrong count updated
     */
    public boolean wrong(User user, Word wd) {
        Database db = Database.getInstance();
        Memorize memo = this.getMemorize(user, wd);
        return this.wrong(memo);
    }

    /**
     * @param user get this user's all memorized word
     * @return all memorized word
     */
    public ArrayList<Word> getMemorizedWord(User user) {
        Database db = Database.getInstance();
        StudyPlan plan = user.getCurrentStudyPlan();
        WordController wct = new WordController();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ArrayList<Word> words = new ArrayList<Word>();
        if (plan == null) {
            return null;
        }
        String book = plan.getStudyPlanName();
        StringBuilder bd = new StringBuilder("select \"WORD_ID\" from \"MEMORIZE\"");
        bd.append(" where \"").append("USER_ID\"").append(" = \'");
        bd.append(String.valueOf(user.getID())).append("\' and ");
        bd.append("\"WORD_SOURCE\" = \'").append(book).append("\' and ");
        bd.append("\"LAST_MEM_TIME\" != \'0\'");
        ResultSet res = db.SQLqr(bd.toString());
        try {
            while (res.next()) {
                ids.add(res.getInt("WORD_ID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemorizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i : ids) {
            Word wd = wct.getBookWordByID(book, i);
            if (wd != null) {
                words.add(wd);
            }
        }
        return words;
    }
    
    public int countMemorizedWord(User user){
        ArrayList<Word> wd = this.getMemorizedWord(user);
        return wd.size();
    }
}
