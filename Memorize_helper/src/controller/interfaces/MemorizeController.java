/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import database.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Memorize;
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
}
