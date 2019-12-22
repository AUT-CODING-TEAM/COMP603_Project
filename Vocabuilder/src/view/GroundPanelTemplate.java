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
            setBackground(new Color(248,246,241));
        }
        else if (mode == 1) {
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
