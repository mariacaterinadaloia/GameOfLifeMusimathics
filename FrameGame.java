package finale;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;


public class FrameGame extends JFrame {
    private static boolean chordsAbilited = false;
    private static String octave = "random";
    private JPanel panelGame;
    private static boolean[][] aliveAndDead;
    private JLabel title = new JLabel("Game Of Life");
    private JButton buttonStart;
    private JPanel panelButton;
    private JPanel voidOne;
    private JPanel voidTwo;
    private JButton randomStart;
    private JButton cleanButton;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem more;
    private static JPanel[][] panels;
    private static int width;
    private static int length;
    private GameLogic gl;
    private JPanel noteArea;
    private JTextArea notes;
    private JScrollBar bar;
    private JScrollPane pane;
    public FrameGame(int width, int length) {
        this.length = length;
        this.width = width;

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.jpg")));
        panels = new JPanel[width][length];
        aliveAndDead = new boolean[width][length];
        setLayout(new BorderLayout());
        panelGame = setPanelGame();
        add(panelGame,BorderLayout.CENTER);
        panelButton = setButtonPanel();
        add(panelButton, BorderLayout.SOUTH);
        voidOne = createVoidPanel();
        voidTwo = createVoidPanel();
        add(voidOne, BorderLayout.EAST);
        add(voidTwo, BorderLayout.WEST);
        setVisible(true);
        gl = new GameLogic(width, length, aliveAndDead, this, panels);
        setResizable(false);
    }
    //This method will create the grid
    private JPanel setPanelGame() {
        JPanel p = new JPanel();
        p.setBackground(new Color(9, 236, 167));
        p.setLayout(new GridLayout(width, length));
        for (int j = 0; j < width; j++) {
            for (int z = 0; z < length; z++) {
                //setting the grid graphically, a Boolean array will help with the logic of the game.
                panels[j][z] = createGamePanel();
                aliveAndDead[j][z] = false;
                p.add(panels[j][z]);
                //Implements the MouseListener in which the states of the cells change if clicked on.
                panels[j][z].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        for (int j = 0; j < width; j++) {
                            for (int z = 0; z < length; z++) {
                                if (panels[j][z].equals(e.getSource())) {
                                    if(!aliveAndDead[j][z]) {
                                        aliveAndDead[j][z] = true;
                                        panels[j][z].setBackground(Color.RED);
                                    }else{
                                        aliveAndDead[j][z] = false;
                                        panels[j][z].setBackground(new Color(134, 213, 204));
                                    }
                                }
                            }
                        }
                    }

                    //void methods
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
            }
        }
        return p;
    }

    //This method sets the button that the user must click on to start with the automation
    private JPanel setButtonPanel() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1, 2));
        //Sets the start button
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gl.init();
            }
        };
        buttonStart = new JButton("Start");
        buttonStart.addActionListener(listener);
        p.add(buttonStart);

        //sets the random start button
        ActionListener listener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRandomStart(aliveAndDead, width, length);
                setGrid(aliveAndDead);
            }
        };
        randomStart = new JButton("Random");
        randomStart.addActionListener(listener1);
        p.add(randomStart);

        //sets the button which clears the grid
        ActionListener listener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAliveAndDeadToFalse(aliveAndDead, width, length);
                setGrid(aliveAndDead);
            }
        };
        cleanButton = new JButton("Clean");
        cleanButton.addActionListener(listener2);

        p.add(cleanButton);

        return p;
    }

    //This method creates the cells, which are JPanels.
    private JPanel createGamePanel() {
        JPanel p = new JPanel();
        p.setBorder(new LineBorder(Color.BLACK));
        p.setBackground(new Color(134, 213, 204));
        return p;
    }

    //For graphic's purposes, not related to the game.
    private JPanel createVoidPanel() {
        JPanel p = new JPanel();
        p.setBackground(new Color(9, 236, 167));
        return p;
    }

    public void setGrid(boolean[][] NEWaliveAndDead) {
        for (int j = 0; j < width; j++) {
            for (int z = 0; z < length; z++) {
                if (NEWaliveAndDead[j][z] == true) {
                    panels[j][z].setBackground(Color.RED);
                    panels[j][z].repaint();
                    try{
                        Thread.sleep(10);
                    }catch(Exception ignore) {
                    }
                } else {
                    panels[j][z].setBackground(new Color(134, 213, 204));
                    panels[j][z].repaint();
                    try{
                            Thread.sleep(10);
                    }catch(Exception ignore){
                    }
                }
            }
        }

        aliveAndDead = NEWaliveAndDead;
    }

    private void setAliveAndDeadToFalse(boolean[][] aliveAndDeadToFalse, int width, int length) {
        for (int j = 0; j < width; j++) {
            for (int z = 0; z < length; z++) {
                aliveAndDeadToFalse[j][z] = false;
            }
        }
    }

    private void setRandomStart(boolean[][] deadAndAlive, int width, int length) {
        Random random = new Random();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                int r = Math.abs(random.nextInt(6));
                if (r == 0)
                    deadAndAlive[i][j] = true;
                else
                    deadAndAlive[i][j] = false;
            }
        }
    }
}
