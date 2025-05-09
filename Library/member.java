package Library;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

class MemberNotFoundException extends Exception {
    public MemberNotFoundException(String message) {
        super(message);
    }
}

class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

class InvalidMemberOperationException extends Exception {
    public InvalidMemberOperationException(String message) {
        super(message);
    }
}

public class member extends person {
    private int ID;
    private int password;
    private boolean validation = false;
    private static ArrayList<member> membersList = new ArrayList<>();
    private static Random rnd = new Random();
    private static Scanner input = new Scanner(System.in);

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public boolean getValidation() {
        return validation;
    }

    public void setValidation(boolean validation) {
        this.validation = validation;
    }

    public static void removing(int index) {
        try {
            if (index < 0 || index >= membersList.size()) {
                throw new MemberNotFoundException("There is no member with this ID");
            }
            System.out.println("Sorry for your quitting, Good luck anyway!");
            membersList.remove(index);
        } catch (MemberNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void singingup() {
        member myMember = new member();

        try {
            System.out.println("Enter your name:");
            String name = input.nextLine();
            if (name.trim().isEmpty()) {
                throw new InvalidInputException("Name cannot be empty");
            }
            myMember.setName(name);

            System.out.println("Enter your gender:(M for male, F for female)");
            String genderInput = input.nextLine().toUpperCase();
            if (!genderInput.equals("M") && !genderInput.equals("F")) {
                throw new InvalidInputException("Gender must be M or F");
            }
            myMember.setGender(genderInput.charAt(0));

            System.out.println("Enter your age:");
            if (!input.hasNextInt()) {
                throw new InvalidInputException("Age must be a number");
            }
            int age = input.nextInt();
            if (age <= 0 || age > 120) {
                throw new InvalidInputException("Age must be between 1 and 120");
            }
            myMember.setAge(age);
            input.nextLine();

            myMember.setID(rnd.nextInt(90000) + 1000);
            myMember.setPassword(rnd.nextInt(1000000000));

            membersList.add(myMember);
            myMember.setValidation(true);

            System.out.println("Hello " + myMember.getName() +
                    "\nWelcome to our library\n" +
                    "Here is your profile:\n" +
                    "Your name is: " + myMember.getName() + "\n" +
                    "Your gender is: " + myMember.getGender() + "\n" +
                    "Your age is: " + myMember.getAge() + "\n" +
                    "Here is your ID: " + myMember.getID() + "\n" +
                    "Here is your password: " + myMember.getPassword());

        } catch (InputMismatchException e) {
            input.nextLine();
            System.out.println("Invalid input format. Please try again.");
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void logIn() {
        try {
            int memberIndex = searchMemberIndex();
            if (memberIndex == -1) {
                throw new MemberNotFoundException("Member not found with the provided ID");
            }

            System.out.println("Enter your Password:");
            if (!input.hasNextInt()) {
                throw new InvalidInputException("Password must be numeric");
            }
            int passwordTest = input.nextInt();
            input.nextLine();

            if (membersList.get(memberIndex).getPassword() != passwordTest) {
                throw new InvalidMemberOperationException("Incorrect password");
            }

            membersList.get(memberIndex).setValidation(true);
            System.out.println("You logged in successfully!");
            showing(memberIndex);
        } catch (InputMismatchException e) {
            input.nextLine();
            System.out.println("Invalid input format. Please try again.");
        } catch (MemberNotFoundException | InvalidInputException | InvalidMemberOperationException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int searchMemberIndex() {
        try {
            System.out.println("Enter your ID:");
            int testID = input.nextInt();
            input.nextLine();

            for (int i = 0; i < membersList.size(); i++) {
                if (testID == membersList.get(i).getID()) {
                    return i;
                }
            }
            return -1;
        } catch (InputMismatchException e) {
            input.nextLine();
            System.out.println("ID must be a number");
            return -1;
        }
    }

    public static int findindex() {
        System.out.println("Enter your ID to access to your profile");
        int Idtest = input.nextInt();
        for (int i = 0; i < membersList.size(); i++) {
            if (membersList.get(i).ID == Idtest)
                return i;
        }
        return -1;
    }

    public static void showing(int index) {
        try {
            if (index < 0 || index >= membersList.size()) {
                throw new MemberNotFoundException("There is no member with this ID");
            }
            if (!membersList.get(index).getValidation()) {
                throw new InvalidMemberOperationException("Member not validated");
            }

            System.out.println("Here is your profile:\n" +
                    "Your name is: " + membersList.get(index).getName() + "\n" +
                    "Your gender is: " + membersList.get(index).getGender() + "\n" +
                    "Your age is: " + membersList.get(index).getAge() + "\n" +
                    "Here is your ID: " + membersList.get(index).getID());
        } catch (MemberNotFoundException | InvalidMemberOperationException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void editing(int index) {
        try {
            if (index < 0 || index >= membersList.size()) {
                throw new MemberNotFoundException("There is no member with this ID");
            }
            if (!membersList.get(index).getValidation()) {
                throw new InvalidMemberOperationException("Member not validated");
            }

            boolean keepEditing = true;
            while (keepEditing) {
                System.out.println("Which part of your profile is needed to edit?\n" +
                        "1.name\n2.gender\n3.age(ID can't be edited!)\n4.done editing");

                try {
                    int option = input.nextInt();
                    input.nextLine();

                    switch (option) {
                        case 1: {
                            System.out.println("Enter new name:");
                            String name = input.nextLine();
                            if (name.trim().isEmpty()) {
                                throw new InvalidInputException("Name cannot be empty");
                            }
                            membersList.get(index).setName(name);
                            break;
                        }
                        case 2: {
                            System.out.println("Enter new gender (M/F):");
                            String genderInput = input.nextLine().toUpperCase();
                            if (!genderInput.equals("M") && !genderInput.equals("F")) {
                                throw new InvalidInputException("Gender must be M or F");
                            }
                            membersList.get(index).setGender(genderInput.charAt(0));
                            break;
                        }
                        case 3: {
                            System.out.println("Enter new age:");
                            if (!input.hasNextInt()) {
                                throw new InvalidInputException("Age must be a number");
                            }
                            int age = input.nextInt();
                            input.nextLine();
                            if (age <= 0 || age > 120) {
                                throw new InvalidInputException("Age must be between 1 and 120");
                            }
                            membersList.get(index).setAge(age);
                            break;
                        }
                        case 4: {
                            keepEditing = false;
                            break;
                        }
                        default: {
                            System.out.println("Not valid input, try again");
                            continue;
                        }
                    }

                    System.out.println("\nProfile updated successfully!");
                    showing(index);
                } catch (InputMismatchException e) {
                    input.nextLine();
                    System.out.println("Invalid input format. Please try again.");
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (MemberNotFoundException | InvalidMemberOperationException e) {
            System.out.println(e.getMessage());
        }
    }
}
