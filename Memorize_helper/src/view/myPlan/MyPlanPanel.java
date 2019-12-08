/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.myPlan;

import controller.StartLearnController;
import controller.myPlan.MakePlanController;
import controller.myPlan.ShowPlanListController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.MyPlanInfo;
import model.User;
import model.VocabularyListInfo;
import view.GridBagTool;
import view.GroundPanelTemplate;
import view.ListInScrollTemplate;
import view.OnePlanPanel;
import view.main.MainView;
import view.vocabularyList.VocabularyListPanel;

/**
 *
 * @author ThinkPad
 */
public class MyPlanPanel extends GroundPanelTemplate {

    private User user;
    private MyPlanInfo myPlanInfo;
    private JFrame myPlanFrame;
    private JLabel selectedBookName;
    private JLabel selectedBookTip;
    private JTabbedPane optionTabs;
    private JList makePlanListPart1;
    private JList makePlanListPart2;
    private JButton btn_myPP_handleByBookSituation;
    private String bookName;
    private String quantity;

    public JFrame getMyPlanFrame() {
        return myPlanFrame;
    }

    public String getBookName() {
        return bookName;
    }

    public String getQuantity() {
        return quantity;
    }

    public MyPlanPanel(User user, MyPlanInfo myPlanInfo) {
        super(GroundPanelTemplate.BACK);
        this.user = user;
        this.myPlanInfo = myPlanInfo;
        setProperty();
        addComponents();
    }

    private void setSize(JFrame jFrame) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
//        int frameWidth = 1280;
        int frameWidth = 720;
        int frameHeight = 720;
        jFrame.setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
    }

    public void setProperty() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        myPlanFrame = new JFrame("单词列表");
        setSize(myPlanFrame);
        myPlanFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myPlanFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e);
                new MainView(user);
            }
        });

        //left fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(6).setWeightx(0.05).setWeighty(1));
        //right fill label
        add(new JLabel(), new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(6).setWeightx(0.05).setWeighty(1));
        //top fill label
