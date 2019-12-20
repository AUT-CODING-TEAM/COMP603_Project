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

/**
 *
 * @author Yun_c, Pingchuan
 */
public class WordController {

    public static final ArrayList<String> NOT_WORD_TABLE = new ArrayList<String>() {
        {
            add("USERS");
            add("MEMORIZE");
            add("PLAN");
            add("COLLECTION");
        }
    };

    /**
     * @param id word's id
     * @return the words whose ID is param.id in all books
     */
    public ArrayList<Word> getAllWordByID(int id) {
        Database db = Database.getInstance();
        ArrayList<String> table = this.getAllBook();
        ArrayList<Word> result = new ArrayList<Word>();
        for (String book : table) {
            result.add(this.getBookWordByID(book, id));
        }
        return result;
    }

    /**
     * @param word the content of a word
     * @return the words whose content is param.word in all books
     */
    public ArrayList<Word> getAllWordByName(String word) {
        Database db = Database.getInstance();
        ArrayList<String> table = this.getAllBook();
        ArrayList<Word> result = new ArrayList<Word>();
        for (String book : table) {
            Word wd = this.getBookWordByName(book, word);
            if (wd != null) {
                result.add(wd);
            }

        }
        return result;
    }

    /**
     * @param book which book user want to get
     * @param id the word's id
     * @return the word whose ID is param.id and belongs to param.book
     */
    public Word getBookWordByID(String book, int id) {
        Database db = Database.getInstance();
        ResultSet res = db.get(book, "ID", id);
        Word wd = null;
        try {
            if (!res.next()) {
                return wd;
            }
            String word = res.getString("WORD");
            String chinese = res.getString("CHINESE");
            String phonetic = res.getString("PHONETIC");
            wd = new Word(id, word, chinese, phonetic, book);

        } catch (SQLException ex) {
            Logger.getLogger(WordController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wd;
    }

    /**
     * @param book which book user want to get
     * @param name the word's content
     * @return the word whose content is param.name and belongs to param.book
     */
    public Word getBookWordByName(String book, String name) {
        Database db = Database.getInstance();
        ResultSet res = db.get(book, "WORD", name);
        Word wd = null;
        try {
            if (!res.next()) {
                return wd;
            }
            int id = res.getInt("ID");
            String chinese = res.getString("CHINESE");
            String phonetic = res.getString("PHONETIC");
            wd = new Word(id, name, chinese, phonetic, book);

        } catch (SQLException ex) {
            Logger.getLogger(WordController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wd;
    }

    /**
     * @return all exist book names
     */
    public ArrayList<String> getAllBook() {
        Database db = Database.getInstance();
        ArrayList<String> books = db.getAllTable();
        for (String tb : WordController.NOT_WORD_TABLE) {
            books.remove(tb);
        }
        return books;
    }

    /**
     * @param book book name
     * @return the number of word in a book
     */
    public int getWordNumber(String book) {
        Database db = Database.getInstance();
        int num = db.count(book);
        return num;
    }

    /**
     * @param book which book user want to get
     * @return all words in the param.book
     */
    public ArrayList<Word> getBookContent(String book) {
        Database db = Database.getInstance();
        ResultSet res = db.get(book, "", "");
        ArrayList<Word> wordCollection = new ArrayList<Word>();
        try {
            while (res.next()) {
                int id = res.getInt("ID");
                String word = res.getString("WORD");
                String chinese = res.getString("CHINESE");
                String phonetic = res.getString("PHONETIC");
                Word wd = new Word(id, word, chinese, phonetic, book);
                wordCollection.add(wd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WordController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wordCollection;
    }

    /**
     * @param str part of the word content
     * @return the words which contain param.str in all books
     */
    public ArrayList<Word> search(String str) {
        Database db = Database.getInstance();
        ArrayList<Word> wordCollection = new ArrayList<Word>();
        ArrayList<String> books = this.getAllBook();
        for (String book : books) {
            ArrayList<Word> bookwords = this.searchBook(book, str);
            wordCollection.addAll(bookwords);
        }
        return wordCollection;
    }

    /**
     * @param book which book user want to search
     * @param str part of the word content
     * @return the words which contain param.str in param.book
     */
    public ArrayList<Word> searchBook(String book, String str) {
        Database db = Database.getInstance();
        ResultSet res = db.search(book, "word", str);
        ArrayList<Word> wordCollection = new ArrayList<Word>();
        try {
            while (res.next()) {
                int id = res.getInt("ID");
                String word = res.getString("WORD");
                String chinese = res.getString("CHINESE");
                String phonetic = res.getString("PHONETIC");
                Word wd = new Word(id, word, chinese, phonetic, book);
                wordCollection.add(wd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WordController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wordCollection;
    }

    public static void main(String[] args) {
        WordController wct = new WordController();
        Word word1 = wct.getBookWordByID("CET4", 2);
        Word word2 = wct.getBookWordByName("CET4", "about");
        ArrayList<Word> word3 = wct.getAllWordByID(4);
        ArrayList<Word> word4 = wct.getAllWordByName("abuse");
        ArrayList<Word> word5 = wct.searchBook("CET4", "abs");
        ArrayList<String> books = wct.getAllBook();
        System.out.println("All books: ");
        for (String book : books) {
            System.out.println(book);
        }
        System.out.println(word1);
        System.out.println("");
        System.out.println(word2);
        System.out.println("");
        for (Word wd : word3) {
            System.out.println(wd);
        }
        System.out.println("");
        for (Word wd : word4) {
            System.out.println(wd);
        }
        System.out.println("");
        for (Word wd : word5) {
            System.out.println(wd);
        }
    }
}
