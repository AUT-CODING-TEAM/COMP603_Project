/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JList;

/**
 *
 * @author ThinkPad
 */
public class ListInScrollTemplate extends JList {

    public ListInScrollTemplate(String[] s) {
        super(s);
//        setBorder(new TitledBorder(""));
        setEnabled(false);
        setFont(new Font("FACE_SYSTEM", Font.BOLD, 20));
        setOpaque(false);
        setBackground(Color.decode("#F8F6F1"));
    }
}
