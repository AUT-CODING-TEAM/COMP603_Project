package view.memory;

import controller.WordDetailController;
import controller.interfaces.CollectionController;
import controller.memory.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Random;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import model.*;
import view.*;
import view.main.MainView;

/**
 *
 * @author ThinkPad
 * @author Pingchuan
 * @author Yizhao
 */
public class MemoryPanel extends GroundPanelTemplate {

    private MemoryPage memoryPage;
    private User user;
    private MemoryRecorder memoryRecorder;
    private JFrame memoryFrame;
    private boolean isChineseChoices;

    public MemoryPanel(User user, MemoryPage memoryPage, MemoryRecorder memoryRecorder) {
        super(GroundPanelTemplate.BACK);
        this.user = user;
        this.memoryRecorder = memoryRecorder;
        this.memoryPage = memoryPage;
        this.isChineseChoices = new Random().nextBoolean();
        setProperty();
        addComponents();
    }

    public MemoryRecorder getMemoryRecorder() {
        return memoryRecorder;
    }

    public MemoryPage getMemoryPage() {
        return memoryPage;
    }

    public JFrame getMemoryFrame() {
        return memoryFrame;
    }

    public void setProperty() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        memoryFrame = new JFrame("Studying");
        setSize(memoryFrame, 720, 720);
        memoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        memoryFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new MainView(user);
            }
        });

        //left fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(7).setWeightx(0.05).setWeighty(1));
        //right fill label
        add(new JLabel(), new GridBagTool().setGridx(3).setGridy(0).setGridwidth(1).setGridheight(7).setWeightx(0.05).setWeighty(1));
        //bottom fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(6).setGridwidth(2).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        addProgressPanel();

        addVocabularyPanel();

        addChoicesPanel();

        addButtonsPanel(memoryFrame);

        memoryFrame.add(this);
        memoryFrame.setVisible(true);
    }

    private void addProgressPanel() {

        JPanel progressPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        progressPanel.setLayout(new BorderLayout());

        JLabel toLearnNumLabel = new JLabel("       " + memoryPage.getSource() + ":  " + memoryPage.getStudyNumber() + "       ", SwingConstants.CENTER);
        progressPanel.add(toLearnNumLabel, BorderLayout.SOUTH);

        add(progressPanel, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.SOUTHWEST).setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.1));
    }

    private void addVocabularyPanel() {
        String text;
        if (isChineseChoices) {
            text = memoryPage.getWord();
        } else {
            text = memoryPage.getWordObj().getChinese();
        }

        JPanel vocabularyPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        vocabularyPanel.setLayout(new GridBagLayout());

        JLabel wordLabel;
        wordLabel = new JLabel("<html>" + text + "</html>", SwingConstants.CENTER);
        wordLabel.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 40));
        vocabularyPanel.add(wordLabel, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.SOUTH).setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.5));

        JLabel phoneticSymbolLabel = new JLabel("/" + memoryPage.getPhoneticSymbol() + "/", SwingConstants.CENTER);
        phoneticSymbolLabel.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 20));
        vocabularyPanel.add(phoneticSymbolLabel, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.NORTH).setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.5));
        if (!isChineseChoices) {
            phoneticSymbolLabel.setText("");
        }

        add(vocabularyPanel, new GridBagTool().setFill(GridBagConstraints.BOTH).setAnchor(GridBagConstraints.SOUTH).setGridx(1).setGridy(2).setGridwidth(2).setGridheight(1).setWeightx(0.9).setWeighty(0.35));
    }

    private void addChoicesPanel() {

        JPanel choicesPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        choicesPanel.setLayout(new GridLayout(2, 2));

        Set<Word> choicesInDB = memoryPage.getChoices();
        for (Word choice : choicesInDB) {
            ChoiceLabel choiceLabel = new ChoiceLabel(choice, user, this, this.isChineseChoices);
            choicesPanel.add(choiceLabel);
        }

        add(choicesPanel, new GridBagTool().setGridx(1).setGridy(3).setGridwidth(2).setGridheight(2).setWeightx(0.9).setWeighty(0.35));
    }

    private void addButtonsPanel(JFrame memoryFrame) {
        JPanel buttonsPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);

        JButton hitBtn = new JButton("Prompt");
        hitBtn.addActionListener(new WordDetailController(user, new WordExplainPage(memoryPage.getWord()), this));
        buttonsPanel.add(hitBtn);

        JButton favorBtn = null;
        try {
            favorBtn = new CollectionController().hasCollected(user, memoryPage.getWordObj()) ? new JButton("Remove from Favorite") : new JButton("Add to Favorite");
            favorBtn.addActionListener(new AddFavoriteController(user, memoryPage.getWordObj()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        buttonsPanel.add(favorBtn);

        add(buttonsPanel, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.NORTHWEST).setGridx(1).setGridy(5).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.1));

    }
}
