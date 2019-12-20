/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.prepare;

import controller.prepare.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import model.MyPlanInfo;
import view.myPlan.MyPlanPanel;
import view.planList.CreatePlanPanel;

/**
 *
 * @author ThinkPad
 */
public class LoginDialog extends JFrame {

    public LoginDialog() {
        setProperty();
        addComponents();
        
        setVisible(true);
    }

    private void setSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = 350;
        int frameHeight = 180;
        setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
    }

    private void setProperty() {
        setSize();
        setTitle("Sign In");
        setLayout(new GridLayout(3, 2));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
                
        });
    }
    

    private void addComponents() {
        JLabel lbl_lgD_usernameTip = new JLabel("Username:", SwingConstants.CENTER);
        add(lbl_lgD_usernameTip);
        JTextField tf_lgD_username = new JTextField();
        add(tf_lgD_username);
        
        JLabel lbl_lgD_passwordTip = new JLabel("Password:", SwingConstants.CENTER);
        add(lbl_lgD_passwordTip);
        JPasswordField tf_lgD_password = new JPasswordField();
        add(tf_lgD_password);
        
        JButton btn_lgD_register = new JButton("Sign Up First");
        btn_lgD_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterDialog();
                dispose();
            }
        });
        add(btn_lgD_register);
        
        JButton btn_lgD_ok = new JButton("Sign In");
        btn_lgD_ok.addActionListener(new LoginController(this, tf_lgD_username, tf_lgD_password));
        add(btn_lgD_ok);
        this.getRootPane().setDefaultButton(btn_lgD_ok);
    }
}
