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

    public AddFavoriteController(User user, Word word) {
        this.user = user;
        this.word = word;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Successfully Added to Favorites!", "information ", JOptionPane.INFORMATION_MESSAGE);
        try {
            new CollectionController().collectWord(user, word);
        } catch (SQLException ex) {
            Logger.getLogger(AddFavoriteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(word);
    }
}
