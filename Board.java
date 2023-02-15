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
    private int [] numInWinWord = new int[26];
    private DBInterface access;
    public void run()
    {
        access = new DBInterface("WordleData.accdb");
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
                guesses[i][j].setForeground(Color.GRAY);
                guesses[i][j].setHorizontalAlignment(JTextField.CENTER);
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
        numInWinWord = new int[26];
        winWord = winWord.toUpperCase();
        System.out.println("Welcome to Wordle. Good Luck!");
        curCol = 0;
        curRow = 0;
        guesses[0][0].requestFocus();
        System.out.println(winWord);
        curFocus = new int[]{0,0};
    }
    private void onGuess(){
            //WordDictionary word = new WordDictionary();
            String guessWord = "";
            for(int i = 0; i < winWord.length(); i++){
                    numInWinWord[winWord.charAt(i) - 65] += 1; 
            }
            int [] tempNumInWinWord = numInWinWord;
                // Assemble guessWord from boxes
                for(int i = 0; i < 5; i++){
                    guessWord = guessWord + guesses[numOfGuess][i].getText();
                }
                //if(word.isWordValid(guessWord)) {
                    String resultString = "";
                    for(int i = 0; i < 5; i++){
                        if(winWord.contains(guessWord.substring(i,i+1))){
                            if(guessWord.substring(i,i+1).equals(winWord.substring(i,i+1))){
                            //resultString += guessWord.substring(i,i+1).toUpperCase();
                            tempNumInWinWord[winWord.charAt(i)-65]--; 
                            guesses[numOfGuess][i].setBackground(Color.GREEN);
                                }
                        }
                    }
                    for(int i = 0; i < 5; i++){
                        if (tempNumInWinWord[winWord.charAt(i)-65] != 0 && winWord.contains(guessWord.substring(i,i+1)) && !guesses[numOfGuess][i].getBackground().equals(Color.GREEN)){
                            //resultString += guessWord.substring(i,i+1);
                                guesses[numOfGuess][i].setBackground(Color.YELLOW);
                            }
                            else if(!winWord.contains(guessWord.substring(i,i+1)))
                                guesses[numOfGuess][i].setBackground(Color.GRAY);
                                guesses[numOfGuess][i].setForeground(Color.WHITE); 
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
    public void actionPerformed(ActionEvent event) {
    if (event.getSource() == guessButton) {
        onGuess();
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
    else if(e.getKeyCode() == 10 && !guesses[curFocus[0]][4].equals("")){
        onGuess();   
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