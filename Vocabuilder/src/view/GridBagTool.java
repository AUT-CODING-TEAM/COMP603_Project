/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.GridBagConstraints;

/**
 *
 * @author ThinkPad
 */
public class GridBagTool extends GridBagConstraints {
    public GridBagTool(){
        this.fill = GridBagConstraints.BOTH;
        this.anchor = GridBagConstraints.CENTER;
    }

    public GridBagTool setFill(int fill) {
        this.fill = fill;
        return this;
    }

    public GridBagTool setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    public GridBagTool setGridx(int gridx) {
        this.gridx = gridx;
        return this;
    }

    public GridBagTool setGridy(int gridy) {
        this.gridy = gridy;
        return this;
    }

    public GridBagTool setGridwidth(int gridwidth) {
        this.gridwidth = gridwidth;
        return this;
    }

    public GridBagTool setGridheight(int gridheight) {
        this.gridheight = gridheight;
        return this;
    }

    public GridBagTool setWeightx(double weightx) {
        this.weightx = weightx;
        return this;
    }

    public GridBagTool setWeighty(double weighty) {
        this.weighty = weighty;
        return this;
    }

}
