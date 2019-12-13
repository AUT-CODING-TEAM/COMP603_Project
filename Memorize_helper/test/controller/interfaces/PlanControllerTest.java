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
    
    public PlanControllerTest() {
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
     * Test of getPlan method, of class PlanController.
     */
    @Test
    public void testGetPlan_int() {
        System.out.println("getPlan");
        int pid = 0;
        PlanController instance = new PlanController();
        StudyPlan expResult = null;
        StudyPlan result = instance.getPlan(pid);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlan method, of class PlanController.
     */
    @Test
    public void testGetPlan_User_String() {
        System.out.println("getPlan");
        User user = null;
        String book = "";
        PlanController instance = new PlanController();
        StudyPlan expResult = null;
        StudyPlan result = instance.getPlan(user, book);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlanIdByBook method, of class PlanController.
     */
    @Test
    public void testGetPlanIdByBook() {
        System.out.println("getPlanIdByBook");
        User user = null;
        String book = "";
        PlanController instance = new PlanController();
        int expResult = 0;
        int result = instance.getPlanIdByBook(user, book);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlan method, of class PlanController.
     */
    @Test
    public void testGetPlan_User() {
        System.out.println("getPlan");
        User user = null;
        PlanController instance = new PlanController();
        StudyPlan expResult = null;
        StudyPlan result = instance.getPlan(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllPlanByUser method, of class PlanController.
     */
    @Test
    public void testGetAllPlanByUser() {
        System.out.println("getAllPlanByUser");
        User user = null;
        PlanController instance = new PlanController();
        ArrayList<StudyPlan> expResult = null;
        ArrayList<StudyPlan> result = instance.getAllPlanByUser(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isPlan method, of class PlanController.
     */
    @Test
    public void testIsPlan() {
        System.out.println("isPlan");
        User user = null;
        String book = "";
        PlanController instance = new PlanController();
        boolean expResult = false;
        boolean result = instance.isPlan(user, book);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPlan method, of class PlanController.
     */
    @Test
    public void testAddPlan_3args_1() {
        System.out.println("addPlan");
        String book = "";
        int everyday_num = 0;
        int user_id = 0;
        PlanController instance = new PlanController();
        int expResult = 0;
        int result = instance.addPlan(book, everyday_num, user_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPlan method, of class PlanController.
     */
    @Test
    public void testAddPlan_3args_2() {
        System.out.println("addPlan");
        int total_day = 0;
        String book = "";
        int user_id = 0;
        PlanController instance = new PlanController();
        int expResult = 0;
        int result = instance.addPlan(total_day, book, user_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPlan method, of class PlanController.
     */
    @Test
    public void testSetPlan() {
        System.out.println("setPlan");
        User user = null;
        int pid = 0;
        PlanController instance = new PlanController();
        instance.setPlan(user, pid);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalWordNum method, of class PlanController.
     */
    @Test
    public void testGetTotalWordNum() {
        System.out.println("getTotalWordNum");
        StudyPlan plan = null;
        PlanController instance = new PlanController();
        int expResult = 0;
        int result = instance.getTotalWordNum(plan);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFinishWordNum method, of class PlanController.
     */
    @Test
    public void testGetFinishWordNum_int_StudyPlan() {
        System.out.println("getFinishWordNum");
        int id = 0;
        StudyPlan plan = null;
        PlanController instance = new PlanController();
        int expResult = 0;
        int result = instance.getFinishWordNum(id, plan);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFinishWordNum method, of class PlanController.
     */
    @Test
    public void testGetFinishWordNum_User() {
        System.out.println("getFinishWordNum");
        User user = null;
        PlanController instance = new PlanController();
        int expResult = 0;
        int result = instance.getFinishWordNum(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRemainWordNum method, of class PlanController.
     */
    @Test
    public void testGetRemainWordNum() {
        System.out.println("getRemainWordNum");
        int id = 0;
        StudyPlan plan = null;
        PlanController instance = new PlanController();
        int expResult = 0;
        int result = instance.getRemainWordNum(id, plan);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlanName method, of class PlanController.
     */
    @Test
    public void testGetPlanName() {
        System.out.println("getPlanName");
        StudyPlan plan = null;
        PlanController instance = new PlanController();
        String expResult = "";
        String result = instance.getPlanName(plan);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateTodayPlanInfo method, of class PlanController.
     */
    @Test
    public void testUpdateTodayPlanInfo() {
        System.out.println("updateTodayPlanInfo");
        User user = null;
        PlanController instance = new PlanController();
        instance.updateTodayPlanInfo(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTodayMemorizedNum method, of class PlanController.
     */
    @Test
    public void testGetTodayMemorizedNum() {
        System.out.println("getTodayMemorizedNum");
        User user = null;
        PlanController instance = new PlanController();
        int expResult = 0;
        int result = instance.getTodayMemorizedNum(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTodayReviewedNum method, of class PlanController.
     */
    @Test
    public void testGetTodayReviewedNum() {
        System.out.println("getTodayReviewedNum");
        User user = null;
        PlanController instance = new PlanController();
        int expResult = 0;
        int result = instance.getTodayReviewedNum(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
