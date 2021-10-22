package by.epam.task6002.main;

import java.util.Scanner;

public class UserInput {
    private Scanner scanner = new Scanner(System.in);

    public String readLine(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}
