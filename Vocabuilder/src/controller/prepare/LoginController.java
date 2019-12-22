/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.prepare;

import controller.interfaces.UserController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.PlanListInfo;
import model.User;
import view.main.MainView;
import view.planList.PlanListPanel;
/**
 *
 * @author ThinkPad
 */
public class LoginController implements ActionListener {

    private JFrame loginDialog;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private User user;
    
    public LoginController(JFrame aThis, JTextField username, JPasswordField password) {
        this.loginDialog = aThis;
        this.usernameTextField = username;
        this.passwordField = password;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameTextField.getText();
        String password = String.valueOf(passwordField.getPassword());

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
            JOptionPane.showMessageDialog(null, "Incorrect Username or Password!", "error ", JOptionPane.ERROR_MESSAGE);
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
        
        this.user = user;
        return 1;
    }

    private User getUser(String username) {
        UserController uc = new UserController();
        return uc.getUser(username);
    } 
}
