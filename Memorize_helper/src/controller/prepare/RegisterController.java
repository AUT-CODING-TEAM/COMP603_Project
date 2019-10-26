/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.prepare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import view.prepare.*;

/**
 *
 * @author ThinkPad
 */
public class RegisterController implements ActionListener {

    private JDialog registerDialog;
    private JTextField tf_rgD_username;
    private JPasswordField tf_rgD_password;
    private JPasswordField tf_rgD_pwdConfirm;

    public RegisterController(RegisterDialog aThis, JTextField tf_rgD_username, JPasswordField tf_rgD_password, JPasswordField tf_rgD_pwdConfirm) {
        this.registerDialog = aThis;
        this.tf_rgD_username = tf_rgD_username;
        this.tf_rgD_password = tf_rgD_password;
        this.tf_rgD_pwdConfirm = tf_rgD_pwdConfirm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = tf_rgD_username.getText();
        String password = String.valueOf(tf_rgD_password.getPassword());
        String pwdConfirm = String.valueOf(tf_rgD_pwdConfirm.getPassword());

        int registerCheck = registerCheck(username, password, pwdConfirm);
        if (registerCheck == 1) {
            JOptionPane.showMessageDialog(null, "注册成功！", "提示",
                    JOptionPane.PLAIN_MESSAGE);
            new LoginDialog();
            registerDialog.dispose();
        } else if (registerCheck == 0) {
            JOptionPane.showMessageDialog(null, "注册失败！", "错误 ",
                    JOptionPane.ERROR_MESSAGE);
        } else if(registerCheck == -1){
            JOptionPane.showMessageDialog(null, "两次密码不一致，或密码为空",
                    "错误 ", JOptionPane.ERROR_MESSAGE);
        }

    }

    private int registerCheck(String username, String password, String pwdConfirm) {
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("pwdConfirm = " + pwdConfirm);
        return 1;
    }
}
