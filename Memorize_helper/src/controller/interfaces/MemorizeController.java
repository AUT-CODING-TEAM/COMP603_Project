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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Memorize;
import model.StudyPlan;
import model.User;
import model.Word;

/**
 *
 * @author Yun_c
 * @author Pingchuan
 */
public class MemorizeController {

    public static final int FAKE_OPTION_NUM = 3;

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

        int len = words.size() > 100 ? 100 : words.size();
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
     * @param user who want enlarge plan memorize table
     * @return if init success
     */
    public boolean putMemorize(User user) {
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
        int now_num = this.getWordNumInMemorize(user);
        if (now_num >= words.size()) {
            return true;
        }
        int len = words.size() - now_num > 100 ? 100 : words.size() - now_num;
        String[][] val = new String[len][4];

        for (int i = 0; i < len; i++) {
            Word wd = words.get(i + now_num);
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
     * @return the map whose key is username and value is the total memorized
     * words number of this user
     */
    public Map<String, Integer> getAllUserMemorizedNum() {
        Database db = Database.getInstance();
        Map<String, Integer> users = new HashMap<String, Integer>();
        ResultSet res = db.get("USERS", "", "");
        try {
            while (res.next()) {
                int id = res.getInt("ID");
                String name = res.getString("USERNAME");
                int num = this.countMemorizedWord(id);
                users.put(name, num);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemorizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    /**
     * @param user get this user's memorized word in activated plan
     * @return memorized word
     */
    public ArrayList<Word> getMemorizedWordInPlan(User user) {
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

    /**
     * @return how many plan words added into the memorize table
     */
    public int getWordNumInMemorize(User user) {
        Database db = Database.getInstance();
        return db.count("MEMORIZE", "ID", user.getCurrentStudyPlan().getID());
    }

    /**
     * @param user user
     * @return the number of words user memorized in activated plan
     */
    public int countMemorizedWordInPlan(User user) {
        ArrayList<Word> wd = this.getMemorizedWordInPlan(user);
        return wd.size();
    }

    /**
     * @param id the user id
     * @return user's total memorized words number
     */
    public int countMemorizedWord(int id) {
        int num = 0;
        try {
            Database db = Database.getInstance();
            StringBuilder sbd = new StringBuilder("select count(*) as \"NUMBER\" from \"MEMORIZE\" ");
            sbd.append("where \"USER_ID\" = ? and \"AGING\" >= ?");
            ResultSet res = db.prepare(sbd.toString(),String.valueOf(id), 1);
            if (res.next()) {
                num = res.getInt("NUMBER");
            }

        } catch (SQLException ex) {
            Logger.getLogger(MemorizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }

    /**
     *
     * @param user Current user instance.
     * @return A list of word that need to memorize
     * @throws SQLException
     */
    public ArrayList<Word> getWordList(User user) throws SQLException {
        ArrayList<Word> wordList = new ArrayList<>();

        Database db = Database.getInstance();

        StringBuilder sb = new StringBuilder();
        String studyPlan = user.getCurrentStudyPlan().getStudyPlanName().toUpperCase();
        String userId = user.getID() + "";
        int number = user.getTodayTargetNumber() - user.getTodayLearnedNumber();
        if(number<1){
            number = user.getTodayTargetNumber();
        }
//        String studyPlan = "CET4入门";
//        String userId = "1";
//        int number = 30;

        sb.append("select ");
        sb.append(studyPlan).append(".* ");
        sb.append("from ");
        sb.append(studyPlan).append(", MEMORIZE ");
        sb.append("where MEMORIZE.USER_ID = \'").append(userId).append("\' and ");
        sb.append("MEMORIZE.WRONG = 0 and MEMORIZE.CORRECT = 0 and ");
        sb.append("MEMORIZE.WORD_SOURCE = \'").append(studyPlan).append("\' and ");
        sb.append(studyPlan).append(".ID = int(MEMORIZE.WORD_ID) fetch first ").append(number).append(" rows only");

        String sql = sb.toString();
        ResultSet rs = db.SQLqr(sql);

        while (rs.next()) {
            Word word = new Word(
                    rs.getInt("ID"),
                    rs.getString("WORD"),
                    rs.getString("CHINESE"),
                    rs.getString("PHONETIC"),
                    studyPlan
            );
            wordList.add(word);
        }

        return wordList;
    }

    /**
     *
     * @param user
     * @return A words list that contains words user learnt in current plan.
     * @throws SQLException
     */
    public ArrayList<Word> getLearntWords(User user) throws SQLException {
        ArrayList<Word> wordList = new ArrayList<>();
        Database db = Database.getInstance();

        String studyPlan = user.getCurrentStudyPlan().getStudyPlanName().toUpperCase();
        String userId = user.getID() + "";
//        String studyPlan = "CET4入门";
//        String userId = "1";

        StringBuilder sb = new StringBuilder();
        sb.append("select ")
                .append(studyPlan)
                .append(".* from MEMORIZE,")
                .append(studyPlan)
                .append(" where MEMORIZE.WORD_SOURCE = ? and MEMORIZE.USER_ID = ? and MEMORIZE.LAST_MEM_TIME != \'0\' and ")
                .append(studyPlan)
                .append(".ID = int(MEMORIZE.WORD_ID)");

        ResultSet rs = db.prepare(
                sb.toString(),
                studyPlan,
                userId
        );

        while (rs.next()) {
            Word word = new Word(
                    rs.getInt("ID"),
                    rs.getString("WORD"),
                    rs.getString("CHINESE"),
                    rs.getString("PHONETIC"),
                    studyPlan
            );
            wordList.add(word);
        }

        return wordList;
    }

    /**
     *
     * @param user
     * @return A list of words that contains all the words from current plan.
     * @throws SQLException
     */
    public ArrayList<Word> getPlanWords(User user) throws SQLException {
        ArrayList<Word> wordList = new ArrayList<>();
        Database db = Database.getInstance();

        String studyPlan = user.getCurrentStudyPlan().getStudyPlanName();
//        String studyPlan = "CET4入门";

        ResultSet rs = db.getFullTable(studyPlan);

        while (rs.next()) {
            Word word = new Word(
                    rs.getInt("ID"),
                    rs.getString("WORD"),
                    rs.getString("CHINESE"),
                    rs.getString("PHONETIC"),
                    studyPlan
            );
            wordList.add(word);
        }

        return wordList;
    }

    /**
     * This method returns a list of words for review, the number of words is
     * depends on the user review plan.
     *
     * @return A list of words that need to be review.
     * @throws SQLException
     */
    public ArrayList<Word> getReviewWordLists(User user) throws SQLException {
        ArrayList<Word> wordList = new ArrayList<>();
        Database db = Database.getInstance();
        StringBuilder sb = new StringBuilder();

        String studyPlan = user.getCurrentStudyPlan().getStudyPlanName();
        String userId = user.getID() + "";
        int number = user.getTodayReviewNumber();
        if(user.getTodayReviewNumber()<1){
            number = user.getTodayTargetNumber();
        }
  

//        String studyPlan = "CET4入门";
//        String userId = "1";
//        int number = 30;

        sb.append("select MEMORIZE.LAST_MEM_TIME, MEMORIZE.AGING, ")
                .append(studyPlan)
                .append(".* from MEMORIZE,")
                .append(studyPlan)
                .append(" where MEMORIZE.WORD_SOURCE = ? and MEMORIZE.USER_ID = ? and MEMORIZE.LAST_MEM_TIME != \'0\' and ")
                .append(studyPlan)
                .append(".ID = int(MEMORIZE.WORD_ID) order by MEMORIZE.AGING, MEMORIZE.LAST_MEM_TIME ASC fetch first ? rows only");

        ResultSet rs = db.prepare(sb.toString(), studyPlan, userId, number);

        while (rs.next()) {
            Word word = new Word(
                    rs.getInt("ID"),
                    rs.getString("WORD"),
                    rs.getString("CHINESE"),
                    rs.getString("PHONETIC"),
                    studyPlan
            );
            wordList.add(word);
        }

        return wordList;
    }

    public Set<Word> getOptions(String studyPlan, Word word) throws SQLException {
        Set<Word> wordSet = new HashSet<>();
        WordController wordController = new WordController();
        Database db = Database.getInstance();
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();

        int bookSize = wordController.getWordNumber(studyPlan);

        String sql = sb.append("select * from ").append(studyPlan).append(" where ID = ?").toString();
        ResultSet rs;

        wordSet.add(word);
        while (wordSet.size() < 4) {
            int id = rd.nextInt(bookSize) + 1;
            rs = db.prepare(sql, id);
            if (rs.next()) {
                wordSet.add(
                        new Word(
                                rs.getInt("ID"),
                                rs.getString("WORD"),
                                rs.getString("CHINESE"),
                                rs.getString("PHONETIC"),
                                studyPlan
                        )
                );
            }

        }
        return wordSet;
    }

    public static void main(String[] args) throws SQLException {
        MemorizeController mc = new MemorizeController();
        WordController wc = new WordController();
//        mc.getLearntWords();
//        mc.getPlanWords();
//        mc.getReviewWordLists();
//        Word word1 = wc.getBookWordByID("CET4入门", 2);
//        mc.getOptions("CET4入门", word1);
    }

}
