package school_system_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherInformationManager {
    public static List<Teacher> getTeachersInformation() {
        List<Teacher> teachers = new ArrayList<>();
        Connection connection = DatabaseManager.getConnection();

        try {
            String query = "SELECT full_name, department, subject FROM user WHERE user_type = 'teacher'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("full_name");
                String department = resultSet.getString("department");
                String subject = resultSet.getString("subject");

                Teacher teacher = new Teacher(0, name, "", "", department, subject);
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teachers;
    }

	
	}
