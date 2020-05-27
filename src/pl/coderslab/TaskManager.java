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

        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + "\n" + ConsoleColors.RESET + "add" + "\n" + "remove" + "\n" + "list" + "\n" + "exit");

        String option = scanner.nextLine();

    }

    public static String[][] loadData(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        File file = new File(fileName);
        Scanner scan = new Scanner(file);

        long lineCount = Files.lines(path).count();
        int count = (int)lineCount;

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
}
