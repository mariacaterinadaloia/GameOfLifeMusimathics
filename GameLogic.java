package finale;

import org.jfugue.player.Player;

import javax.swing.*;
import java.util.Random;

/* This class implements the game's logic, which will be explained throughout the scope of the class. */
public class GameLogic {
    private JPanel panels[][];
    private int iteration;
    private String melody = "";
    //the melody will be played as a Grand Piano, in this array we have the chosen octaves
    private String[] notes = { "C3", "C3#", "D3" ,"D3#", "E3", "F3", "F3#" , "G3", "G3#", "A3", "A3#", "B3",
             "C4", "C4#", "D4" ,"D4#", "E4", "F4", "F4#" , "G4", "G4#", "A4", "A4#", "B4",
            "C5", "C5#", "D5" ,"D5#", "E5", "F5", "F5#" , "G5", "G5#", "A5", "A5#", "B5"};
    private int previousNote;
    private String[] durations = { "q", "i", "s","t"};
    private boolean[][] DAAcopy;
    private int width;
    private int length;
    private boolean[][] deadAndAlive;
    private FrameGame frame;
    public GameLogic(int width, int length, boolean[][] deadAndAlive, FrameGame frame, JPanel panel[][]){
        DAAcopy = new boolean[width][length];
        this.width=width;
        this.length=length;
        this.deadAndAlive=deadAndAlive;
        this.frame=frame;
        previousNote = 0;
        this.panels=panel;
    }

