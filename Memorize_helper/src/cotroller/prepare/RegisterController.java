/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cotroller.prepare;

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
        char[] password = tf_rgD_password.getPassword();
        char[] pwdConfirm = tf_rgD_pwdConfirm.getPassword();

        int registerCheck = registerCheck(username, password, pwdConfirm);
        if (registerCheck == 1) {
            JOptionPane.showMessageDialog(null, "注册成功！", "提示", JOptionPane.PLAIN_MESSAGE);
            new LoginDialog();
            registerDialog.dispose();
        } else if (registerCheck == 0) {
            JOptionPane.showMessageDialog(null, "注册失败！", "错误 ", JOptionPane.ERROR_MESSAGE);
        }

    }

    private int registerCheck(String username, char[] password, char[] pwdConfirm) {
        System.out.println("username = " + username);
        for (char c : password) {
            System.out.print(c);
        }
        System.out.println();
        for (char c : pwdConfirm) {
            System.out.print(c);
        }
        return 1;
    }
}
