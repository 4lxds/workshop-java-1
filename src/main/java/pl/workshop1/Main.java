package pl.workshop1;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static String[][] dataFromFile = new String[100][3];
    static int countLine = 0;

    public static void commands() {
        Scanner commandScanner = new Scanner(System.in);
        System.out.println("Enter command:");
        String command = commandScanner.nextLine().trim();

        while (true) {
            if (command.equals("exit") || command.equals("quit")) {
                quit();
                break;
            } else if (command.equals("add")) {
                add();
            } else if (command.equals("remove")) {
                remove();
            } else if (command.equals("list")) {
                list();
            } else {
                System.out.println("Invalid command, enter another command");
            }
            System.out.println("Enter command:");
            command = commandScanner.nextLine().trim();
        }
    }

    public static void saveTabToFile(String fileName) {
        String[] lines = new String[countLine];

        for (int i = 0; i < countLine; i++) {
            lines[i] = String.join(",", dataFromFile[i]);
        }

        try {
            PrintWriter writer = new PrintWriter(fileName);
            writer.println(String.join("\n", lines));
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    public static void quit() {
        saveTabToFile("./tasks.csv");
        System.out.println(ConsoleColors.RED + "Tasks saved!" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.RED + "Program Finished!!");
    }

    public static void add() {
        Scanner scanAdd = new Scanner(System.in);

        System.out.println("Enter a task:");
        String task = scanAdd.nextLine();

        System.out.println("Enter a date:");
        String date = scanAdd.nextLine();

        System.out.println("Is it important: true/false");
        String trueFalse = scanAdd.nextLine();

        dataFromFile = Arrays.copyOf(dataFromFile, countLine + 1);
        dataFromFile[countLine] = new String[3];
        dataFromFile[countLine][0] = task;
        dataFromFile[countLine][1] = date;
        dataFromFile[countLine][2] = trueFalse;
        countLine++;
    }

    public static boolean isNumericMoreThanZero(String number) {
        if (NumberUtils.isParsable(number)) {
            return Integer.parseInt(number) > 0;
        }
        return false;
    }

    public static int getNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a task to remove");

        String n = scanner.nextLine();
        while (!isNumericMoreThanZero(n)) {
            System.out.println("Incorrect task number. Please give a number greater than 0:");
            n = scanner.nextLine();
        }
        return Integer.parseInt(n);
    }

    public static void remove() {
        int n = getNumber();

        if (n <= countLine) {
            dataFromFile = ArrayUtils.remove(dataFromFile, n - 1);
            countLine--;
            System.out.println(ConsoleColors.RED_BRIGHT + "Removed task: " + n + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.CYAN + "Task does not exist, enter another task!" + ConsoleColors.RESET);
        }
    }

    public static boolean isDataFromFile() {
        if (dataFromFile.length == 0) {
            System.out.println("No tasks!");
            return false;
        }
        return true;
    }

    public static void readFromFile() {
        File csv = new File("./tasks.csv");
        int i = 0;

        try {
            Scanner scan = new Scanner(csv);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                dataFromFile[i] = line.split(",");
                i++;
            }
            countLine = i;
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    public static void list() {
        for (int i = 0; i < countLine; i++) {
            System.out.println(ConsoleColors.YELLOW + (i + 1) + " " + StringUtils.join(dataFromFile[i], ",") + ConsoleColors.RESET);
        }
    }

    public static void main(String[] args) {
        readFromFile();
        System.out.println("Commands:");

        String[] commands = {"add", "remove", "list", "exit", "quit"};
        for (int i = 0; i < commands.length; i++) {
            System.out.println(ConsoleColors.GREEN + commands[i] + ConsoleColors.RESET);
        }
        commands();
    }
}

