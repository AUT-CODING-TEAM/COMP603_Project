/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.main;

import javax.swing.*;
import model.*;

/**
 *
 * @author ThinkPad
 */
public abstract class MainViewViewTemplate extends JPanel{
    protected JFrame mainView;
    protected User user;
    
    public MainViewViewTemplate(JFrame mainView, User user){
        this.mainView = mainView;
        this.user = user;
        setProperty();
        addComponents();
    }

    public abstract void setProperty();

    public abstract void addComponents();
    
}
