package main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        firstMenu();
        String menu = scanner.nextLine();
        while (!menu.equalsIgnoreCase("q")) {
            switch (menu) {
                case "1":
                    createFile(scanner);
                    menu = showMenu(scanner);
                    break;
                case "2":
                    loadDictionary(scanner);
                default:
                    menu = showMenu(scanner);
                    break;
            }
        }
    }

    private static void loadDictionary(Scanner scanner) throws IOException {

        System.out.println("Please enter dictionary name: ");
        String input = scanner.nextLine();
        File loadedDictionary = new File(input + ".txt");
        if (loadedDictionary.exists()) {
            System.out.println("Dictionary has been loaded");
            System.out.println();
            addOrViewDictionary();
            String yesOrNoOrShowOrFindInDictionary = addTranslationOrViewDictionary(scanner, loadedDictionary);
            if (yesOrNoOrShowOrFindInDictionary.equalsIgnoreCase("v")) {
                System.out.println(new String(Files.readAllBytes(
                        Paths.get(String.valueOf(loadedDictionary)))));
            }
            if (yesOrNoOrShowOrFindInDictionary.equalsIgnoreCase("f")) {
                findingTranslation(scanner, loadedDictionary);
            }
        } else {
            System.out.println("File not found, please enter valid file name");
        }

    }

    private static void findingTranslation(Scanner scanner, File loadedDictionary) {
        try {
            System.out.println("Enter translation to find: ");
            String findWord = scanner.nextLine();
            scanner = new Scanner(loadedDictionary);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(findWord)) {
                    System.out.println(line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    private static String addTranslationOrViewDictionary(Scanner scanner, File loadedDictionary) {
        String yesOrNoOrShowDictionary = scanner.nextLine();
        while (yesOrNoOrShowDictionary.equalsIgnoreCase("y")) {
            System.out.println("Enter word in English: ");
            String inEnglish = scanner.nextLine();
            System.out.println("Enter translation to Estonian: ");
            String inEstonian = scanner.nextLine();
            addOrViewDictionary();
            yesOrNoOrShowDictionary = scanner.nextLine();
            try (FileWriter fw = new FileWriter(loadedDictionary, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println("\"" + inEnglish + "\"" + " in English, is " + "\"" + inEstonian + "\"" + " in Estonian.");
            } catch (IOException ignored) {
            }
        }
        return yesOrNoOrShowDictionary;
    }

    private static void addOrViewDictionary() {
        System.out.println("If You want to add new translation to Your dictionary, enter \"y\", otherwise, enter \"n\"");
        System.out.println("If You want to view dictionary, press \"v\".");
        System.out.println("If You want to find translation, please press \"f\".");
    }

    private static void createFile(Scanner scanner) throws IOException {
        System.out.print("Enter the name of Your new dictionary: ");
        String fileName = scanner.nextLine();
        fileName = fileName + ".txt";

        File file = new File(fileName);
        if (file.createNewFile()) {
            System.out.println("Dictionary has been created.");
        } else {
            System.out.println();
            System.out.println("File already exists.");
        }
        System.out.println("If You want to add new translation to Your dictionary, enter \"y\"," +
                " otherwise, enter \"n\"");
        String yesOrNo = scanner.nextLine();
        while (yesOrNo.equalsIgnoreCase("y")) {
            System.out.println("Enter word in English: ");
            String inEnglish = scanner.nextLine();
            System.out.println("Enter translation to Estonian: ");
            String inEstonian = scanner.nextLine();
            System.out.println("If You want to add new translation to Your dictionary, enter \"y\", otherwise, enter \"n\"");
            yesOrNo = scanner.nextLine();
            try (FileWriter fw = new FileWriter(fileName, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println("\"" + inEnglish + "\"" + " in English, is " + "\"" + inEstonian + "\"" + " in Estonian.");
            } catch (IOException e) {
                System.out.println("File not found");
            }
        }
    }

    private static void firstMenu() {
        System.out.println("To create new Dictionary, please press 1 \n"
                + "To load Dictionary, please press 2 \n"
                + "To quit program, please type \"Quit\"");
    }

    private static String showMenu(Scanner scanner) {
        String menu;
        System.out.println();
        System.out.println("To create new Dictionary, please press 1 \n"
                + "To load Dictionary, please press 2 \n"
                + "To quit program, please type \"q\"");
        System.out.println();
        menu = scanner.nextLine();
        return menu;
    }
}
