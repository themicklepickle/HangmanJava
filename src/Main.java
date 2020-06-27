package com.company;
import java.util.*;
import java.util.stream.Collectors;
import java.io.FileNotFoundException;
import java.io.File;

public class Main {

    static Scanner input = new Scanner(System.in);
    static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    static void display(char[] correct, ArrayList<Character> wrong, int numWrong, int allowed) {
        System.out.printf("%n%d/%d%n", numWrong, allowed);
        for (char c: correct) {
            System.out.printf("%s ", c);
        }
        System.out.printf("%n");
        System.out.println(wrong);
    }

    static char[] getTarget1() {
        ArrayList<String> words = new ArrayList<String>();
        try {
            File dictionary = new File("resources/sanatizedDictionary.txt");
            Scanner reader =  new Scanner(dictionary);
            while (reader.hasNextLine()) {
                words.add(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        int index = (int) (Math.random() * (words.size() - 1));
        char[]target = words.get(index).toCharArray();
        return target;
    }

    static char[] getTarget2() {
        char[] target;
        ArrayList<Character> badLetters = new ArrayList<Character>();
        while (true) {
            System.out.print("\nWord to guess: ");
            target = input.next().toCharArray();
            badLetters.clear();
            for (int i = 0; i < target.length; i++) {
                if (alphabet.indexOf(Character.toLowerCase(target[i])) == -1) {
                    badLetters.add(target[i]);
                } else {
                    target[i] = Character.toLowerCase(target[i]);
                }
            }
            if (badLetters.size() == 0) {
                return target;
            } else {
                String string = badLetters.stream().map(Object::toString)
                                          .collect(Collectors.joining(", "));
                System.out.printf("Inavlid characters: %s", string);
            }
        }
    }

    static char getGuess() {
        char guess;
        while (true) {
            System.out.print("\nGuess: ");
            guess = Character.toLowerCase(input.next().charAt(0));
            if (alphabet.indexOf(guess) != -1) return guess;
        }
    }

    static int getMode() {
        while (true) {
            System.out.print("\nOne Player (1) or Two Players (2): ");
            int mode = input.nextInt();
            if (mode == 1 || mode == 2) return mode;
        }
    }

    static boolean getPlayAgain() {
        while (true) {
            System.out.print("Play again? (y/n): ");
            char playAgain = input.next().charAt(0);
            if (playAgain == 'y') return true;
            if (playAgain == 'n') return false;
        }
    }

    static void game() {

        char[] target = getMode() == 1 ? getTarget1() : getTarget2();

        int allowedGuesses = 8;

        int count = 0;

        int letters = target.length;

        char[] correctGuesses = new char[letters];
        for (int i=0; i<correctGuesses.length; i++) {
            correctGuesses[i] = '_';
        }

        ArrayList<Character> wrongGuesses = new ArrayList<Character>();

        while (true) {

            if (count == allowedGuesses) {
                System.out.println("\nOut of guesses.");
                System.out.println(target);
                break;
            }

            if (Arrays.equals(correctGuesses, target)) {
                System.out.println("\nYou win!");
                break;
            }

            // input guess
            char guess = getGuess();

            // guess not in guesses
            if (String.valueOf(correctGuesses).indexOf(guess) == -1 && wrongGuesses.indexOf(guess) == -1) {
                boolean wrong = true;
                for (int i=0; i<letters; i++) {
                    if (target[i] == guess) {
                        wrong = false;
                        correctGuesses[i] = guess;
                    }
                }
                if (wrong) {
                    wrongGuesses.add(guess);
                    count++;
                }
            }
            display(correctGuesses, wrongGuesses, count, allowedGuesses);
        }
    }

    public static void main(String[] args) {
        boolean playAgain = true;

        while (playAgain) {
            game();
            playAgain = getPlayAgain();
        }
    }
}
