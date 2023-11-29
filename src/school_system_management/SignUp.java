
package school_system_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUp {
 public static boolean registerUser(String username, String password, String fullName, String email,
                                    String department, String subject, String userType) {
     Connection connection = DatabaseManager.getConnection();

     try {
         String checkQuery = "SELECT * FROM user WHERE username=? OR email=?";
         PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
         checkStatement.setString(1, username);
         checkStatement.setString(2, email);

         ResultSet checkResult = checkStatement.executeQuery();

         if (checkResult.next()) {
             System.out.println("User with the given username or email already exists.");
             return false;
         }

         String insertQuery = "INSERT INTO user (username, password, full_name, email, department, subject, user_type) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
         PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
         insertStatement.setString(1, username);
         insertStatement.setString(2, password);
         insertStatement.setString(3, fullName);
         insertStatement.setString(4, email);
         insertStatement.setString(5, department);
         insertStatement.setString(6, subject);
         insertStatement.setString(7, userType);

         int rowsAffected = insertStatement.executeUpdate();

         return rowsAffected > 0;
     } catch (SQLException e) {
         e.printStackTrace();
     }

     return false;
 }
}
