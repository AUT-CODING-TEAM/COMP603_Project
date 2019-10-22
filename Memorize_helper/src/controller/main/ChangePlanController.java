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
public class ChangePlanController extends MainViewControllerTemplate {

    public ChangePlanController(JFrame mainView, User user) {
        super(mainView, user);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("修改计划JFrame");
        mainView.dispose();
    }
}
