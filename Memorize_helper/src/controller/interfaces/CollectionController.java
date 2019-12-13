package controller.interfaces;

import database.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.User;
import model.Word;

/**
 *
 * @author pingchuan.huang
 *
 * This class handles functionalities of collecting vocabularies.
 *
 */
public class CollectionController {

    /**
     *
     * @param user who collects the word.
     * @param word which user wants to collect.
     * @throws SQLException
     */
    public void collectWord(User user, Word word) throws SQLException {
        String userId = user.getID() + "";
        String wordId = word.getID() + "";
        String wordSource = word.getSource();

        if (!this.hasCollected(user, word)) {
            Database db = Database.getInstance();
            db.add(
                    "COLLECTION",
                    new String[]{
                        "USER_ID",
                        "WORD_ID",
                        "WORD_SOURCE"
                    },
                    new String[]{
                        userId,
                        wordId,
                        wordSource
                    }
            );
        } else {
            this.uncollectWord(user, word);
        }
    }

    /**
     *
     * @param user who unCollects the word.
     * @param word which user wants to unCollect.
     */
    private void uncollectWord(User user, Word word) {
        Database db = Database.getInstance();
        String userId = user.getID() + "";
        String wordId = word.getID() + "";
        String wordSource = word.getSource();
        String sqlPrepared = "delect from COLLECTION where USER_ID = ? and WORD_ID = ? and WORD_SOURCE = ?";
        db.prepare(sqlPrepared, userId, wordId, wordSource);
    }

    /**
     *
     * @param user that triggered this method.
     * @param word that need to be check whether had been collected by user.
     * @return if contains, true; else false;
     * @throws SQLException
     */
    public boolean hasCollected(User user, Word word) throws SQLException {
        Database db = Database.getInstance();
        String userId = user.getID() + "";
        String wordId = word.getID() + "";
        String wordSource = word.getSource();

        ResultSet rs = db.get(
                "COLLECTION",
                new String[]{
                    "USER_ID",
                    "WORD_ID",
                    "WORD_SOURCE"
                },
                new String[]{
                    userId,
                    wordId,
                    wordSource
                }
        );

        return rs.next();
    }

    /**
     * 
     * @param user
     * @return words that have been added to favorite in current studyPlan.
     * @throws SQLException 
     */
    public ArrayList<Word> getCollectedWords(User user) throws SQLException {
        ArrayList<Word> words = new ArrayList<>();
        Database db = Database.getInstance();
        String userId = user.getID() + "";
        String wordSource = user.getCurrentStudyPlan().getStudyPlanName();
//        ResultSet rs =db.get(
//                "COLLECTION",
//                new String[]{
//                    "USER_ID",
//                    "WORD_SOURCE"
//                },
//                new String[]{
//                    userId,
//                    wordSource
//                }
//        );
        StringBuilder sb = new StringBuilder();
        sb.append("select ")
                .append(wordSource)
                .append(".* from COLLECTION,")
                .append(wordSource)
                .append(" where COLLECTION.WORD_SOURCE = ? and COLLECTION.USER_ID = ? and ")
                .append(wordSource)
                .append(".ID = int(COLLECTION.WORD_ID)");
        
        ResultSet rs = db.prepare(sb.toString(), wordSource, userId);
        
        
        while(rs.next()){
            words.add(new Word(
                    rs.getInt("ID"),
                    rs.getString("WORD"),
                    rs.getString("CHINESE"),
                    rs.getString("PHONETIC"),
                    wordSource
            ));
        } 
        
        return words;
    }
}
