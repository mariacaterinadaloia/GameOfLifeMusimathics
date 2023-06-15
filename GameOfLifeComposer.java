package finale;

import javax.swing.*;

public class GameOfLifeComposer {

    public static void main(String[] args) {
        //Sets the first Frame in game, where you can choose the size of the grid.
        JFrame frame = new PreFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
