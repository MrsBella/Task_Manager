package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) throws IOException {

        String[][] tasks = loadData("tasks.csv");

        boolean loop = true;
        while (loop) {
            String option = optionDisplay();
            switch (option) {
                case "add":
                    tasks = add(tasks);
                    break;
                case "remove":
                    tasks = remove(tasks);
                    break;
                case "list":
                    list(tasks);
                    System.out.println();
                    break;
                case "exit":
                    loop = exit(tasks);
                    break;
                default:
                    System.out.println("Please select a correct option.");
                    System.out.println();
            }
        }
    }

    public static String[][] loadData(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        Scanner scan = new Scanner(path);

        long lineCount = Files.lines(path).count();
        int count = (int) lineCount;

        String[][] tasks = new String[count][3];

        while (scan.hasNextLine()) {

            for (int i = 0; i < tasks.length; i++) {
                String line = scan.nextLine().replaceAll(", ",";");
                String[] text = line.split(";");
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
            System.out.println(i + " : " + tasks[i][0] + "  " +  tasks[i][1] + "  " + tasks[i][2]);
        }
    }

    public static boolean exit(String[][] tasks) {
        System.out.println(ConsoleColors.RED + "Bye, bye." + ConsoleColors.RESET);

        try (FileWriter fileWriter = new FileWriter("tasks.csv", false)) {

            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < 3; j++) {
                    if (j < 2) {
                        fileWriter.append(tasks[i][j] + ", ");
                    } else {
                        fileWriter.append(tasks[i][j] + "\n");
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error writing to file.");
        }
        return false;
    }

    public static String[][] add(String[][] tasks) {
        System.out.println("add");
        System.out.println("Please add task description:");
        Scanner scanner = new Scanner(System.in);

        String task = scanner.nextLine();
        System.out.println("Please add task due date:");

        String dueDate = "";

        boolean loop = true;
        while (loop) {
            dueDate = scanner.nextLine();

            char[] date = dueDate.toCharArray();

            if ((Character.isDigit(date[0])) && (Character.isDigit(date[1])) &&
                    (Character.isDigit(date[2])) && (Character.isDigit(date[3])) && (date[4] == '-') &&
                    (Character.isDigit(date[5])) && (Character.isDigit(date[6])) && (date[7] == '-') &&
                    (Character.isDigit(date[8])) && (Character.isDigit(date[9]))) {
                System.out.println("Is your task is important: true/false:");
                loop = false;
            } else {
                System.out.println("Please enter a valid date format YYYY-MM-DD:");
            }
        }

        String important = scanner.nextLine();
        System.out.println();

        String[][] newArray = Arrays.copyOf(tasks, tasks.length + 1);

        String[] newElement = {task, dueDate, important};

        newArray[newArray.length - 1] = newElement;

        return newArray;
    }


    public static String[][] remove(String[][] tasks) {
        System.out.println("remove");
        System.out.println("Please select number to remove:");

        boolean loop = true;
        while (loop) {
            Scanner scanner = new Scanner(System.in);
            String remove = scanner.nextLine();
            boolean parsable = NumberUtils.isParsable(remove);

            if ((parsable) && (Integer.parseInt(remove) >= 0)) {

                try {
                    tasks = ArrayUtils.remove(tasks, Integer.parseInt(remove));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Incorrect argument passed. Please give number greater or equal 0:");
                    continue;
                }
                System.out.println("Value was successfully deleted.");
                loop = false;
                System.out.println();

            } else if ((!(parsable)) || (Integer.parseInt(remove) < 0)) {
                System.out.println("Incorrect argument passed. Please give number greater or equal 0:");
            }
        }
        return tasks;
    }
}
