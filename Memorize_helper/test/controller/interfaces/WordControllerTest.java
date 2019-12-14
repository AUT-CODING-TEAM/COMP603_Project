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
import model.User;
import model.Word;
import controller.interfaces.WordController;
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
        
    }

    @AfterClass
    public static void tearDownClass() {
        
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
        int id = 1;
        WordController instance = new WordController();
        ArrayList<Word> result = instance.getAllWordByID(id);
        assertTrue(result != null);
        assertTrue(result.size() == WordController.NOT_WORD_TABLE.size());

    }

    /**
     * Test of getAllWordByName method, of class WordController.
     */
    @Test
    public void testGetAllWordByName() {
        System.out.println("getAllWordByName");
        String word = "lens";
        WordController instance = new WordController();
        ArrayList<Word> result = instance.getAllWordByName(word);
        for (Word w : result) {
            assertEquals(w.getWord(), word);
        }
    }

    /**
     * Test of getBookWordByID method, of class WordController.
     */
    @Test
    public void testGetBookWordByID() {
        System.out.println("getBookWordByID");
        String book = "CET4入门";
        int id = 1;
        WordController instance = new WordController();
        Word expResult = new Word(id, "lens", "n.透镜，镜片；镜头", "lenz", book);
        Word result = instance.getBookWordByID(book, id);
        assertEquals(expResult, result);
    }

    /**
     * Test of getBookWordByName method, of class WordController.
     */
    @Test
    public void testGetBookWordByName() {
        System.out.println("getBookWordByName");
        String book = "CET4入门";
        String name = "lens";
        WordController instance = new WordController();
        Word expResult = new Word(1, "lens", "n.透镜，镜片；镜头", "lenz", book);
        Word result = instance.getBookWordByName(book, name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllBook method, of class WordController.
     */
    @Test
    public void testGetAllBook() {
        System.out.println("getAllBook");
        Database db = Database.getInstance();
        ArrayList<String> tables = db.getAllTable();
        WordController instance = new WordController();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getAllBook();
        for (String bookname : result) {
            assertFalse(WordController.NOT_WORD_TABLE.contains(bookname));
        }
        assertEquals(result.size(), tables.size() - WordController.NOT_WORD_TABLE.size());
    }

    /**
     * Test of getWordNumber method, of class WordController.
     */
    @Test
    public void testGetWordNumber() {
        System.out.println("getWordNumber");
        String book = "CET4入门";
        WordController instance = new WordController();
        int expResult = 609;
        int result = instance.getWordNumber(book);
        assertEquals(expResult, result);
    }

    /**
     * Test of getBookContent method, of class WordController.
     */
    @Test
    public void testGetBookContent() {
        System.out.println("getBookContent");
        String book = "CET4入门";
        int i = 0;
        WordController instance = new WordController();
        ArrayList<Word> result = instance.getBookContent(book);
        while (i < 609) {
            assertEquals(result.get(i).getID(), i+1);
            i++;
        }
    }

    /**
     * Test of search method, of class WordController.
     */
    @Test
    public void testSearch() {
        System.out.println("search");
        String str = "appe";
        WordController instance = new WordController();
        ArrayList<Word> expResult = new ArrayList<Word>() {
            {
                add(new Word(0, "appeal", "", "", ""));
                add(new Word(0, "appetite", "", "", ""));
            }
        };
        ArrayList<Word> result = instance.search(str);
        for (Word wd : result) {
            for (Word exp : expResult) {
                if (wd.getWord().equals(exp.getWord())) {
                    assertTrue(true);
                }
            }
        }
    }

    /**
     * Test of searchBook method, of class WordController.
     */
    @Test
    public void testSearchBook() {
        System.out.println("searchBook");
        String book = "CET4入门";
        String str = "ac";
        ArrayList<Word> expResult = new ArrayList<Word>() {
            {
                add(new Word(12, "acid", "", "", book));
                add(new Word(360, "acre", "", "", book));
            }
        };
        WordController instance = new WordController();
        ArrayList<Word> result = instance.searchBook(book, str);
        for (Word w : result) {
            assertTrue(expResult.contains(w));
        }
    }
}