    public void init(){
        clearMelody();
        createMelody();
        copyDAA();
        nextStep();
        Player player = new Player();
        player.play(melody);
        JFrame melFrame = new TextFrame(melody);
        melFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void createMelody(){
        for (int i=0; i<width; i++) {
            for (int j=0; j<length; j++) {
                if(deadAndAlive[i][j]==true)
                    chooseNoteAndDuration();
            }
        }
    }

    private void chooseNoteAndDuration(){
        Random random = new Random();

        //if it is the first note, it will be chosen randomly
        if(previousNote==0) {
            int n = Math.abs(random.nextInt(notes.length));
            int d = Math.abs(random.nextInt(durations.length));

            melody += notes[n] + durations[d] + " ";
            previousNote = n;
        }
        //if other notes already exists, it will be chosen semi-randomly,
        //by the armony rules
        else {
            int i = Math.abs(random.nextInt(6));
            int d = Math.abs(random.nextInt(durations.length));
            int chords = Math.abs(random.nextInt(8));
            if (chords == 4  && previousNote<(notes.length-12)) {
                melody += notes[previousNote] + "+" + notes[previousNote + 4] + "+" + notes[previousNote + 7] + " ";
            } else if (chords == 8 && previousNote<(notes.length-12)) {
                melody += notes[previousNote] + "+" + notes[previousNote + 3] + "+" + notes[previousNote + 7] + " ";
            } else {
                switch (i) {
                    case 0:
                        if (previousNote < notes.length - 6) {
                            melody += notes[previousNote + 5] + durations[d] + " ";
                            previousNote = previousNote + 5;
                        } else {
                            melody += notes[previousNote - 5] + durations[d] + " ";
                            previousNote = previousNote - 5;
                        }
                        break;
                    case 1:
                        if (previousNote < notes.length - 8) {
                            melody += notes[previousNote + 7] + durations[d] + " ";
                            previousNote = previousNote + 7;
                        } else {
                            melody += notes[previousNote - 7] + durations[d] + " ";
                            previousNote = previousNote - 7;
                        }
                        break;
                    case 2:
                        if (previousNote < notes.length - 13) {
                            melody += notes[previousNote + 12] + durations[d] + " ";
                            previousNote = previousNote + 12;
                        } else {
                            melody += notes[previousNote - 12] + durations[d] + " ";
                            previousNote = previousNote - 12;
                        }
                        break;
                    case 3:
                        if (previousNote > 8) {
                            melody += notes[previousNote - 7] + durations[d] + " ";
                            previousNote = previousNote - 7;
                        } else {
                            melody += notes[previousNote + 7] + durations[d] + " ";
                            previousNote = previousNote + 7;
                        }
                        break;
                    case 4:
                        if (previousNote > 6) {
                            melody += notes[previousNote - 5] + durations[d] + " ";
                            previousNote = previousNote - 5;
                        } else {
                            melody += notes[previousNote + 5] + durations[d] + " ";
                            previousNote = previousNote + 5;
                        }
                        break;
                    case 5:
                        if (previousNote > 12) {
                            melody += notes[previousNote - 12] + durations[d] + " ";
                            previousNote = previousNote - 12;
                        } else {
                            melody += notes[previousNote + 12] + durations[d] + " ";
                            previousNote = previousNote + 12;
                        }
                        break;
                }
            }
        }
    }

    public void clearMelody(){
        melody="";
    }

    private void nextStep() {
        boolean[][] copyNS = new boolean[width][length];
        for (int i=0; i<width; i++) {
            for (int j=0; j<length; j++) {
                if(deadAndAlive[i][j]==true){ //vivo
                    if(checkAlive(i,j,deadAndAlive)==2) {
                        copyNS[i][j] = true;
                    }else if(checkAlive(i,j,deadAndAlive)==3) {
                        copyNS[i][j] = true;
                    }
                    else {
                        copyNS[i][j] = false;
                    }
                }else if(deadAndAlive[i][j]==false){
                    if(checkAlive(i, j, deadAndAlive)==3)
                        copyNS[i][j] = true;
                    else{
                        copyNS[i][j] = false;
                    }
                }
            }
        }
        copyNS(copyNS);
        GridWorker gw = new GridWorker();
        gw.execute();

        if(areTheyEquals()) {
            return;
        }else{
            createMelody();
            copyDAA();
            nextStep();
        }
    }
    //repaints the grid
    private class GridWorker extends SwingWorker<Boolean, Boolean>{

        @Override
        protected Boolean doInBackground() throws Exception {
            frame.setGrid(deadAndAlive);
            frame.validate();
            return true;
        }
    }
    //checks the cells
    private int checkAlive(int i, int j, boolean[][] deadAndAlive){
        int count=0;
        if(j!=0) {
            if ((deadAndAlive[i][j - 1])==true) count++;
       }if(j!=(length-1)) {
            if ((deadAndAlive[i][j + 1])==true) count++;
        }if(((i!=0)&&(j!=length-1))) {
            if ((deadAndAlive[i - 1][j + 1])==true) count++;
        }if(((i!=0)&&(j!=0))) {
            if ((deadAndAlive[i - 1][j - 1])==true)  count++;
        }if (i!=0) {
            if ((deadAndAlive[i - 1][j])==true) count++;
        }if(((i!=(width-1))&&(j!=0))) {
            if ((deadAndAlive[i + 1][j - 1])==true) count++;
        }if(((i!=width-1)&&(j!=length-1))) {
            if ((deadAndAlive[i + 1][j + 1])==true) count++;
        }if(i!=(width-1)) {
            if ((deadAndAlive[i + 1][j])==true) count++;
        }
        return count;
    }

    private void copyDAA(){
        for(int i=0; i<width; i++)
            for(int j=0; j<length; j++)
                DAAcopy[i][j]=deadAndAlive[i][j];
    }
    //stops nextStep if it is true
    private boolean areTheyEquals(){
        for(int i=0; i<width; i++)
            for(int j=0; j<length; j++)
                if(DAAcopy[i][j]!=deadAndAlive[i][j])
                    return false;
        return true;
    }
    private void copyNS(boolean [][] array){
        for(int i=0; i<width; i++)
            for(int j=0; j<length; j++)
                deadAndAlive[i][j]=array[i][j];
    }
}
