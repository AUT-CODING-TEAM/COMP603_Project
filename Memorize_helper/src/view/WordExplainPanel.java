/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import model.WordExplainPage;

/**
 *
 * @author ThinkPad
 */
public class WordExplainPanel extends GroundPanelTemplate {

    private WordExplainPage wordExplainPage;
    private JFrame sourceFrame;

    public WordExplainPanel(WordExplainPage wordExplainPage, JFrame sourceFrame) {
        super(GroundPanelTemplate.BACK);
        this.wordExplainPage = wordExplainPage;
        this.sourceFrame = sourceFrame;
        setProperty();
        addComponents();
    }

    public void setProperty() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        JFrame wordExplainFrame = new JFrame("单词释义");
        setSize(wordExplainFrame, 720, 360);
        wordExplainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        wordExplainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e);
                wordExplainFrame.dispose();
                sourceFrame.setVisible(true);
            }
        });

        JLabel lbl_wEP_word = new JLabel(wordExplainPage.getWord(), SwingConstants.CENTER);
        lbl_wEP_word.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 40));
        add(lbl_wEP_word, new GridBagTool().setFill(GridBagConstraints.NONE).setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.4));

        JLabel lbl_wEP_phoneticSymbol = new JLabel(wordExplainPage.getPhoneticSymbol(), SwingConstants.CENTER);
        lbl_wEP_phoneticSymbol.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 20));
        add(lbl_wEP_phoneticSymbol, new GridBagTool().setFill(GridBagConstraints.NONE).setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.3));

        JLabel lbl_wEP_Chinese = new JLabel(wordExplainPage.getChinese(), SwingConstants.CENTER);
        lbl_wEP_Chinese.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 20));
        add(lbl_wEP_Chinese, new GridBagTool().setFill(GridBagConstraints.NONE).setGridx(0).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.3));

        wordExplainFrame.add(this);
        wordExplainFrame.setVisible(true);
    }
}
