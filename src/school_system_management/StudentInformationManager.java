package school_system_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentInformationManager {
    public static List<Student> getStudentsInformation() {
        List<Student> students = new ArrayList<>();
        Connection connection = DatabaseManager.getConnection();

        try {
            String query = "SELECT full_name, department FROM user WHERE user_type = 'student'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("full_name");
                String department = resultSet.getString("department");

                Student student = new Student(0, name, "", "", department);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
}