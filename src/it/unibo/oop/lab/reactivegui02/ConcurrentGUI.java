package it.unibo.oop.lab.reactivegui02;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Toolkit;

public class ConcurrentGUI extends JFrame{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;
    private final JButton upButton = new JButton("UP");
    private final JButton downButton = new JButton("DOWN");
    private final JButton stopBtn = new JButton("Stop");
    private final JLabel countLabel = new JLabel();

    public ConcurrentGUI() {
        final Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        final JFrame frame = new JFrame("Counter");
        frame.setSize((int) (screenDim.getWidth() * WIDTH_PERC), (int) (screenDim.getHeight() * HEIGHT_PERC));
        final JPanel panel = new JPanel();
        panel.add(countLabel);
        panel.add(downButton);
        panel.add(upButton);
        panel.add(stopBtn);
        frame.setVisible(true);
    }

}
