package com.company;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter; 

public class Sanatize {
    public static void main(String[] args) {

        // read dictionary file
        ArrayList<char[]> words = new ArrayList<char[]>();
        try {
            File dictionary = new File("resources/dictionary.txt");
            Scanner reader =  new Scanner(dictionary);
            while (reader.hasNextLine()) {
                words.add(reader.nextLine().toCharArray());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // sanitize words and write to new file
        try {
            FileWriter myWriter = new FileWriter("resources/sanatizedDictionary.txt");
            ArrayList<Character> sanitized = new ArrayList<Character>();
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            for (char[] word : words) {
                if (word.length < 5) continue;
                sanitized.clear();
                boolean valid = true;
                for (char c : word) {
                    c = Character.toLowerCase(c);
                    if (alphabet.indexOf(c) == -1) valid = false;
                    else sanitized.add(c);
                }
                if (valid) {
                    for (char c : sanitized) {
                        myWriter.write(c);
                    }
                    myWriter.write("\n");
                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}