//        add(new JLabel(), new GridBagTool().setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
        //bottom fill label
        add(new JLabel(), new GridBagTool().setGridx(1).setGridy(5).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        JLabel lbl_myPP_myPlan = new JLabel("我的计划", SwingConstants.CENTER);
        add(lbl_myPP_myPlan, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.CENTER).setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        JLabel lbl_myPP_chooseBook = new JLabel("选择学习词书");
        add(lbl_myPP_chooseBook, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.WEST).setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));

        addMyBookPanel();

        addMakePlanPanel();

        btn_myPP_handleByBookSituation = new JButton();
        if (user.getCurrentStudyPlan().getStudyPlanName().equals(myPlanInfo.getStudyPlans().get(0).getStudyPlanName())) {
            btn_myPP_handleByBookSituation.setEnabled(false);
            btn_myPP_handleByBookSituation.setText("继续学习该计划");
        } else if (myPlanInfo.getStudyPlans().get(0).getFinished() == 0) {
            btn_myPP_handleByBookSituation.setText("学习该计划");
        } else if (myPlanInfo.getStudyPlans().get(0).getFinished() == 1) {
            btn_myPP_handleByBookSituation.setText("复习该计划");
        }
        btn_myPP_handleByBookSituation.addActionListener(new MakePlanController(user, this));
        add(btn_myPP_handleByBookSituation, new GridBagTool().setFill(GridBagConstraints.HORIZONTAL).setGridx(1).setGridy(4).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        myPlanFrame.add(this);
        myPlanFrame.setVisible(true);
    }

    public void addMyBookPanel() {
        JPanel myBookPanel = new JPanel(new GridLayout(1, myPlanInfo.getStudyPlans().size() + 1, 20, 20));

        for (int i = 0; i < myPlanInfo.getStudyPlans().size(); i++) {
//            JPanel jPanel = new JPanel(new GridBagLayout());
            OnePlanPanel jPanel = new OnePlanPanel();
            jPanel.setName(String.valueOf(i));

            JLabel lbl_myPP_studyPlanName = new JLabel(myPlanInfo.getStudyPlans().get(i).getStudyPlanName(), SwingConstants.CENTER);
            lbl_myPP_studyPlanName.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 20));
            jPanel.addStudyPlanName(lbl_myPP_studyPlanName, new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.8));

            JLabel lbl_myPP_totalNumber = new JLabel(String.valueOf(myPlanInfo.getStudyPlans().get(i).getTotalNumber()) + "词", SwingConstants.CENTER);
            lbl_myPP_totalNumber.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 15));
            jPanel.addTotalNumber(lbl_myPP_totalNumber, new GridBagTool().setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.2));

            jPanel.setBackground(new Color(91, 110, 125));
            jPanel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    OnePlanPanel selectedPanel = (OnePlanPanel) e.getSource();
                    int index = Integer.parseInt(selectedPanel.getName());
                    bookName = myPlanInfo.getStudyPlans().get(index).getStudyPlanName();
                    selectedBookName.setText(myPlanInfo.getStudyPlans().get(index).getStudyPlanName());
                    if (myPlanInfo.getStudyPlans().get(index).getFinished() == 0) {
                        selectedBookTip.setText("计划完成日期 " + myPlanInfo.getStudyPlans().get(index).getPlanFinishedDay());
                        optionTabs.setTitleAt(0, "按每天背单词数学习");
                        optionTabs.setTitleAt(1, "按完成天数学习");
                        btn_myPP_handleByBookSituation.setEnabled(true);
                        btn_myPP_handleByBookSituation.setText("学习该计划");
                    } else if (myPlanInfo.getStudyPlans().get(index).getFinished() == 1) {
                        selectedBookTip.setText("已学完整本计划，开始总复习吧");
                        optionTabs.setTitleAt(0, "按每天复习单词数复习");
                        optionTabs.setTitleAt(1, "按完成天数复习");
                        btn_myPP_handleByBookSituation.setEnabled(true);
                        btn_myPP_handleByBookSituation.setText("复习该计划");
                    }
                    if (user.getCurrentStudyPlan().getStudyPlanName().equals(myPlanInfo.getStudyPlans().get(index).getStudyPlanName())) {
                        btn_myPP_handleByBookSituation.setEnabled(false);
                        btn_myPP_handleByBookSituation.setText("继续学习该计划");
                    }

                    String s0[] = null;
                    if (myPlanInfo.getStudyPlans().get(index).getTotalNumber() % 5 == 0) {
                        s0 = new String[myPlanInfo.getStudyPlans().get(index).getTotalNumber() / 5];
                        for (int i = 0; i < s0.length; i++) {
                            s0[i] = String.format("%50s", String.valueOf(5 * (i + 1) + "词"));
                        }
                    } else {
                        s0 = new String[myPlanInfo.getStudyPlans().get(index).getTotalNumber() / 5 + 1];
                        for (int i = 0; i < s0.length - 1; i++) {
                            s0[i] = String.format("%50s", String.valueOf(5 * (i + 1) + "词"));
                        }
                        s0[s0.length - 1] = String.format("%50s", String.valueOf(myPlanInfo.getStudyPlans().get(index).getTotalNumber() + "词"));
                    }
                    makePlanListPart1.setListData(s0);

                    String s1[] = new String[myPlanInfo.getStudyPlans().get(index).getTotalNumber()];
                    for (int i = 0; i < s1.length; i++) {
                        s1[i] = String.format("%50s", (i + 1) + "天");
                    }
                    makePlanListPart2.setListData(s1);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            if (myPlanInfo.getStudyPlans().get(i).getFinished() == 1) {
                jPanel.setBorder(new TitledBorder("已学完"));
            }

            myBookPanel.add(jPanel);
        }

        JButton btn_myBP_addBook = new JButton("添加词书");
        btn_myBP_addBook.addActionListener(new ShowPlanListController(myPlanFrame, user));
        myBookPanel.add(btn_myBP_addBook);

