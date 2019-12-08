/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.prepare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.*;
import view.main.*;
import view.prepare.*;
import controller.interfaces.*;
import controller.myPlan.ShowPlanListController;
import view.planList.PlanListPanel;
/**
 *
 * @author ThinkPad
 */
public class LoginController implements ActionListener {

    private JDialog loginDialog;
    private JTextField tf_lgD_username;
    private JPasswordField tf_lgD_password;
    private User user;
    
    public LoginController(LoginDialog aThis, JTextField tf_lgD_username, JPasswordField tf_lgD_password) {
        this.loginDialog = aThis;
        this.tf_lgD_username = tf_lgD_username;
        this.tf_lgD_password = tf_lgD_password;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = tf_lgD_username.getText();
        String password = String.valueOf(tf_lgD_password.getPassword());

        int loginCheck = loginCheck(username, password);
        if (loginCheck == 1) {
            if (user.getCurrentStudyPlan() == null) {
                new PlanListPanel(user, new PlanListInfo(user));
            }
            else{
                new MainView(user);
            }
            loginDialog.dispose();
        } else if (loginCheck == 0) {
            JOptionPane.showMessageDialog(null, "用户名或密码错误！", "错误 ", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int loginCheck(String username, String password) {
        UserController uc = new UserController();
        User user = this.getUser(username);
        if(user == null){
            return 0;
        }
        
        boolean correct = uc.checkPassword(user,password);
        if(!correct){
            return 0;
        }
        
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        this.user = user;
        return 1;
    }

    private User getUser(String username) {
        UserController uc = new UserController();
        return uc.getUser(username);
    } 
}
