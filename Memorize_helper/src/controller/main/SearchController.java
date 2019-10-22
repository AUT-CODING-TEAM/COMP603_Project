/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author ThinkPad
 */
public class SearchController implements ActionListener {

    private JFrame mainView;
    private String word;

    public SearchController(JFrame mainView, String word) {
        this.mainView = mainView;
        this.word = word;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(word + "查询结果JFrame");
        mainView.setVisible(false);
    }

}
