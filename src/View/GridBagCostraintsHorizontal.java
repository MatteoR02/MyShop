package View;

import java.awt.*;

public class GridBagCostraintsHorizontal extends GridBagConstraints {

    GridBagCostraintsHorizontal(){
    }
    GridBagCostraintsHorizontal(int gridx, int gridy, int gridwidth, int gridheight){
        super.fill = GridBagConstraints.HORIZONTAL;
        super.gridx = gridx;
        super.gridy = gridy;
        super.gridwidth = gridwidth;
        super.gridheight = gridheight;
    }

    public GridBagCostraintsHorizontal(int gridx, int gridy, int gridwidth, int gridheight,  Insets inset ) {
        super.fill = GridBagConstraints.HORIZONTAL;
        super.gridx = gridx;
        super.gridy = gridy;
        super.gridwidth = gridwidth;
        super.gridheight = gridheight;
        super.insets = inset;
    }

   public GridBagCostraintsHorizontal(int gridx, int gridy, int gridwidth, int gridheight,  Insets inset, int ipadx, int ipady ) {
        super.fill = GridBagConstraints.HORIZONTAL;
        super.gridx = gridx;
        super.gridy = gridy;
        super.gridwidth = gridwidth;
        super.gridheight = gridheight;
        super.insets = inset;
        this.ipadx = ipadx;
        this.ipady = ipady;
    }

    public GridBagCostraintsHorizontal(int gridx, int gridy, int gridwidth, int gridheight,  Insets inset, double weightx, double weighty ) {
        super.fill = GridBagConstraints.HORIZONTAL;
        super.gridx = gridx;
        super.gridy = gridy;
        super.gridwidth = gridwidth;
        super.gridheight = gridheight;
        super.insets = inset;
        this.weightx = weightx;
        this.weighty = weighty;
    }

    public GridBagCostraintsHorizontal(int gridx, int gridy, int gridwidth, int gridheight,  Insets inset, double weightx, double weighty, int anchor ) {
        super.fill = GridBagConstraints.HORIZONTAL;
        super.gridx = gridx;
        super.gridy = gridy;
        super.gridwidth = gridwidth;
        super.gridheight = gridheight;
        super.insets = inset;
        this.weightx = weightx;
        this.weighty = weighty;
        this.anchor = anchor;
    }
}
