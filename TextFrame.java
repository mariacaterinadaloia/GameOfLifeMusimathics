package finale;

import javax.swing.*;
import java.awt.*;

public class TextFrame extends JFrame {
    private JLabel label;
    private JTextArea notes = new JTextArea();
    private JScrollPane pane;
    private JPanel noteArea;
    public TextFrame(String melody){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.jpg")));
        noteArea = createNoteArea(melody);
        add(noteArea);
        setTitle("Played notes");
        setLocationRelativeTo(null);
        setSize(300,300);
        setVisible(true);
        setResizable(false);
    }

    private JPanel createNoteArea(String melody){
        JPanel panel = new JPanel();
        notes.setSize(250,250);
        panel.setBackground(new Color(9, 236, 167));
        notes.setEditable(false);
        notes.setText(melody);
        notes.setLineWrap(true);
        notes.setFocusable(true);
        pane = new JScrollPane(notes);
        notes.setBackground(new Color(9, 236, 167));
        panel.add(notes);
        return panel;
    }
}
