package school_system_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginManager {
    public static User loginUser(String username, String password) {
        Connection connection = DatabaseManager.getConnection();

        try {
            String query = "SELECT * FROM user WHERE (username=? OR email=?) AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                
                String userType = resultSet.getString("user_type");

                switch (userType) {
                    case "admin":
                        return new Admin(resultSet.getInt("id"),
                                resultSet.getString("full_name"),
                                resultSet.getString("email"),
                                resultSet.getString("password"));

                    case "student":
                        return new Student(resultSet.getInt("id"),
                                resultSet.getString("full_name"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("department"));

                    case "teacher":
                        return new Teacher(resultSet.getInt("id"),
                                resultSet.getString("full_name"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("department"),
                                resultSet.getString("subject"));

                    default:
                        return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; 
    }
}
