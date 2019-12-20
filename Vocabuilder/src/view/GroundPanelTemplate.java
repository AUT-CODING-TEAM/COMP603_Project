/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author ThinkPad
 */
public class GroundPanelTemplate extends JPanel{
    public static final int FORE = 0;
    public static final int BACK = 1;
    
    public  GroundPanelTemplate(int mode){
        setOpaque(true);
        if (mode == 0) {
//            setBackground(Color.decode("#F8F6F1"));
            setBackground(new Color(248,246,241));
        }
        else if (mode == 1) {
//            setBackground(Color.decode("#EEECE8"));
            setBackground(new Color(238,236,232));
        }
        
    }
    
    protected void setSize(JFrame jFrame, int frameWidth, int frameHeight) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        jFrame.setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
    }
}
