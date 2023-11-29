package school_system_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ProfilePage {
    public ProfilePage() {
    }

    public void displayProfile(User user) {
        System.out.println("User Profile:");
        System.out.println("ID: " + user.getID());
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());

        if (user instanceof Student) {
            System.out.println("Type: Student");
            System.out.println("Department: " + ((Student) user).getDepartment());
        } else if (user instanceof Teacher) {
            System.out.println("Type: Teacher");
            System.out.println("Department: " + ((Teacher) user).getDepartment());
            System.out.println("Subject: " + ((Teacher) user).getSubject());
        } else if (user instanceof Admin) {
            System.out.println("Type: Admin");
        }

        System.out.println("----------------------------");
    }

    public void editProfile(User user, Scanner scanner) {
        System.out.println("Editing Profile:");
        System.out.println("1. Change Password");
        System.out.println("2. Change Full Name");
        System.out.println("3. Change Email");

        if (user instanceof Student || user instanceof Teacher) {
            System.out.println("4. Change Department");
        }

        if (user instanceof Teacher) {
            System.out.println("5. Change Subject");
        }
        if (user instanceof Admin) {
            System.out.println("6. Edit Student Information");
            System.out.println("7. Edit Teacher Information");
        }
        System.out.println("8. Return to Main menu");
        System.out.print("Choose an option (1-8): ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                user.setPassword(newPassword);
                updatePasswordInDatabase(user.getID(), newPassword);
                System.out.println("Password changed successfully!");
                break;

            case 2:
                System.out.print("Enter new full name: ");
                String newFullName = scanner.nextLine();
                user.setName(newFullName);
                updateFullNameInDatabase(user.getID(), newFullName);
                System.out.println("Full name changed successfully!");
                break;

            case 3:
                System.out.print("Enter new email: ");
                String newEmail = scanner.nextLine();
                user.setEmail(newEmail);
                updateEmailInDatabase(user.getID(), newEmail);
                System.out.println("Email changed successfully!");
                break;

            case 4:
                if (user instanceof Student || user instanceof Teacher) {
                    System.out.print("Enter new department: ");
                    String newDepartment = scanner.nextLine();
                    ((User) user).setDepartment(newDepartment);
                    updateDepartmentInDatabase(user.getID(), newDepartment);
                    System.out.println("Department changed successfully!");
                } else {
                    System.out.println("Invalid option for this user type.");
                }
                break;

            case 5:
                if (user instanceof Teacher) {
                    System.out.print("Enter new subject: ");
                    String newSubject = scanner.nextLine();
                    ((Teacher) user).setSubject(newSubject);
                    updateSubjectInDatabase(user.getID(), newSubject);
                    System.out.println("Subject changed successfully!");
                } else {
                    System.out.println("Invalid option for this user type.");
                }
                break;
            case 6:
                if (user instanceof Admin) {
                    editStudentInformation(scanner);
                } else {
                    System.out.println("Invalid option for this user type.");
                }
                break;

            case 7:
                if (user instanceof Admin) {
                    editTeacherInformation(scanner);
                } else {
                    System.out.println("Invalid option for this user type.");
                }
                break;
            case 8:
                break;

            default:
                System.out.println("Invalid option. No changes made.");
        }
    }

    private void updatePasswordInDatabase(int userID, String newPassword) {
        updateDataInDatabase(userID, "password", newPassword);
    }

    private void updateFullNameInDatabase(int userID, String newFullName) {
        updateDataInDatabase(userID, "full_name", newFullName);
    }

    private void updateEmailInDatabase(int userID, String newEmail) {
        updateDataInDatabase(userID, "email", newEmail);
    }

    private void updateDepartmentInDatabase(int userID, String newDepartment) {
        updateDataInDatabase(userID, "department", newDepartment);
    }

    private void updateSubjectInDatabase(int userID, String newSubject) {
        updateDataInDatabase(userID, "subject", newSubject);
    }

    private void updateDataInDatabase(int userID, String columnName, String newValue) {
        Connection connection = DatabaseManager.getConnection();

        try {
            String updateQuery = "UPDATE user SET " + columnName + " = ? WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, newValue);
            updateStatement.setInt(2, userID);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Database updated successfully.");
            } else {
                System.out.println("Failed to update the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void editStudentInformation(Scanner scanner) {
        System.out.print("Enter the student's ID to edit: ");
        int studentID = scanner.nextInt();
        scanner.nextLine();

        Student student = fetchStudentById(studentID);

        if (student != null) {
            System.out.println("Current Student Information:");
            System.out.println("ID: " + student.getID());
            System.out.println("Name: " + student.getName());
            System.out.println("Department: " + student.getDepartment());

            updateFullNameInDatabase(studentID, scanner);
            updateEmailInDatabase(studentID, scanner);
            updateDepartmentInDatabase(studentID, scanner);

            System.out.println("Student information updated successfully!");
        } else {
            System.out.println("Student not found with ID: " + studentID);
        }
    }

    private void editTeacherInformation(Scanner scanner) {
        System.out.print("Enter the teacher's ID to edit: ");
        int teacherID = scanner.nextInt();
        scanner.nextLine();

        Teacher teacher = fetchTeacherById(teacherID);

        if (teacher != null) {
            System.out.println("Current Teacher Information:");
            System.out.println("ID: " + teacher.getID());
            System.out.println("Name: " + teacher.getName());
            System.out.println("Department: " + teacher.getDepartment());
            System.out.println("Subject: " + teacher.getSubject());

            updateFullNameInDatabase(teacherID, scanner);
            updateEmailInDatabase(teacherID, scanner);
            updateDepartmentInDatabase(teacherID, scanner);
            updateSubjectInDatabase(teacherID, scanner);

            System.out.println("Teacher information updated successfully!");
        } else {
            System.out.println("Teacher not found with ID: " + teacherID);
        }
    }

    private Student fetchStudentById(int studentID) {
        Connection connection = DatabaseManager.getConnection();

        try {
            String query = "SELECT * FROM user WHERE id = ? AND user_type = 'student'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("department")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    private Teacher fetchTeacherById(int teacherID) {
        Connection connection = DatabaseManager.getConnection();

        try {
            String query = "SELECT * FROM user WHERE id = ? AND user_type = 'teacher'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, teacherID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Teacher(
                        resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("department"),
                        resultSet.getString("subject")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    private void updateFullNameInDatabase(int userID, Scanner scanner) {
        System.out.print("Enter new full name: ");
        String newFullName = scanner.nextLine();
        updateDataInDatabase(userID, "full_name", newFullName);
    }

    private void updateEmailInDatabase(int userID, Scanner scanner) {
        System.out.print("Enter new email: ");
        String newEmail = scanner.nextLine();
        updateDataInDatabase(userID, "email", newEmail);
    }

    private void updateDepartmentInDatabase(int userID, Scanner scanner) {
        System.out.print("Enter new department: ");
        String newDepartment = scanner.nextLine();
        updateDataInDatabase(userID, "department", newDepartment);
    }

    private void updateSubjectInDatabase(int userID, Scanner scanner) {
        System.out.print("Enter new subject: ");
        String newSubject = scanner.nextLine();
        updateDataInDatabase(userID, "subject", newSubject);
    }

}
