/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.User;
import view.main.MainView;

/**
 *
 * @author ThinkPad
 */
public class WordDetailController implements ListSelectionListener {

    private User user;
    private JList list_sP_searchResultList;

    public WordDetailController(User user, JList list_sP_searchResultList) {
        this.user = user;
        this.list_sP_searchResultList = list_sP_searchResultList;

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (list_sP_searchResultList.getValueIsAdjusting()) {
            String word = list_sP_searchResultList.getSelectedValue().toString().trim();
            System.out.println(word + "详细页面JFrame");
            //there is no mainView exists from search
            //new MainView(user);
        }
    }
}
