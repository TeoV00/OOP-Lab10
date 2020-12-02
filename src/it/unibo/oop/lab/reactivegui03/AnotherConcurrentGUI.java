package it.unibo.oop.lab.reactivegui03;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class AnotherConcurrentGUI extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -8710276539980695794L;
    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;
    private final JButton upButton = new JButton("UP");
    private final JButton downButton = new JButton("DOWN");
    private final JButton stopBtn = new JButton("Stop");
    private final JLabel countLabel = new JLabel("");
    private boolean stop = false;


    public AnotherConcurrentGUI() {
        super();
        final Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        final JPanel panel = new JPanel();
        this.setSize((int) (screenDim.getWidth() * WIDTH_PERC), (int) (screenDim.getHeight() * HEIGHT_PERC));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.add(countLabel);
        panel.add(downButton);
        panel.add(upButton);
        panel.add(stopBtn);

        this.getContentPane().add(panel);
        this.setVisible(true);

        final Agent agent = new Agent();
        new Thread(agent).start();
        final AutoStopAgent autoStop = new AutoStopAgent();
        new Thread(autoStop).start();

        stopBtn.addActionListener(e -> agent.stopCounting());
        upButton.addActionListener(e -> agent.setUpCount());
        downButton.addActionListener(e -> agent.setDownCount());

    }

    private class AutoStopAgent implements Runnable {

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            long oldTime;
            for (int time = 0; time < 10;) {
                oldTime = System.currentTimeMillis();
                if (oldTime - startTime >= 1000) {
                    time++;
                    startTime = oldTime;
                    System.out.println(time);
                }
            }
            System.out.println("10 second expired");
            AnotherConcurrentGUI.this.stop = true;
            AnotherConcurrentGUI.this.stopBtn.setEnabled(false);
            AnotherConcurrentGUI.this.downButton.setEnabled(false);
            AnotherConcurrentGUI.this.upButton.setEnabled(false);
        }

    }

    private class Agent implements Runnable {

        private boolean upDown = true;
        private int counter = 0;

        public void stopCounting() {
            AnotherConcurrentGUI.this.stop = true;
        }

        public void setDownCount() {
            this.upDown = false;
        }

        public void setUpCount() {
           this.upDown = true;
        }

        @Override
        public void run() {
            while (!stop) {
                try {
                    SwingUtilities.invokeAndWait(() -> AnotherConcurrentGUI.this.countLabel.setText(Integer.toString(this.counter)));
                    if (upDown) {
                        counter++;
                    } else {
                        counter--;
                    }
                    Thread.sleep(100);
                } catch (InvocationTargetException | InterruptedException e) {
                    e.printStackTrace();
                } 
            }
            AnotherConcurrentGUI.this.stopBtn.setEnabled(false);
            AnotherConcurrentGUI.this.downButton.setEnabled(false);
            AnotherConcurrentGUI.this.upButton.setEnabled(false);
        }

    }

}
