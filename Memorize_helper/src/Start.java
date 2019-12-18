import database.Database;
import java.util.Locale;
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
        db.init();
        new LoginDialog();
    }
}
