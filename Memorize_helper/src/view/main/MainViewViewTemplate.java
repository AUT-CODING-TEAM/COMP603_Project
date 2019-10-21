/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import model.User;

/**
 *
 * @author ThinkPad
 */
public class MainViewViewTemplate extends JPanel{
    protected JFrame mainView;
    protected User user;
    
    public MainViewViewTemplate(JFrame mainView, User user){
        this.mainView = mainView;
        this.user = user;
        setProperty();
        addComponents();
    }

    public void setProperty() {
    }

    public void addComponents() {
        
    }
}
