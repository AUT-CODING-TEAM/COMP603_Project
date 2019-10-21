/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cotroller.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.*;

/**
 *
 * @author ThinkPad
 */
public class MainViewControllerTemplate implements ActionListener {
    protected JFrame mainView;
    protected User user;
    
    public MainViewControllerTemplate(JFrame mainView, User user) {
        this.mainView = mainView;
        this.user = user;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
