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
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.StudyPlan;
import model.User;
import model.Word;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yun_c
 */
public class UserControllerTest {

    public static User testUser;

    public UserControllerTest() {
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
            }
            res = db.prepare("select * from USERS where USERNAME = ?", "TEST");
            if (res.next()) {
                int user_id = res.getInt("ID");
                String name = res.getString("USERNAME");
                String pass = res.getString("PASSWORD");
                UserControllerTest.testUser = new User(name, pass, user_id);

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
     * Test of getUser method, of class UserController. If the username correct,
     * that means the result is expected
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        String username = "TEST";
        UserController instance = new UserController();
        User result = instance.getUser(username);
        assertEquals("TEST", result.getUsername());
    }

    /**
     * Test of register method, of class UserController.
     */
    @Test
    public void testRegister() {
        System.out.println("register");
        String username = "TEST2";
        String password = "TEST2";
        UserController instance = new UserController();
        boolean result = instance.register(username, password);
        if (result) {
            Database.getInstance().prepare("delete from USERS where USERNAME = ?", "TEST2");
        }
        assertEquals(true, result);
    }

    /**
     * Test of checkPassword method, of class UserController.
     */
    @Test
    public void testCheckPassword_User_String() {
        System.out.println("checkPassword");
        String password = "TEST";
        UserController instance = new UserController();
        boolean expResult = true;
        boolean result = instance.checkPassword(UserControllerTest.testUser, password);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkPassword method, of class UserController.
     */
    @Test
    public void testCheckPassword_String_charArr() {
        System.out.println("checkPassword");
        char[] password = {'T', 'E', 'S', 'T'};
        UserController instance = new UserController();
        boolean expResult = true;
        boolean result = instance.checkPassword(UserControllerTest.testUser.getUsername(), password);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkPassword method, of class UserController.
     */
    @Test
    public void testCheckPassword_String_String() {
        System.out.println("checkPassword");
        String password = "TEST";
        UserController instance = new UserController();
        boolean expResult = true;
        boolean result = instance.checkPassword(UserControllerTest.testUser.getUsername(), password);
        assertEquals(expResult, result);
    }

    /**
     * Test of activateStudyPlanByNum method, of class UserController.
     */
    @Test
    public void testActivateStudyPlanByNum() {
        System.out.println("activateStudyPlanByNum");
        String book = "CET4进阶";
        int everyday_num = 5;
        UserController instance = new UserController();
        boolean expResult = true;
        boolean result = instance.activateStudyPlanByNum(UserControllerTest.testUser, book, everyday_num);
        assertEquals(expResult, result);
    }

    /**
     * Test of activateStudyPlanByDay method, of class UserController.
     */
    @Test
    public void testActivateStudyPlanByDay() {
        System.out.println("activateStudyPlanByDay");
        String book = "CET6入门";
        int totalDay = 60;
        UserController instance = new UserController();
        boolean expResult = true;
        boolean result = instance.activateStudyPlanByDay(UserControllerTest.testUser, book, totalDay);
        assertEquals(expResult, result);
    }

    /**
     * Test of changeStudyPlan method, of class UserController.
     */
    @Test
    public void testChangeStudyPlan() {
        System.out.println("changeStudyPlan");
        String book = "CET4入门";
        UserController instance = new UserController();
        boolean expResult = true;
        boolean result = instance.changeStudyPlan(UserControllerTest.testUser, book);
        assertEquals(expResult, result);
    }

    /**
     * Test of bookPlanList method, of class UserController.
     */
    @Test
    public void testBookPlanList() {
        System.out.println("bookPlanList");
        UserController instance = new UserController();
        ArrayList<StudyPlan> result = instance.bookPlanList(UserControllerTest.testUser);
        for (StudyPlan p : result) {
            System.out.println(p.getName());
        }
        assertTrue(true);
    }

    /**
     * Test of AllBookInfo method, of class UserController.
     */
    @Test
    public void testAllBookInfo() {
        System.out.println("AllBookInfo");
        UserController instance = new UserController();
        Map<String, String> expResult = null;
        Map<String, String> result = instance.AllBookInfo(UserControllerTest.testUser);
        for (Entry<String, String> e : result.entrySet()) {
            System.out.println("book name: " + e.getKey() + "word number & isPlan: "
                    + e.getValue());
        }
        assertTrue(true);
    }

    /**
     * Test of checkAns method, of class UserController.
     */
    @Test
    public void testCheckAns() {
        System.out.println("checkAns");
        Database db = Database.getInstance();
        ResultSet res = db.prepare("select * from CET4入门 where WORD = ?", "lens");
        Word oriWord = null;
        Word choice = new Word(1, "lens", "n.透镜，镜片；镜头", "lenz", "CET4入门");

        try {
            if (res.next()) {
                int id = res.getInt("ID");
                String word = res.getString("WORD");
                String chinese = res.getString("CHINESE");
                String phonetic = res.getString("PHONETIC");
                oriWord = new Word(id, word, chinese, phonetic, "CET4入门");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        UserController instance = new UserController();
        boolean expResult = true;
        boolean result = instance.checkAns(UserControllerTest.testUser, oriWord, choice, 1);
        assertEquals(expResult, result);
    }
}
