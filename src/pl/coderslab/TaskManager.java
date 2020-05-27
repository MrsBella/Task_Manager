package pl.coderslab;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) throws IOException {

        String[][] tasks = loadData("tasks.csv");

        boolean loop = true;

        while (loop) {
            String option = optionDisplay();
            switch (option) {
                case "list":
                    list(tasks);
                    System.out.println();
                    break;
                case "exit":
                    loop = exit();
                    break;
                default:
                    System.out.println("Please select a correct option.");
                    System.out.println();
            }
        }

    }

    public static String[][] loadData(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        File file = new File(fileName);
        Scanner scan = new Scanner(file);

        long lineCount = Files.lines(path).count();
        int count = (int) lineCount;

        String[][] tasks = new String[count][3];


        while (scan.hasNextLine()) {

            for (int i = 0; i < tasks.length; i++) {
                String[] text = scan.nextLine().split(",");
                for (int j = 0; j < 3; j++) {
                    tasks[i][j] = text[j];
                }
            }
        }
        return tasks;
    }

    public static String optionDisplay() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);

        String[] optional = {"add", "remove", "list", "exit"};

        for (int i = 0; i < optional.length; i++) {
            System.out.println(optional[i]);
        }

        String option = scanner.nextLine();

        return option;
    }

    public static void list(String[][] tasks) {
        System.out.println("list");

        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(i + " : " + tasks[i][j] + " ");
            }
            System.out.println();
        }

    }

    public static boolean exit() {
        System.out.println(ConsoleColors.RED + "Bye, bye." + ConsoleColors.RESET);
        return false;
    }

}