//        JScrollPane jScrollPane = new JScrollPane(myBookPanel);
        JScrollPane jScrollPane = new JScrollPane(myBookPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(jScrollPane, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.15));
    }

    public void addMakePlanPanel() {
        JPanel makePlanPanel = new JPanel(new GridBagLayout());

        JPanel topJPanel = new JPanel(new GridLayout(2, 1));
        topJPanel.setOpaque(true);
        topJPanel.setBackground(new Color(238, 236, 232));

        selectedBookName = new JLabel(myPlanInfo.getStudyPlans().get(0).getStudyPlanName());
        selectedBookName.setFont(new Font("FACE_SYSTEM", Font.BOLD, 20));
        topJPanel.add(selectedBookName);

        if (myPlanInfo.getStudyPlans().get(0).getFinished() == 0) {
            selectedBookTip = new JLabel("计划完成日期 " + myPlanInfo.getStudyPlans().get(0).getPlanFinishedDay());
        } else if (myPlanInfo.getStudyPlans().get(0).getFinished() == 1) {
            selectedBookTip = new JLabel("已学完整本计划，开始总复习吧");
        }
        selectedBookTip.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 15));
        topJPanel.add(selectedBookTip);

        makePlanPanel.add(topJPanel, new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.05));

        optionTabs = new JTabbedPane();

        MakePlanTabPanel makePlanTabPanelPart1 = new MakePlanTabPanel(0);
        MakePlanTabPanel makePlanTabPanelPart2 = new MakePlanTabPanel(1);
        if (myPlanInfo.getStudyPlans().get(0).getFinished() == 0) {
            optionTabs.addTab("按每天背单词数学习", makePlanTabPanelPart1);
            optionTabs.addTab("按完成天数学习", makePlanTabPanelPart2);
        } else if (myPlanInfo.getStudyPlans().get(0).getFinished() == 1) {
            optionTabs.addTab("按每天复习单词数复习", makePlanTabPanelPart1);
            optionTabs.addTab("按完成天数复习", makePlanTabPanelPart2);
        }

        makePlanPanel.add(optionTabs, new GridBagTool().setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.95));

        add(makePlanPanel, new GridBagTool().setGridx(1).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.5));
    }

    class MakePlanTabPanel extends JPanel {

        private int option;

        public MakePlanTabPanel(int option) {
            this.option = option;
            setProperty();
            addComponents();
        }

        public void setProperty() {
            setLayout(new GridLayout(1, 1));
        }

        public void addComponents() {
            if (option == 0) {
                String s[] = null;
                if (myPlanInfo.getStudyPlans().get(0).getTotalNumber() % 5 == 0) {
                    s = new String[myPlanInfo.getStudyPlans().get(0).getTotalNumber() / 5];
                    for (int i = 0; i < s.length; i++) {
                        s[i] = String.format("%50s", String.valueOf(5 * (i + 1) + "词"));
                    }
                } else {
                    s = new String[myPlanInfo.getStudyPlans().get(0).getTotalNumber() / 5 + 1];
                    for (int i = 0; i < s.length - 1; i++) {
                        s[i] = String.format("%50s", String.valueOf(5 * (i + 1) + "词"));
                    }
                    s[s.length - 1] = String.format("%50s", String.valueOf(myPlanInfo.getStudyPlans().get(0).getTotalNumber() + "词"));
                }
                makePlanListPart1 = new ListInScrollTemplate(s);
                makePlanListPart1.setEnabled(true);
                makePlanListPart1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                makePlanListPart1.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (makePlanListPart1.getValueIsAdjusting()) {
                            quantity = makePlanListPart1.getSelectedValue().toString().trim();
                        }
                    }
                });
                JScrollPane jScrollPane = new JScrollPane(makePlanListPart1);
                add(jScrollPane);
            } else if (option == 1) {
                String s[] = new String[myPlanInfo.getStudyPlans().get(0).getTotalNumber()];
                for (int i = 0; i < s.length; i++) {
                    s[i] = String.format("%50s", (i + 1) + "天");
                }
                makePlanListPart2 = new ListInScrollTemplate(s);
                makePlanListPart2.setEnabled(true);
                makePlanListPart2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                makePlanListPart2.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (makePlanListPart2.getValueIsAdjusting()) {
                            quantity = makePlanListPart2.getSelectedValue().toString().trim();
                        }
                    }
                });
                JScrollPane jScrollPane = new JScrollPane(makePlanListPart2);
                add(jScrollPane);
            }
        }
    }
}
