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

        JLabel lbl_rgD_usernameTip = new JLabel("Username:", SwingConstants.CENTER);
        add(lbl_rgD_usernameTip);
        JTextField tf_rgD_username = new JTextField();
        add(tf_rgD_username);

        JLabel lbl_rgD_passwordTip = new JLabel("Password:", SwingConstants.CENTER);
        add(lbl_rgD_passwordTip);
        JPasswordField tf_rgD_password = new JPasswordField();
        add(tf_rgD_password);

        JLabel lbl_rgD_pwdConfirmTip = new JLabel("Confirm Password:", SwingConstants.CENTER);
        add(lbl_rgD_pwdConfirmTip);
        JPasswordField tf_rgD_pwdConfirm = new JPasswordField();
        add(tf_rgD_pwdConfirm);

        JButton btn_rgD_login = new JButton("Already have an account?");
        btn_rgD_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginDialog();
                dispose();
            }
        });
        add(btn_rgD_login);

        JButton btn_rgD_ok = new JButton("Sign Up");
        btn_rgD_ok.addActionListener(new RegisterController(this, tf_rgD_username, tf_rgD_password, tf_rgD_pwdConfirm));
        add(btn_rgD_ok);
        this.getRootPane().setDefaultButton(btn_rgD_ok);
    }
}
