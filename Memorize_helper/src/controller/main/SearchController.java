/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.main;

import java.awt.event.ActionEvent;
import javax.swing.*;
import model.*;

/**
 *
 * @author ThinkPad
 */
public class SearchController extends MainViewControllerTemplate {

    public SearchController(JFrame mainView, User user) {
        super(mainView, user);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("查询结果JFrame");
        mainView.dispose();
    }

}
