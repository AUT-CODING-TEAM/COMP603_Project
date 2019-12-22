package view.searchResultList;

import controller.WordDetailController;
import controller.main.SearchController;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import model.*;
import view.*;
import view.main.MainView;

/**
 *
 * @author ThinkPad, Pingchuan.Huang
 */
public class SearchResultListPanel extends GroundPanelTemplate {

    private JFrame searchResultListFrame;
    private JList searchRsList;
    private String inputKeyWord;
    private User user;
    private JTextField keywordField;

    public SearchResultListPanel(String inputKeyWord, User user) {
        super(GroundPanelTemplate.BACK);
        this.inputKeyWord = inputKeyWord;
        this.user = user;
        setProperty();
        addComponents();
    }

    public void setProperty() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        searchResultListFrame = new JFrame("Look Up");
        setSize(searchResultListFrame, 720, 720);
        searchResultListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchResultListFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                backToMain();
            }
        });

        //left fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(4).setWeightx(0.05).setWeighty(1));
        //right fill label
        add(new JLabel(), new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(4).setWeightx(0.05).setWeighty(1));
        //top fill label
        add(new JLabel(), new GridBagTool().setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
        //bottom fill label
        add(new JLabel(), new GridBagTool().setGridx(1).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        addSearchPanel();

        addSearchResultListPanel();

        searchResultListFrame.add(this);

        this.searchResultListFrame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                SearchResultListPanel.this.keywordField.requestFocus();
            }
        });

        searchResultListFrame.setVisible(true);
    }

    private void addSearchPanel() {
        JPanel searchPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        searchPanel.setLayout(new GridLayout(3, 1));

        JPanel jPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        JLabel searchTip = new JLabel("Look Up");
        jPanel.add(searchTip);

        keywordField = new JTextField(20);
        keywordField.getDocument().addDocumentListener(new SearchController(this));
        jPanel.add(keywordField);

        searchPanel.add(new JLabel());
        searchPanel.add(jPanel);
        searchPanel.add(new JLabel());

        add(searchPanel, new GridBagTool().setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.01));
    }

    public void addSearchResultListPanel() {
        JPanel jPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        jPanel.setLayout(new GridLayout(1, 1));

        searchRsList = new ListInScrollTemplate(new SearchResultInfo(inputKeyWord).getSearchResultListInfo());
        searchRsList.setEnabled(true);
        searchRsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchRsList.addListSelectionListener(new WordDetailController(user, searchRsList, searchResultListFrame));
        jPanel.add(searchRsList);

        this.keywordField.setCaret(new HighlightCaret());
        JScrollPane jScrollPane = new JScrollPane(jPanel);
        add(jScrollPane, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.89));

        this.keywordField.setText(inputKeyWord);
    }

    public JFrame getSearchResultListFrame() {
        return searchResultListFrame;
    }

    public JList getSearchRsList() {
        return searchRsList;
    }

    public JTextField getKeywordField() {
        return keywordField;
    }

    public void backToMain() {
        new MainView(user);
    }
}

class HighlightCaret extends DefaultCaret {

    private static final Highlighter.HighlightPainter unfocusedPainter
            = new DefaultHighlighter.DefaultHighlightPainter(new Color(230, 230, 210));
    private static final long serialVersionUID = 1L;
    private boolean isFocused;

    @Override
    protected Highlighter.HighlightPainter getSelectionPainter() {
        return isFocused ? super.getSelectionPainter() : unfocusedPainter;
    }

    @Override
    public void setSelectionVisible(boolean hasFocus) {
        if (hasFocus != isFocused) {
            isFocused = hasFocus;
            super.setSelectionVisible(false);
            super.setSelectionVisible(true);
        }
    }
}
