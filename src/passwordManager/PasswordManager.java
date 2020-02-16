package passwordManager;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

public class PasswordManager {
    public static String askUserQuestion(String question, Scanner scanner) {
        System.out.println("Please enter the webite " + question + ":");
        return scanner.next();
    }

    public static void main(String[] args) throws Exception {
        Console console = System.console();
        System.out.println("Please enter your encyption password:");
        final Scanner scanner = new Scanner(System.in);
        final String encryptionPassword = new String(console.readPassword());
        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Add A New Website (Adding an existing website updates it)");
            System.out.println("2. Delete A Website");
            System.out.println("3. Get Info about A Website");
            final int choice = scanner.nextInt();
            String url, username, password;
            Website web;
            switch (choice) {

                case 0:
                    System.out.println("Thank you for using the Password Manager!");
                    scanner.close();
                    return;

                case 1:
                    url = PasswordManager.askUserQuestion("url", scanner);
                    username = PasswordManager.askUserQuestion("username", scanner);
                    password = PasswordManager.askUserQuestion("password", scanner);
                    web = new Website(url, username, password);
                    try {
                        web.save(encryptionPassword);
                        System.out.println("The website information has been saved!");
                    } catch (IOException e) {
                        System.out.println("Something went wrong and the information could not be saved!");
                    }
                    break;

                case 2:
                    url = PasswordManager.askUserQuestion("url", scanner);
                    web = Website.findWebsite(url, encryptionPassword);
                    if(web == null) {
                        System.out.println("The website url you entered can not be found!");
                        break;
                    }
                    web.delete();
                    System.out.println("The website information has been deleted!");
                    break;

                case 3:
                    url = PasswordManager.askUserQuestion("url", scanner);
                    web = Website.findWebsite(url, encryptionPassword);
                    if(web == null) {
                        System.out.println("The website url you entered can not be found!");
                        break;
                    }
                    System.out.println("Url: " + web.getUrl());
                    System.out.println("Username: " + web.getUsername());
                    System.out.println("Password: " + web.getPassword());
                    break;

                default:
                    break;
            }

        }        
    }
}