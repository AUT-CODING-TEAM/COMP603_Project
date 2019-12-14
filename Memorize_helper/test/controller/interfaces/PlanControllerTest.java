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
import model.StudyPlan;
import model.User;
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
public class PlanControllerTest {

    public static User user;
    public static StudyPlan plan;

    public PlanControllerTest() {
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
                PlanControllerTest.user = new User(name, pass, user_id);

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
                res = db.prepare("select * from PLAN where USER_ID = ?", String.valueOf(user_id));
                res.next();
                int id = res.getInt("ID");
                int today_num = res.getInt("TODAY_TARGET_NUMBER");
                long start = res.getLong("START_TIME");
                int isFinish = res.getInt("FINISH");
                int total_num = db.count(book, "", "");
                PlanControllerTest.plan = new StudyPlan(book, id, total_num, total_day, start, today_num, isFinish);
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
     * Test of getPlan method, of class PlanController.
     */
    @Test
    public void testGetPlan_int() {
        System.out.println("getPlan");
        PlanController instance = new PlanController();

        StudyPlan result = instance.getPlan(plan.getID());
        assertEquals(plan.getName(), result.getName());
        assertEquals(plan.getID(), result.getID());
        
    }

    /**
     * Test of getPlan method, of class PlanController.
     */
    @Test
    public void testGetPlan_User_String() {
        System.out.println("getPlan");
        String book = "CET4入门";
        PlanController instance = new PlanController();
        StudyPlan result = instance.getPlan(user, book);
        assertEquals(plan.getName(), result.getName());
        assertEquals(plan.getID(), result.getID());
    }

    /**
     * Test of getPlanIdByBook method, of class PlanController.
     */
    @Test
    public void testGetPlanIdByBook() {
        System.out.println("getPlanIdByBook");
        String book = "CET4入门";
        PlanController instance = new PlanController();
        int result = instance.getPlanIdByBook(user, book);
        assertEquals(plan.getID(), result);
    }

    /**
     * Test of getAllPlanByUser method, of class PlanController.
     */
    @Test
    public void testGetAllPlanByUser() {
        System.out.println("getAllPlanByUser");
        PlanController instance = new PlanController();
        ArrayList<StudyPlan> result = instance.getAllPlanByUser(user);
        assertTrue(result.size() == 1);
        assertEquals(result.get(0).getID(), plan.getID());
    }

    /**
     * Test of isPlan method, of class PlanController.
     */
    @Test
    public void testIsPlan() {
        System.out.println("isPlan");
        String book = "CET4入门";
        PlanController instance = new PlanController();
        boolean expResult = true;
        boolean result = instance.isPlan(user, book);
        assertEquals(expResult, result);

    }

    /**
     * Test of addPlan method, of class PlanController.
     */
    @Test
    public void testAddPlan_3args_1() {
        System.out.println("addPlan");
        String book = "CET4进阶";
        int everyday_num = 1;
        PlanController instance = new PlanController();
        int expResult = 0;
        int result = instance.addPlan(book, everyday_num, user.getID());
        assertFalse(expResult == result);
    }

    /**
     * Test of addPlan method, of class PlanController.
     */
    @Test
    public void testAddPlan_3args_2() {
        System.out.println("addPlan");
        int total_day = 100;
        String book = "CET6入门";
        PlanController instance = new PlanController();
        int expResult = 0;
        int result = instance.addPlan(total_day, book, user.getID());
        assertFalse(expResult==result);
    }

    /**
     * Test of setPlan method, of class PlanController.
     */
    @Test
    public void testSetPlan() {
        System.out.println("setPlan");
        PlanController instance = new PlanController();
        instance.setPlan(user, plan.getID());
        assertTrue(true);
    }

    /**
     * Test of getTotalWordNum method, of class PlanController.
     */
    @Test
    public void testGetTotalWordNum() {
        System.out.println("getTotalWordNum");
        PlanController instance = new PlanController();
        int expResult = 609;
        int result = instance.getTotalWordNum(plan);
        assertEquals(expResult, result);
    }

    /**
     * Test of getFinishWordNum method, of class PlanController.
     */
    @Test
    public void testGetFinishWordNum_int_StudyPlan() {
        System.out.println("getFinishWordNum");
        PlanController instance = new PlanController();
        int expResult = 0;
        int result = instance.getFinishWordNum(user.getID(), plan);
        assertEquals(expResult, result);
    }

    /**
     * Test of getFinishWordNum method, of class PlanController.
     */
    @Test
    public void testGetFinishWordNum_User() {
        System.out.println("getFinishWordNum");
        PlanController instance = new PlanController();
        int expResult = 0;
        int result = instance.getFinishWordNum(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of getRemainWordNum method, of class PlanController.
     */
    @Test
    public void testGetRemainWordNum() {
        System.out.println("getRemainWordNum");
        PlanController instance = new PlanController();
        int expResult = 609;
        int result = instance.getRemainWordNum(user.getID(), plan);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlanName method, of class PlanController.
     */
    @Test
    public void testGetPlanName() {
        System.out.println("getPlanName");
        PlanController instance = new PlanController();
        String expResult = "CET4入门";
        String result = instance.getPlanName(plan);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateTodayPlanInfo method, of class PlanController.
     */
    @Test
    public void testUpdateTodayPlanInfo() {
        System.out.println("updateTodayPlanInfo");
        PlanController instance = new PlanController();
        instance.updateTodayPlanInfo(user);
        assertTrue(true);
    }

    /**
     * Test of getTodayMemorizedNum method, of class PlanController.
     */
    @Test
    public void testGetTodayMemorizedNum() {
        System.out.println("getTodayMemorizedNum");
        PlanController instance = new PlanController();
        int expResult = 0;
        int result = instance.getTodayMemorizedNum(user);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTodayReviewedNum method, of class PlanController.
     */
    @Test
    public void testGetTodayReviewedNum() {
        System.out.println("getTodayReviewedNum");
        PlanController instance = new PlanController();
        int expResult = 0;
        int result = instance.getTodayReviewedNum(user);
        assertEquals(expResult, result);
    }

}
