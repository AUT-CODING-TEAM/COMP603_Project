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
        int frameHeight = 360;
        jFrame.setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
    }

    public void setProperty() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        myPlanFrame = new JFrame("我的计划");
        setSize(myPlanFrame);
        myPlanFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myPlanFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e);
                new MainView(user);
            }
        });

        //left fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));
        //right fill label
        add(new JLabel(), new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));
        //top fill label
//        add(new JLabel(), new GridBagTool().setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
        //bottom fill label
        add(new JLabel(), new GridBagTool().setGridx(1).setGridy(4).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        JLabel lbl_myPP_myPlan = new JLabel("我的计划", SwingConstants.CENTER);
        add(lbl_myPP_myPlan, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.CENTER).setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        JLabel lbl_myPP_chooseBook = new JLabel("选择学习词书");
        add(lbl_myPP_chooseBook, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.WEST).setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));

        addMyBookPanel();

        btn_myPP_handleByBookSituation = new JButton("继续学习该计划");
        btn_myPP_handleByBookSituation.setEnabled(false);
        btn_myPP_handleByBookSituation.addActionListener(new MakePlanController(user, this));
        add(btn_myPP_handleByBookSituation, new GridBagTool().setFill(GridBagConstraints.HORIZONTAL).setGridx(1).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        myPlanFrame.add(this);
        myPlanFrame.setVisible(true);
    }

    public void addMyBookPanel() {
        JPanel myBookPanel = new JPanel(new GridLayout(1, myPlanInfo.getMyStudyPlans().size() + 1, 20, 20));

        for (int i = 0; i < myPlanInfo.getMyStudyPlans().size(); i++) {
//            JPanel jPanel = new JPanel(new GridBagLayout());
            OnePlanPanel jPanel = new OnePlanPanel();
            jPanel.setName(String.valueOf(i));

            JLabel lbl_myPP_studyPlanName = new JLabel(myPlanInfo.getMyStudyPlans().get(i).getStudyPlanName(), SwingConstants.CENTER);
            lbl_myPP_studyPlanName.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 20));
            jPanel.addStudyPlanName(lbl_myPP_studyPlanName, new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.8));

            JLabel lbl_myPP_totalNumber = new JLabel(String.valueOf(myPlanInfo.getMyStudyPlans().get(i).getTotalNumber()) + "词", SwingConstants.CENTER);
            lbl_myPP_totalNumber.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 15));
            jPanel.addTotalNumber(lbl_myPP_totalNumber, new GridBagTool().setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.2));

            jPanel.setBackground(new Color(91, 110, 125));
            jPanel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    OnePlanPanel selectedPanel = (OnePlanPanel) e.getSource();
                    int index = Integer.parseInt(selectedPanel.getName());
                    bookName = myPlanInfo.getMyStudyPlans().get(index).getStudyPlanName();
                    if (myPlanInfo.getMyStudyPlans().get(index).getFinished() == 0) {
                        btn_myPP_handleByBookSituation.setEnabled(true);
                        btn_myPP_handleByBookSituation.setText("学习该计划");
                    } else if (myPlanInfo.getMyStudyPlans().get(index).getFinished() == 1) {
                        btn_myPP_handleByBookSituation.setEnabled(true);
                        btn_myPP_handleByBookSituation.setText("复习该计划");
                    }
                    if (user.getCurrentStudyPlan().getStudyPlanName().equals(myPlanInfo.getMyStudyPlans().get(index).getStudyPlanName())) {
                        btn_myPP_handleByBookSituation.setEnabled(false);
                        btn_myPP_handleByBookSituation.setText("继续学习该计划");
                    }
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
            if (myPlanInfo.getMyStudyPlans().get(i).getFinished() == 1) {
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
}
