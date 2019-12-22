import database.Database;
import java.util.Locale;
import javax.swing.JFrame;
import view.main.Loading;
import view.prepare.LoginDialog;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ThinkPad
 */
public class Start {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        //init the database
        Database db = Database.getInstance();
        
        db.init(new Loading());
        new LoginDialog();
    }
}
