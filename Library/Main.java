package Library;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        LocalDateTime mydate = LocalDateTime.now();
        DateTimeFormatter myformat = DateTimeFormatter.ofPattern("E, yyyy/MM/dd\nHH:mm:ss");
        System.out.println(mydate.format(myformat));

        System.out.println("Welcome to our library!");

        while (true) {
            System.out.println("Choose your process:\nA.Members Management\nB.Book Management\nC.Exit");
            try {
                char inputchar = input.next().toUpperCase().charAt(0);
                input.nextLine();

                if (inputchar == 'A') {
                    memberManagement(input);
                } else if (inputchar == 'B') {
                    bookManagement(input);
                } else if (inputchar == 'C') {
                    System.out.println("Thank you for using our library system. Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid input. Please enter A, B, or C.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                input.nextLine();
            }
        }
        input.close();
    }

    private static void memberManagement(Scanner input) {
        System.out.println("1.Sign up\n2.Log in\n3.Show details\n4.Edit details\n5.Remove a member\n6.Return to main menu");

        while (true) {
            try {
                System.out.print("Enter your choice (1-6): ");
                int option = input.nextInt();
                input.nextLine();

                switch (option) {
                    case 1:
                        member.singingup();
                        break;
                    case 2:
                        member.logIn();
                        break;
                    case 3:
                        int index = member.findindex();
                        member.showing(index);
                        break;
                    case 4:
                        index = member.findindex();
                        member.editing(index);
                        break;
                    case 5:
                        index = member.findindex();
                        member.removing(index);
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid option. Please enter 1-6.");
                        continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number (1-6)");
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void bookManagement(Scanner input) {
        System.out.println("1.Add a book\n2.Show details\n3.Edit details\n4.Remove a book\n5.Borrow a book\n6.Return a book\n7.Return to main menu");

        while (true) {
            try {
                System.out.print("Enter your choice (1-7): ");
                int option = input.nextInt();
                input.nextLine();

                switch (option) {
                    case 1:
                        Book.addBook();
                        break;
                    case 2:
                        Book.showDetails();
                        break;
                    case 3:
                        Book.editDetails();
                        break;
                    case 4:
                        Book.removeBook();
                        break;
                    case 5:
                        Book.borrowedBook();
                        break;
                    case 6:
                        Book.returnedBook();
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid option. Please enter 1-7.");
                        continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number (1-7)");
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}