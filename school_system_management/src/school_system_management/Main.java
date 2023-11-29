package school_system_management;

import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to Daffodil International University");
            System.out.println("1. Teachers Information");
            System.out.println("2. Students Information");
            System.out.println("3. Login");
            System.out.println("4. Sign Up");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1, 2, 3, 4, or 5): ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    displayTeachersInformation();
                    break;

                case 2:
                    displayStudentsInformation();
                    break;

                case 3:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    User loggedInUser = LoginManager.loginUser(username, password);

                    if (loggedInUser != null) {
                        ProfilePage profilePage = new ProfilePage();
                        profilePage.displayProfile(loggedInUser);

                        System.out.println("1. Edit Profile");
                        System.out.println("2. Return Main Menu");
                        System.out.print("Choose an option (1 or 2): ");
                        int profileOption = scanner.nextInt();
                        scanner.nextLine();
                        switch (profileOption) {
                            case 1:
                                profilePage.editProfile(loggedInUser, scanner);
                                break;

                            case 2:
                                break;

                            default:
                                System.out.println("Invalid option.");
                        }
                    } else {
                        System.out.println("Login failed. Invalid username or password.");
                    }
                    break;

                case 4:
                    System.out.print("Enter username: ");
                    String signUpUsername = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String signUpPassword = scanner.nextLine();

                    System.out.print("Enter full name: ");
                    String fullName = scanner.nextLine();

                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter department (for students and teachers): ");
                    String department = scanner.nextLine();

                    System.out.print("Enter subject (for teachers): ");
                    String subject = scanner.nextLine();

                    System.out.print("Enter user type (admin, student, teacher): ");
                    String userType = scanner.nextLine();

                    boolean registrationSuccess = SignUp.registerUser(signUpUsername, signUpPassword, fullName, email, department, subject, userType);

                    if (registrationSuccess) {
                        System.out.println("Registration successful! You can now log in.");
                    } else {
                        System.out.println("Registration failed. Please try again.");
                    }

                    break;

                case 5:
                    System.out.println("Exiting program. Thank You");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    private static void displayTeachersInformation() {
        List<Teacher> teachers = TeacherInformationManager.getTeachersInformation();
        System.out.println("Teachers Information:");
        for (Teacher teacher : teachers) {
            System.out.println("Name: " + teacher.getName());
            System.out.println("Department: " + teacher.getDepartment());
            System.out.println("Subject: " + teacher.getSubject());
            System.out.println("----------------------------");
        }
    }

    private static void displayStudentsInformation() {
        List<Student> students = StudentInformationManager.getStudentsInformation();
        System.out.println("Students Information:");
        for (Student student : students) {
            System.out.println("Name: " + student.getName());
            System.out.println("Department: " + student.getDepartment());
            System.out.println("----------------------------");
        }
    }
  
}
