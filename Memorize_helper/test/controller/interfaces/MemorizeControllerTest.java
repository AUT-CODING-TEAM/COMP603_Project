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

/**
 *
 * @author Yun_c
 */
public class MemorizeControllerTest {

    public MemorizeControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Database db = Database.getInstance();
        SHA256Util sha256 = new SHA256Util();
        db.init();
        ResultSet res = db.prepare("select * from USERS where USERNAME = ? and PASSWORD = ?", "TEST", sha256.SHA256("TEST"));
        try {
            if (!res.next()) {
                db.prepare("insert into USERS (USERNAME, PASSWORD) values (?, ?)", "TEST", sha256.SHA256("TEST"));
                res = db.prepare("select * from USERS where USERNAME = ? and PASSWORD = ?", "TEST", sha256.SHA256("TEST"));
            }

            if (res.next()) {
                int user_id = res.getInt("ID");
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
                for (int i = 0; i < 100; i++) {
                    if (words.next()) {
                        db.prepare("insert into MEMORIZE (USER_ID, WORD_ID,"
                                + " WORD_SOURCE, LAST_MEM_TIME) values (?, ?, "
                                + "?, ?)", String.valueOf(user_id), words.getString("ID"),
                                "CET4入门", "0");
                    }

                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(MemorizeControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @AfterClass
    public static void tearDownClass() {
        try {
            Database db = Database.getInstance();
            SHA256Util sha256 = new SHA256Util();
            db.init();
            ResultSet res = db.prepare("select * from USERS where USERNAME = ? and PASSWORD = ?", "TEST", sha256.SHA256("TEST"));
            if (res.next()) {
                int user_id = res.getInt("ID");
                db.prepare("delete from USERS where USERNAME = ?", "TEST");
                db.prepare("delete from PLAN where USER_ID = ?", String.valueOf(user_id));
                db.prepare("delete from MEMORIZE where USER_ID = ?", String.valueOf(user_id));
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
        User user = null;
        Word wd = null;
        MemorizeController instance = new MemorizeController();
        Memorize expResult = null;
        Memorize result = instance.getMemorize(user, wd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initMemorize method, of class MemorizeController.
     */
    @Test
    public void testInitMemorize() {
        System.out.println("initMemorize");
        User user = null;
        MemorizeController instance = new MemorizeController();
        boolean expResult = false;
        boolean result = instance.initMemorize(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putMemorize method, of class MemorizeController.
     */
    @Test
    public void testPutMemorize() {
        System.out.println("putMemorize");
        User user = null;
        MemorizeController instance = new MemorizeController();
        boolean expResult = false;
        boolean result = instance.putMemorize(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateTime method, of class MemorizeController.
     */
    @Test
    public void testUpdateTime_3args_1() {
        System.out.println("updateTime");
        int userid = 0;
        int wordid = 0;
        String source = "";
        MemorizeController instance = new MemorizeController();
        boolean expResult = false;
        boolean result = instance.updateTime(userid, wordid, source);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateTime method, of class MemorizeController.
     */
    @Test
    public void testUpdateTime_3args_2() {
        System.out.println("updateTime");
        String username = "";
        String word = "";
        String source = "";
        MemorizeController instance = new MemorizeController();
        boolean expResult = false;
        boolean result = instance.updateTime(username, word, source);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateTime method, of class MemorizeController.
     */
    @Test
    public void testUpdateTime_User_Word() {
        System.out.println("updateTime");
        User user = null;
        Word wd = null;
        MemorizeController instance = new MemorizeController();
        boolean expResult = false;
        boolean result = instance.updateTime(user, wd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of aging method, of class MemorizeController.
     */
    @Test
    public void testAging() {
        System.out.println("aging");
        int userid = 0;
        int wordid = 0;
        String source = "";
        int ag = 0;
        MemorizeController instance = new MemorizeController();
        boolean expResult = false;
        boolean result = instance.aging(userid, wordid, source, ag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCorrectCount method, of class MemorizeController.
     */
    @Test
    public void testGetCorrectCount() {
        System.out.println("getCorrectCount");
        User user = null;
        Word wd = null;
        MemorizeController instance = new MemorizeController();
        int expResult = 0;
        int result = instance.getCorrectCount(user, wd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWrongCount method, of class MemorizeController.
     */
    @Test
    public void testGetWrongCount() {
        System.out.println("getWrongCount");
        User user = null;
        Word wd = null;
        MemorizeController instance = new MemorizeController();
        int expResult = 0;
        int result = instance.getWrongCount(user, wd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of correct method, of class MemorizeController.
     */
    @Test
    public void testCorrect_Memorize() {
        System.out.println("correct");
        Memorize memo = null;
        MemorizeController instance = new MemorizeController();
        boolean expResult = false;
        boolean result = instance.correct(memo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of wrong method, of class MemorizeController.
     */
    @Test
    public void testWrong_Memorize() {
        System.out.println("wrong");
        Memorize memo = null;
        MemorizeController instance = new MemorizeController();
        boolean expResult = false;
        boolean result = instance.wrong(memo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of correct method, of class MemorizeController.
     */
    @Test
    public void testCorrect_User_Word() {
        System.out.println("correct");
        User user = null;
        Word wd = null;
        MemorizeController instance = new MemorizeController();
        boolean expResult = false;
        boolean result = instance.correct(user, wd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of wrong method, of class MemorizeController.
     */
    @Test
    public void testWrong_User_Word() {
        System.out.println("wrong");
        User user = null;
        Word wd = null;
        MemorizeController instance = new MemorizeController();
        boolean expResult = false;
        boolean result = instance.wrong(user, wd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllUserMemorizedNum method, of class MemorizeController.
     */
    @Test
    public void testGetAllUserMemorizedNum() {
        System.out.println("getAllUserMemorizedNum");
        MemorizeController instance = new MemorizeController();
        Map<String, Integer> expResult = null;
        Map<String, Integer> result = instance.getAllUserMemorizedNum();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMemorizedWordInPlan method, of class MemorizeController.
     */
    @Test
    public void testGetMemorizedWordInPlan() {
        System.out.println("getMemorizedWordInPlan");
        User user = null;
        MemorizeController instance = new MemorizeController();
        ArrayList<Word> expResult = null;
        ArrayList<Word> result = instance.getMemorizedWordInPlan(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWordNumInMemorize method, of class MemorizeController.
     */
    @Test
    public void testGetWordNumInMemorize() {
        System.out.println("getWordNumInMemorize");
        User user = null;
        MemorizeController instance = new MemorizeController();
        int expResult = 0;
        int result = instance.getWordNumInMemorize(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countMemorizedWordInPlan method, of class MemorizeController.
     */
    @Test
    public void testCountMemorizedWordInPlan() {
        System.out.println("countMemorizedWordInPlan");
        User user = null;
        MemorizeController instance = new MemorizeController();
        int expResult = 0;
        int result = instance.countMemorizedWordInPlan(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countMemorizedWord method, of class MemorizeController.
     */
    @Test
    public void testCountMemorizedWord() {
        System.out.println("countMemorizedWord");
        int id = 0;
        MemorizeController instance = new MemorizeController();
        int expResult = 0;
        int result = instance.countMemorizedWord(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWordList method, of class MemorizeController.
     */
    @Test
    public void testGetWordList() throws Exception {
        System.out.println("getWordList");
        User user = null;
        MemorizeController instance = new MemorizeController();
        ArrayList<Word> expResult = null;
        ArrayList<Word> result = instance.getWordList(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLearntWords method, of class MemorizeController.
     */
    @Test
    public void testGetLearntWords() throws Exception {
        System.out.println("getLearntWords");
        User user = null;
        MemorizeController instance = new MemorizeController();
        ArrayList<Word> expResult = null;
        ArrayList<Word> result = instance.getLearntWords(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlanWords method, of class MemorizeController.
     */
    @Test
    public void testGetPlanWords() throws Exception {
        System.out.println("getPlanWords");
        User user = null;
        MemorizeController instance = new MemorizeController();
        ArrayList<Word> expResult = null;
        ArrayList<Word> result = instance.getPlanWords(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReviewWordLists method, of class MemorizeController.
     */
    @Test
    public void testGetReviewWordLists() throws Exception {
        System.out.println("getReviewWordLists");
        User user = null;
        MemorizeController instance = new MemorizeController();
        ArrayList<Word> expResult = null;
        ArrayList<Word> result = instance.getReviewWordLists(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOptions method, of class MemorizeController.
     */
    @Test
    public void testGetOptions() throws Exception {
        System.out.println("getOptions");
        String studyPlan = "";
        Word word = null;
        MemorizeController instance = new MemorizeController();
        Set<Word> expResult = null;
        Set<Word> result = instance.getOptions(studyPlan, word);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class MemorizeController.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        MemorizeController.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
