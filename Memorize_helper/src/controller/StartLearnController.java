/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.main.MainViewControllerTemplate;
import java.awt.event.ActionEvent;
import javax.swing.*;
import model.*;
import view.memory.*;

/**
 *
 * @author ThinkPad
 */
public class StartLearnController extends MainViewControllerTemplate{

    public StartLearnController(JFrame mainView, User user) {
        super(mainView, user);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("开始背单词JFrame");
        new MemoryPanel(new MemoryPage(), user);
        mainView.dispose();
    }
}
