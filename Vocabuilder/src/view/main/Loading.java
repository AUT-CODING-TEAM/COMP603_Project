/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.main;

import database.Database;
import java.awt.*;
import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author ThinkPad
 */
public class Loading extends JFrame {
    private int currentNum;
    private int totalNum;
    private JLabel info;
    private JProgressBar progressBar;

    public Loading() {
        this.currentNum = 0;
        try {
            this.totalNum = Database.getInstance().getFileLines();
        } catch (IOException ex) {
        }
        
        setProperty();
        addComponents();
        setVisible(true);
    }

    private void setProperty() {
        setSize();
        setTitle("Loading");
    }

    private void addComponents() {
        this.setLayout(new BorderLayout());
        info = new JLabel();
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 20));
        progressBar = new JProgressBar(0, totalNum);
        this.add(info, BorderLayout.CENTER);
        this.add(progressBar, BorderLayout.SOUTH);
    }

    private void setSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = 600;
        int frameHeight = 200;
        this.setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
    }

    public void setText(String txt){
        this.info.setText(txt);
        progressBar.setValue(currentNum++);
    }
    
    public void done(){
        this.dispose();
    }
}
