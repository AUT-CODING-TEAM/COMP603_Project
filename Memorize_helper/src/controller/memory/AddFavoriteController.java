/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.memory;

import controller.interfaces.CollectionController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.User;
import model.Word;

/**
 *
 * @author ThinkPad
 */
public class AddFavoriteController implements ActionListener{
    private User user;
    private Word word;
    private boolean favorited;

    public AddFavoriteController(User user, Word word, boolean favorited) {
        this.user = user;
        this.word = word;
        this.favorited = favorited;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        JOptionPane.showMessageDialog(null, "Successfully Added to Favorites!", "information ", JOptionPane.INFORMATION_MESSAGE);
        try {
            CollectionController cc = new CollectionController();
            cc.collectWord(user, word);
            JButton favorite = (JButton)e.getSource();
            if (!cc.hasCollected(user, word)) {
                favorite.setText("Add to Favorite");
            }
            else{
                favorite.setText("Remove from Favorite");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
