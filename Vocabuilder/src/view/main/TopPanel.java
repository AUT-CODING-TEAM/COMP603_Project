package view.main;

import controller.main.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import model.*;
import view.*;
import view.searchResultList.SearchResultListPanel;

/**
 *
 * @author ThinkPad, Pingchuan.Huang
 */
public class TopPanel extends MainViewViewTemplate {

    private JPanel searchPanel;
    private JTextField keywordField;

    public TopPanel(JFrame mainView, User user) {
        super(mainView, user);
    }

    @Override
    public void setProperty() {
        setLayout(new GridBagLayout());
        setOpaque(true);
        setBackground(Color.decode("#EEECE8"));
    }

    @Override
    public void addComponents() {
        //left fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.05).setWeighty(1));
        //right fill label
        add(new JLabel(), new GridBagTool().setGridx(4).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.05).setWeighty(1));
        //middle fill label
        add(new JLabel(), new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.3).setWeighty(1));

        addProfilePanel();

        addSearchPanel();
    }

    private void addProfilePanel() {
        JPanel profilePanel = new GroundPanelTemplate(GroundPanelTemplate.BACK);

        JLabel lbl_tP_username = new JLabel("Kia ora, " + user.getUsername());
        profilePanel.add(lbl_tP_username);

        JButton btn_tP_rankList = new JButton("Rankings");
        btn_tP_rankList.addActionListener(new ShowRankListController(mainView, user));
        profilePanel.add(btn_tP_rankList);

        add(profilePanel, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.WEST).setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.3).setWeighty(1));

    }

    private void addSearchPanel() {
        searchPanel = new GroundPanelTemplate(GroundPanelTemplate.BACK);

        JLabel lbl_tP_searchTip = new JLabel("Look Up");
        searchPanel.add(lbl_tP_searchTip);

        keywordField = new JTextField(15);
        keywordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String str = keywordField.getText();
                new SearchResultListPanel(str, user);
                mainView.dispose();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        searchPanel.add(keywordField);

        add(searchPanel, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.EAST).setGridx(3).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.3).setWeighty(1));
    }

    public JTextField getKeywordField() {
        return keywordField;

    }

}
