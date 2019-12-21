/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import model.Memorize;
import model.User;
import model.Word;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import database.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.StudyPlan;

/**
 *
 * @author Yun_c
 */
public class MemorizeControllerTest {

    public static User user;
    public static StudyPlan plan;

    public MemorizeControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Database db = Database.getInstance();
        SHA256Util sha256 = new SHA256Util();
        db.init();
        ResultSet res = db.prepare("select * from USERS where USERNAME = ?", "TEST");
        try {
            if (!res.next()) {
                db.prepare("insert into USERS (USERNAME, PASSWORD, STUDY_PLAN) values (?, ?, ?)", "TEST", sha256.SHA256("TEST"), "0");
                System.out.println("user create");
            }
            res = db.prepare("select * from USERS where USERNAME = ?", "TEST");
            if (res.next()) {
                int user_id = res.getInt("ID");
                String name = res.getString("USERNAME");
                String pass = res.getString("PASSWORD");
                MemorizeControllerTest.user = new User(name, pass, user_id);

                int everyday_num = 5;
                int total_day = 0;
                long time = System.currentTimeMillis();
                String book = "CET4入门";
                ResultSet count = db.prepare("select count(*) as NUMBER from CET4入门");
                ResultSet words = db.prepare("select * from CET4入门");

                if (count.next()) {
                    total_day = count.getInt("NUMBER") / everyday_num;
                }
                db.prepare("insert into PLAN (USER_ID, BOOK, TOTAL_DAY, "
                        + "START_TIME, TODAY_TARGET_NUMBER, FINISH) values "
                        + "(?, ?, ?, ?, ?, ?)", user_id, book, total_day, time,
                        everyday_num, 0);
                System.out.println("plan create");

                res = db.prepare("select * from PLAN where USER_ID = ?", String.valueOf(user_id));
                res.next();
                int id = res.getInt("ID");
                int today_num = res.getInt("TODAY_TARGET_NUMBER");
                long start = res.getLong("START_TIME");
                int isFinish = res.getInt("FINISH");
                int total_num = db.count(book, "", "");
                plan = new StudyPlan(book, id, total_num, total_day, start, today_num, isFinish);
                user.setCurrentStudyPlan(plan);
                db.prepare("update USERS set STUDY_PLAN = ? where ID = ?", String.valueOf(id), user.getID());
                for (int i = 0; i < 100; i++) {
                    if (words.next()) {
                        db.prepare("insert into MEMORIZE (USER_ID, WORD_ID,"
                                + " WORD_SOURCE, LAST_MEM_TIME) values (?, ?, "
                                + "?, ?)", String.valueOf(user_id), words.getString("ID"),
                                "CET4入门", "0");
                        System.out.println("memorize insert" + i);
                    }
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(MemorizeControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Test set up done!");
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            Database db = Database.getInstance();
            SHA256Util sha256 = new SHA256Util();
            db.init();
            ResultSet res = db.prepare("select * from USERS where USERNAME = ?", "TEST");
            if (res.next()) {
                int user_id = res.getInt("ID");
                db.prepare("delete from USERS where USERNAME = ?", "TEST");
                System.out.println("user delete");
                db.prepare("delete from PLAN where USER_ID = ?", String.valueOf(user_id));
                System.out.println("plan delete");
                db.prepare("delete from MEMORIZE where USER_ID = ?", String.valueOf(user_id));
                System.out.println("memorize delete");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemorizeControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getMemorize method, of class MemorizeController.
     */
    @Test
    public void testGetMemorize() {
        System.out.println("getMemorize");
        Word wd = new Word(1, "lens", "", "", "CET4入门");
        MemorizeController instance = new MemorizeController();
        Memorize result = instance.getMemorize(user, wd);
        assertTrue(result.getWordSource().equals("CET4入门"));
        assertTrue(result.getAge() == 0);
        assertTrue(result.getCorrect() == 0);
        assertTrue(result.getWrong() == 0);
    }

    /**
     * Test of putMemorize method, of class MemorizeController.
     */
    @Test
    public void testPutMemorize() {
        System.out.println("putMemorize");
        MemorizeController instance = new MemorizeController();
        boolean expResult = true;
        boolean result = instance.putMemorize(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateTime method, of class MemorizeController.
     */
    @Test
    public void testUpdateTime_3args_1() {
        System.out.println("updateTime");
        int wordid = 1;
        String source = "CET4入门";
        MemorizeController instance = new MemorizeController();
        boolean expResult = true;
        boolean result = instance.updateTime(user.getID(), wordid, source, 1);
        Database.getInstance().prepare("update MEMORIZE set CORRECT = 0,"
                + "WRONG = 0, AGING = 0, LAST_MEM_TIME = ? where USER_ID = ?",
                "0", String.valueOf(user.getID()));
        assertEquals(expResult, result);
    }

    /**
     * Test of aging method, of class MemorizeController.
     */
    @Test
    public void testAging() {
        System.out.println("aging");
        int wordid = 1;
        String source = "CET4入门";
        int ag = 1;
        MemorizeController instance = new MemorizeController();
        boolean expResult = true;
        boolean result = instance.aging(user.getID(), wordid, source, ag);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCorrectCount method, of class MemorizeController.
     */
    @Test
    public void testGetCorrectCount() {
        System.out.println("getCorrectCount");
        Word wd = new Word(1, "lens", "", "", "CET4入门");
        MemorizeController instance = new MemorizeController();
        int expResult = 0;
        int result = instance.getCorrectCount(user, wd);
        assertEquals(expResult, result);
    }

    /**
     * Test of getWrongCount method, of class MemorizeController.
     */
    @Test
    public void testGetWrongCount() {
        System.out.println("getWrongCount");
        Word wd = new Word(1, "lens", "", "", "CET4入门");
        MemorizeController instance = new MemorizeController();
        int expResult = 0;
        int result = instance.getWrongCount(user, wd);
        assertEquals(expResult, result);
    }

    /**
     * Test of correct method, of class MemorizeController.
     */
    @Test
    public void testCorrect_Memorize() {
        System.out.println("correct");
        Memorize memo = null;
        Word wd = new Word(1, "lens", "", "", "CET4入门");
        MemorizeController instance = new MemorizeController();
        memo = instance.getMemorize(user, wd);
        boolean expResult = true;
        boolean result = instance.correct(memo, 1);
        Database.getInstance().prepare("update MEMORIZE set CORRECT = 0,"
                + "WRONG = 0, AGING = 0, LAST_MEM_TIME = ? where USER_ID = ?",
                "0", String.valueOf(user.getID()));
        assertEquals(expResult, result);

    }

    /**
     * Test of wrong method, of class MemorizeController.
     */
    @Test
    public void testWrong_Memorize() {
        System.out.println("wrong");
        Memorize memo = null;
        MemorizeController instance = new MemorizeController();
        Word wd = new Word(1, "lens", "", "", "CET4入门");
        memo = instance.getMemorize(user, wd);
        boolean expResult = true;
        boolean result = instance.wrong(memo);
        Database.getInstance().prepare("update MEMORIZE set CORRECT = 0,"
                + "WRONG = 0, AGING = 0, LAST_MEM_TIME = ? where USER_ID = ?",
                "0", String.valueOf(user.getID()));
        assertEquals(expResult, result);
    }

    /**
     * Test of correct method, of class MemorizeController.
     */
    @Test
    public void testCorrect_User_Word() {
        System.out.println("correct");
        MemorizeController instance = new MemorizeController();
        Word wd = new Word(1, "lens", "", "", "CET4入门");
        boolean expResult = true;
        boolean result = instance.correct(user, wd, 1);
        Database.getInstance().prepare("update MEMORIZE set CORRECT = 0,"
                + "WRONG = 0, AGING = 0, LAST_MEM_TIME = ? where USER_ID = ?",
                "0", String.valueOf(user.getID()));
        assertEquals(expResult, result);
    }

    /**
     * Test of wrong method, of class MemorizeController.
     */
    @Test
    public void testWrong_User_Word() {
        System.out.println("wrong");
        Word wd = new Word(1, "lens", "", "", "CET4入门");
        MemorizeController instance = new MemorizeController();
        boolean expResult = true;
        boolean result = instance.wrong(user, wd);
        Database.getInstance().prepare("update MEMORIZE set CORRECT = 0,"
                + "WRONG = 0, AGING = 0, LAST_MEM_TIME = ? where USER_ID = ?",
                "0", String.valueOf(user.getID()));
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllUserMemorizedNum method, of class MemorizeController.
     */
    @Test
    public void testGetAllUserMemorizedNum() {
        System.out.println("getAllUserMemorizedNum");
        MemorizeController instance = new MemorizeController();
        Map<String, Integer> result = instance.getAllUserMemorizedNum();
        assertTrue(true);
    }

    /**
     * Test of getMemorizedWordInPlan method, of class MemorizeController.
     */
    @Test
    public void testGetMemorizedWordInPlan() {
        System.out.println("getMemorizedWordInPlan");
        MemorizeController instance = new MemorizeController();
        ArrayList<Word> result = instance.getMemorizedWordInPlan(user);
        assertTrue(result != null);
        assertTrue(0 == result.size());
    }

    /**
     * Test of getWordNumInMemorize method, of class MemorizeController.
     */
    @Test
    public void testGetWordNumInMemorize() {
        System.out.println("getWordNumInMemorize");
        MemorizeController instance = new MemorizeController();
        
        //beforeClass put 100, and testPutMemorize put another 100, total 200
        int expResult = 200;
        int result = instance.getWordNumInMemorize(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of countMemorizedWordInPlan method, of class MemorizeController.
     */
    @Test
    public void testCountMemorizedWordInPlan() {
        System.out.println("countMemorizedWordInPlan");
        MemorizeController instance = new MemorizeController();
        int expResult = 0;
        int result = instance.countMemorizedWordInPlan(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of countMemorizedWord method, of class MemorizeController.
     */
    @Test
    public void testCountMemorizedWord() {
        System.out.println("countMemorizedWord");
        MemorizeController instance = new MemorizeController();
        int expResult = 0;
        int result = instance.countMemorizedWord(user.getID());
        assertEquals(expResult, result);
    }

    /**
     * Test of getWordList method, of class MemorizeController.
     */
    @Test
    public void testGetWordList() throws Exception {
        System.out.println("getWordList");
        MemorizeController instance = new MemorizeController();
        ArrayList<Word> result = instance.getWordList(user);
        assertEquals(5, result.size());
    }

    /**
     * Test of getLearntWords method, of class MemorizeController.
     */
    @Test
    public void testGetLearntWords() throws Exception {
        System.out.println("getLearntWords");
        MemorizeController instance = new MemorizeController();
        ArrayList<Word> result = instance.getLearntWords(user);
        assertEquals(0, result.size());
    }

    /**
     * Test of getPlanWords method, of class MemorizeController.
     */
    @Test
    public void testGetPlanWords() throws Exception {
        System.out.println("getPlanWords");
        MemorizeController instance = new MemorizeController();
        ArrayList<Word> result = instance.getPlanWords(user);
        assertEquals(609, result.size());
    }

    /**
     * Test of getReviewWordLists method, of class MemorizeController.
     */
    @Test
    public void testGetReviewWordLists() throws Exception {
        System.out.println("getReviewWordLists");
        MemorizeController instance = new MemorizeController();
        ArrayList<Word> result = instance.getReviewWordLists(user);
        assertEquals(0, result.size());
    }

    /**
     * Test of getOptions method, of class MemorizeController.
     */
    @Test
    public void testGetOptions() throws Exception {
        System.out.println("getOptions");
        String studyPlan = "CET4入门";
        Word wd = new Word(1, "lens", "", "", "CET4入门");
        MemorizeController instance = new MemorizeController();
        Set<Word> result = instance.getOptions(studyPlan, wd);
        assertEquals(4, result.size());
        assertTrue(result.contains(wd));
    }

}
