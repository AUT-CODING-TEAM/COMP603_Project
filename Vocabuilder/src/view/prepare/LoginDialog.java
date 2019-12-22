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
        JLabel usernameTipLabel = new JLabel("Username:", SwingConstants.CENTER);
        add(usernameTipLabel);
        JTextField usernameField = new JTextField();
        add(usernameField);

        JLabel pwTipLabel = new JLabel("Password:", SwingConstants.CENTER);
        add(pwTipLabel);
        JPasswordField pwField = new JPasswordField();
        add(pwField);

        JButton signUpBtn = new JButton("Sign Up First");
        signUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterDialog();
                dispose();
            }
        });
        add(signUpBtn);

        JButton signInBtn = new JButton("Sign In");
        signInBtn.addActionListener(new LoginController(this, usernameField, pwField));
        add(signInBtn);
        this.getRootPane().setDefaultButton(signInBtn);
    }
}
