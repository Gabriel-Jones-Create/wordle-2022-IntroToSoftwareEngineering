import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Board implements ActionListener, Runnable
{
    private JFrame frame;
    private JPanel mainPanel;
    private JButton guessButton;
    private JLabel label;
    private JTextField[] guesses;
    private String winWord;
    private int numOfGuess;
    
    public void run()
    {
        numOfGuess = 0;
        // Create top-level container
        frame = new JFrame();
        frame.setSize(215, 300);
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
        guesses = new JTextField[6];
        for(int i = 0; i < guesses.length; i++){
            guesses[i] = new JTextField();
            guesses[i].setSize(80, 30);
            guesses[i].setLocation(65, i * 35 + 10);
            guesses[i].setFont(font1);
            mainPanel.add(guesses[i]);
        }

        label.setLocation(50, 50);
        guessButton.setLocation(50, 225);
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
    }
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == guessButton) {
            WordDictionary word = new WordDictionary();
            String guessWord = guesses[numOfGuess].getText();
            //if(word.isWordValid(guessWord)) {
                String resultString = "";
                for(int i = 0; i < 5; i++){
                    if(guessWord.substring(i,i+1).equals(winWord.substring(i,i+1))){
                        resultString += guessWord.substring(i,i+1).toUpperCase();
                    }
                    else if(winWord.contains(guessWord.substring(i,i+1))){
                        resultString += guessWord.substring(i,i+1);
                    }
                    else {
                        resultString += "-";
                    }
                }
                guesses[numOfGuess].setEditable(false);
                System.out.println(resultString);
                numOfGuess++;
                if (numOfGuess == 6) {
                    System.out.println("The word was: " + winWord);
                }
            //} else {
            //    System.out.println("Invalid Input");
            //}
        }
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