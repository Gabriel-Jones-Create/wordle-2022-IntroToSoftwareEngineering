import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
public class WordDictionary {
    public String[] words = new String[14855];
    public void main() throws Exception
    {
        // pass the path to the file as a parameter
        File file = new File("wordlewords.txt");
        Scanner sc = new Scanner(file);
        int curIndex = 0;
        while (sc.hasNextLine()){
            words[curIndex] = sc.nextLine();
            curIndex++;
        }
        boolean allTrue = true;
        Scanner sc2 = new Scanner(file);
        curIndex = 0;
        System.out.println("\"" + words[14854] + "\"");
        while (sc2.hasNextLine()){
            String nl = sc2.nextLine();
            if(!isWordValid(nl)){
                System.out.println("Not correct at " + curIndex);
                // System.out.println("\"" + nl + "\"");
                allTrue = false;
                System.out.println(nl.equals(words[curIndex]));
            }
            curIndex++;
        }
        if(allTrue){
            System.out.println("All correct.");
        }
        System.out.println("Testing incorrect word lengths:");
        if(!isWordValid("Am")){
            System.out.println("Passed length 2 test");
        }
        else{
            System.out.println("Failed length 2 test");
        }
        if(!isWordValid("AmongUs")){
            System.out.println("Passed length 7 test");
        }
        else{
            System.out.println("Failed length 7 test");
        }
        if(!isWordValid(null)){
            System.out.println("Passed null test");
        }
        else{
            System.out.println("Failed null test");
        }
    }

    /**
     * Evaluates the input
     * 
     * (0 = not there,
     * 1 = there but wrong position,
     * 2 = correct letter and position)
     * @param word
     * @return
     */
    public static int[] letterEval(String word, String correctWord){
        int[] out = new int[5];
        for(int i = 0; i < word.length(); i++){
            for(int o = 0; o < correctWord.length(); o++){
                if(correctWord.charAt(o) == word.charAt(i)){
                    if(o == i){
                        out[i] = 2;
                    }
                    else{
                        out[i] = 1;
                    }
                }
            }
        }
        return out;
    }
    public boolean isWordValid(String word){
        if(word == null){
            return false;
        } else if(word.length() != 5){
            return false;
        } else if(RecursiveBinarySearch(words, word) != -1){
            return true;
        } else{
            return false;
        }
    }
    public static int RecursiveBinarySearch(String[] arr, String toFind){
        return RecursionBinarySearch(arr, toFind, 0, arr.length - 1, arr.length / 2);
    }
    private static int RecursionBinarySearch(String[] arr, String toFind, int begin, int end, int mid){
        System.out.println(arr[0]);
        if(arr[mid].equals(toFind)){
            return mid;
        } else if(((mid == begin && mid == end) && (!arr[mid].equals(toFind))) || !(end >= begin && end < arr.length && begin >= 0)){
            return -1;
        } else if(arr[mid].compareTo(toFind) < 0){
            begin = mid + 1;
            mid = (begin + end) / 2;
            return RecursionBinarySearch(arr, toFind, begin, end, mid);
        } else {
            end = mid - 1;
            mid = (begin + end) / 2;
            return RecursionBinarySearch(arr, toFind, begin, end, mid);
        }
    }
    public static void insertionSort(String[] arr)
    {
        for(int curChecking = 1; curChecking < arr.length; curChecking++){
            int checkSave = curChecking;
            String temp = arr[curChecking];
            while(curChecking > 0 && temp.compareTo(arr[curChecking - 1]) < 0){
                arr[curChecking] = arr[curChecking - 1];
                curChecking--;
            }
            arr[curChecking] = temp;
            curChecking = checkSave;
        }
    }
    public String returnWord() throws IOException{
        Scanner sc = new Scanner(new File("wordlewords.txt"));
        for(int i = 0; i < words.length; i++){
            words[i] = sc.nextLine();
            }
        String theWord = words[(int)(Math.random()*words.length)];
        return theWord;
    }
}
