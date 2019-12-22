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

/**
 *
 * @author ThinkPad
 */
public class RegisterDialog extends JFrame {

    public RegisterDialog() {
        setProperty();
        addComponents();

        setVisible(true);
    }

    private void setSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = 400;
        int frameHeight = 240;
        setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
    }

    private void setProperty() {
        setSize();
        setTitle("Sign Up");
        setLayout(new GridLayout(4, 2));

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                RegisterDialog.this.dispose();
                new LoginDialog();
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

        JLabel confirmPwTipLabel = new JLabel("Confirm Password:", SwingConstants.CENTER);
        add(confirmPwTipLabel);
        JPasswordField rePwField = new JPasswordField();
        add(rePwField);

        JButton signInBtn = new JButton("Already have an account?");
        signInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginDialog();
                dispose();
            }
        });
        add(signInBtn);

        JButton signUpBtn = new JButton("Sign Up");
        signUpBtn.addActionListener(new RegisterController(this, usernameField, pwField, rePwField));
        add(signUpBtn);
        this.getRootPane().setDefaultButton(signUpBtn);
    }
}
