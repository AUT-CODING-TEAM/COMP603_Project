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
        setEnabled(false);
        setFont(new Font("FACE_SYSTEM", Font.BOLD, 20));
        setOpaque(false);
        setBackground(Color.decode("#F8F6F1"));
    }
}
