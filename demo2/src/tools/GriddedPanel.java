package tools;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class GriddedPanel extends JPanel {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        private GridBagConstraints constraints = null;
        
        
        public GriddedPanel() {
                this(new Insets(2, 2, 2, 2));
        }

        public GriddedPanel(Insets insets) {
                super(new GridBagLayout());
                constraints = new GridBagConstraints();
                constraints.anchor = GridBagConstraints.WEST;
                constraints.insets = insets;
        }
        
 

        public void addComponent(JComponent component, int row, int col, int width, int height, int anchor, int fill)
        {
                constraints.gridx = col;
                constraints.gridy = row;
                constraints.gridheight = height;
                constraints.gridwidth = width;
                constraints.anchor = anchor;
                
                double weightx = 0.0;
                double weighty = 0.0;
                
                if(width > 1)
                      weightx = 1.0;
                if(height > 1)
                      weighty = 1.0;
                
                switch(fill)
                {
                        case GridBagConstraints.HORIZONTAL:
                                constraints.weightx = weightx;
                                constraints.weighty = 0.0;
                                break;
                        case GridBagConstraints.VERTICAL:
                                constraints.weightx = 0.0;
                                constraints.weighty = weighty;
                                break;
                        case GridBagConstraints.BOTH:
                                constraints.weightx = weightx;
                                constraints.weighty = weighty;
                                break;
                        case GridBagConstraints.NONE:
                                constraints.weightx = 0.0;
                                constraints.weighty = 0.0;
                                break;
                        default:
                                break;
                }
                constraints.fill = fill;
                add(component, constraints);
        }
        
        public void addComponent(JComponent component, int row, int col, int width, int height, double weightx, double weighty, int anchor, int fill)
        {
                constraints.gridx = col;
                constraints.gridy = row;
                constraints.gridheight = height;
                constraints.gridwidth = width;
                constraints.weightx = weightx;
                constraints.weighty = weighty;
                constraints.anchor = anchor;
                constraints.fill = fill;
                add(component, constraints);
        }
}