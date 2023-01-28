import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Board implements ActionListener, KeyListener, Runnable
{
    private JFrame frame;
    private JPanel mainPanel;
    private JButton guessButton;
    private JLabel label;
    private JTextField[][] guesses;
    private String winWord;
    private int numOfGuess;
    private int curRow;
    private int curCol;
    private int[] curFocus;
    private String alphabet;
    
    public void run()
    {
       
        numOfGuess = 0;
        // Create top-level container
        frame = new JFrame();
        frame.setSize(300, 300);
        frame.setLocation(100,100);
        frame.setTitle("Wordle");
        frame.setResizable(false);
        
        // Create content panel without a layout manager
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        frame.setContentPane(mainPanel);
        
        // Setup label
        Font font1 = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
        label = new JLabel();
        label.setFont(font1);
        label.setHorizontalAlignment(JLabel.CENTER);
        
        // Setup button
        guessButton = new JButton("GUESS");
        guessButton.addActionListener(this);
        
        // Setup Text Field Array
        guesses = new JTextField[6][5];
        for(int i = 0; i < guesses.length; i++){
            int y = i * 35 + 10;
            for(int j = 0; j < 5; j++){
                guesses[i][j] = new JTextField();
                guesses[i][j].setSize(30, 30);
                guesses[i][j].setLocation(40 + j * 40, y);
                guesses[i][j].setFont(font1);
                guesses[i][j].addKeyListener(this);
                guesses[i][j].setCaretColor(Color.white);
                mainPanel.add(guesses[i][j]);
            }
        }
        // Setup Guess Button
        label.setLocation(50, 50);
        guessButton.setLocation(85, 225);
        label.setSize(200, 30);
        guessButton.setSize(100, 30);
        mainPanel.add(label);
        mainPanel.add(guessButton);
        
        frame.setVisible(true);
        WordDictionary word = new WordDictionary();
        try
            {
                 winWord = word.returnWord();
            }
        catch (java.io.IOException ioe)
            {
                ioe.printStackTrace();
            }
        System.out.println("Welcome to Wordle. Good Luck!");
        curCol = 0;
        curRow = 0;
        guesses[0][0].requestFocus();
        curFocus = new int[]{0,0};
    }
    public void actionPerformed(ActionEvent event) {
    if (event.getSource() == guessButton) {
        WordDictionary word = new WordDictionary();
        String guessWord = "";
            // Assemble guessWord from boxes
            for(int i = 0; i < 5; i++){
                guessWord = guessWord + guesses[numOfGuess][i].getText();
            }
            winWord = winWord.toUpperCase();
            //if(word.isWordValid(guessWord)) {
                String resultString = "";
                for(int i = 0; i < 5; i++){
                    if(guessWord.substring(i,i+1).equals(winWord.substring(i,i+1))){
                        //resultString += guessWord.substring(i,i+1).toUpperCase();
                        guesses[numOfGuess][i].setBackground(Color.GREEN);
                    }
                    else if(winWord.contains(guessWord.substring(i,i+1))){
                        //resultString += guessWord.substring(i,i+1);
                        guesses[numOfGuess][i].setBackground(Color.YELLOW);
                    }
                    else {
                        guesses[numOfGuess][i].setBackground(Color.GRAY);
                    }
                }
                for(int i = 0; i < 5; i++){
                    guesses[numOfGuess][i].setEditable(false);
                }
                System.out.println(resultString);
                numOfGuess++;
                if (numOfGuess == 6) {
                    System.out.println("The word was: " + winWord);
                }
            //} else {
            //    System.out.println("Invalid Input");
            //}
    curFocus[0] += 1;
    curFocus[1] = 0;
    guesses[curFocus[0]][curFocus[1]].requestFocus();   
            
    }
    
}
public void keyPressed(KeyEvent e){
    e.consume();
    //String backspaceString = "/b";
     String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    if(alphabet.contains(Character.toString(e.getKeyChar()))){
        if (curFocus[1] < 5){
            guesses[curFocus[0]][curFocus[1]].setText(Character.toString(e.getKeyChar()).toUpperCase());
            if (curFocus[1] != 4) {
                curFocus[1] += 1;
            }
            guesses[curFocus[0]][curFocus[1]].requestFocus();
        }
    }
    else if(e.getKeyCode() == 8){// if its a backspace character
            if(curFocus[1] != 0){
                if(guesses[curFocus[0]][curFocus[1]].getText().equals("")) {
                    guesses[curFocus[0]][curFocus[1] - 1].requestFocus();
                    guesses[curFocus[0]][curFocus[1] - 1].setText("");
                    curFocus[1] -= 1;
                } 
                else{
                    guesses[curFocus[0]][curFocus[1]].setText("");
                }
            }
        }
}

public void keyReleased(KeyEvent e){
        e.consume();
    }
public void keyTyped(KeyEvent e){
        e.consume();
    }
public void closePopUp(String toDisplay)
{
        label.setText(toDisplay);
        guessButton.setEnabled(true);
    }
public static void start() {
        SwingUtilities.invokeLater(new Board());
    }
}