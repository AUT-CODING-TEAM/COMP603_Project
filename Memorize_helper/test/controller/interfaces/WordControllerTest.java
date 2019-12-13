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
public class WordControllerTest {
    
    public WordControllerTest() {
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
     * Test of getAllWordByID method, of class WordController.
     */
    @Test
    public void testGetAllWordByID() {
        System.out.println("getAllWordByID");
        int id = 0;
        WordController instance = new WordController();
        ArrayList<Word> expResult = null;
        ArrayList<Word> result = instance.getAllWordByID(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllWordByName method, of class WordController.
     */
    @Test
    public void testGetAllWordByName() {
        System.out.println("getAllWordByName");
        String word = "";
        WordController instance = new WordController();
        ArrayList<Word> expResult = null;
        ArrayList<Word> result = instance.getAllWordByName(word);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBookWordByID method, of class WordController.
     */
    @Test
    public void testGetBookWordByID() {
        System.out.println("getBookWordByID");
        String book = "";
        int id = 0;
        WordController instance = new WordController();
        Word expResult = null;
        Word result = instance.getBookWordByID(book, id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBookWordByName method, of class WordController.
     */
    @Test
    public void testGetBookWordByName() {
        System.out.println("getBookWordByName");
        String book = "";
        String name = "";
        WordController instance = new WordController();
        Word expResult = null;
        Word result = instance.getBookWordByName(book, name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllBook method, of class WordController.
     */
    @Test
    public void testGetAllBook() {
        System.out.println("getAllBook");
        WordController instance = new WordController();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getAllBook();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWordNumber method, of class WordController.
     */
    @Test
    public void testGetWordNumber() {
        System.out.println("getWordNumber");
        String book = "";
        WordController instance = new WordController();
        int expResult = 0;
        int result = instance.getWordNumber(book);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBookContent method, of class WordController.
     */
    @Test
    public void testGetBookContent() {
        System.out.println("getBookContent");
        String book = "";
        WordController instance = new WordController();
        ArrayList<Word> expResult = null;
        ArrayList<Word> result = instance.getBookContent(book);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of search method, of class WordController.
     */
    @Test
    public void testSearch() {
        System.out.println("search");
        String str = "";
        WordController instance = new WordController();
        ArrayList<Word> expResult = null;
        ArrayList<Word> result = instance.search(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchBook method, of class WordController.
     */
    @Test
    public void testSearchBook() {
        System.out.println("searchBook");
        String book = "";
        String str = "";
        WordController instance = new WordController();
        ArrayList<Word> expResult = null;
        ArrayList<Word> result = instance.searchBook(book, str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class WordController.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        WordController.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
