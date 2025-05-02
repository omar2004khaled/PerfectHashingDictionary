package PerfectHashingDictionary.src;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    private static final String RESET = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String BLACK_BG = "\u001B[40m";

    private Scanner scanner;
//    private DictionaryImplementation dictionary;

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    public void start() {
        scanner = new Scanner(System.in);
        initializeDictionary();
        showOperationsMenu();
        scanner.close();
    }

    private void initializeDictionary() {
        System.out.println(
                BLACK_BG + "Enter a number corresponding to the type of the backend tree of the dictionary: " + RESET);
        System.out.print(YELLOW + "1) 'O(n)'\n2) 'O(n^2)'" + RESET + "\nAnswer >> ");

        String typeChoice = getValidChoice();
        String complexityType = typeChoice.equals("1") ? "O(n)" : "O(n^2)";

//        dictionary = new DictionaryImplementation(complexityType);
    }

    private String getValidChoice() {
        while (true) {
            try {
                String input = scanner.next();
                if (input.equals("1") || input.equals("2")) {
                    return input;
                }
                throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.print(RED + "Error!! Please enter a valid option\n" + RESET + "Answer >> ");
            }
        }
    }

    private void showOperationsMenu() {
        while (true) {
            printMenuOptions();
            try {
                int choice = scanner.nextInt();
                handleUserChoice(choice);
                if (choice == 6)
                    break;
            } catch (InputMismatchException e) {
                System.out.print(RED + "Error!! Please enter a valid option\n" + RESET);
                scanner.next(); // Clear invalid input
            }
        }
    }

    private void printMenuOptions() {
        System.out.println("-------------------------------------------------------------");
        System.out.println(BLACK_BG + "Dictionary Menu: " + RESET);
        System.out.println(YELLOW + "1) Insert a string" + RESET);
        System.out.println(YELLOW + "2) Delete a string" + RESET);
        System.out.println(YELLOW + "3) Search for a string" + RESET);
        System.out.println(YELLOW + "4) Batch insert a list of strings" + RESET);
        System.out.println(YELLOW + "5) Batch delete a list of strings" + RESET);
        System.out.println(YELLOW + "6) Exit" + RESET);
        System.out.print("Operation Number >> ");
    }

    private void handleUserChoice(int choice) {
        switch (choice) {
            case 1 -> handleInsert();
            case 2 -> handleDelete();
            case 3 -> handleSearch();
            case 4 -> handleBatchInsert();
            case 5 -> handleBatchDelete();
            case 6 -> exitProgram();
            default -> System.out.print(RED + "Error!! Please enter a valid option\n" + RESET);
        }
    }

    private void handleInsert() {
        System.out.print("Enter the string to insert >> ");

    }

    private void handleDelete() {
        System.out.print("Enter the string to delete >> ");

    }

    private void handleSearch() {
        System.out.print("Enter the string to search >> ");

    }

    private void handleBatchInsert() {
        System.out.print("Enter the path of the file to insert >> ");

    }

    private void handleBatchDelete() {
        System.out.print("Enter the path of the file to delete >> ");

    }

    private void exitProgram() {
        System.out.println(BLACK_BG + "Exiting the program..." + RESET);
    }
}