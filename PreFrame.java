package finale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreFrame extends JFrame {
    Image imageIcon;
    JRadioButton b1;
    JRadioButton b2;
    JRadioButton b3;
    JPanel imagePanel;
    JLabel image;
    JPanel buttonsPanel;
    JButton goToGame;
    JLabel label;
    JPanel buttons;
    JPanel superPanel;

    public PreFrame(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.jpg")));
        buttonsPanel = createButtonsPanel();
        superPanel = new JPanel();
        setTitle("GoFC starter");
        superPanel.setLayout(new BorderLayout());
        superPanel.setSize(300,300);
        setSize(300, 300);
        superPanel.setBackground(new Color(9, 236, 167));
        superPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(superPanel);
        setResizable(false);
    }

    //This method sets the buttons where the user chooses the grid's size.
    private JPanel createButtonsPanel(){
        //creates the panels and sets its layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.CYAN);
        //creation of the buttons which determines the size of the grid and their button group
        //creates another panel for the buttons
        buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,3));
        b1 = new JRadioButton("10x10");
        b2 = new JRadioButton("20x20");
        b3 = new JRadioButton("30x30");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(b1);
        buttonGroup.add(b2);
        buttonGroup.add(b3);
        buttons.add(b1);
        buttons.add(b2);
        buttons.add(b3);
        panel.add(buttons, BorderLayout.CENTER);
        label = new JLabel("Grid's dimension: ");
        panel.add(label, BorderLayout.NORTH);

        //this button starts the other frame with the game and its ActionListener to open the other frame
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(b1.isSelected()) {
                    JFrame frame = new FrameGame(10, 10);
                    frame.setSize(500, 500);
                    frame.setTitle("Game of Life Composer");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    dispose();
                }else if(b2.isSelected()){
                    JFrame frame = new FrameGame(20, 20);
                    frame.setSize(500, 500);
                    frame.setTitle("Game of Life Composer");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    dispose();
                }else if(b3.isSelected()){
                    JFrame frame = new FrameGame(30, 30);
                    frame.setSize(1000, 700);
                    frame.setTitle("Game of Life Composer");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    dispose();
                }else if(!b1.isSelected()&&!b2.isSelected()&&!b3.isSelected()){
                    JLabel wrong = new JLabel("Please, choose the size.");
                    wrong.setForeground(Color.red);
                    panel.remove(label);
                    panel.validate();
                    panel.add(wrong, BorderLayout.NORTH);
                    panel.validate();
                }
            }
        };
        goToGame = new JButton("Start Game of Life Composer");
        goToGame.addActionListener(listener);
        panel.add(goToGame, BorderLayout.SOUTH);

        return panel;
    }
}
