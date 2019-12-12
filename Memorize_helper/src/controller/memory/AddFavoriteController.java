/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.memory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.User;

/**
 *
 * @author ThinkPad
 */
public class AddFavoriteController implements ActionListener{
    private User user;
    private String word;

    public AddFavoriteController(User user, String word) {
        this.user = user;
        this.word = word;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Successfully Added to Favorites!", "information ", JOptionPane.INFORMATION_MESSAGE);
        System.out.println(word);
    }
}
