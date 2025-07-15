import java.sql.*;

public class StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public int deleteStudentsByAge(int ageThreshold) {
        String sql = "{CALL delete_students_by_age(?)}";
        int rowsDeleted = 0;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {

            conn.setAutoCommit(false);

            cs.setInt(1, ageThreshold);
            rowsDeleted = cs.executeUpdate();

            conn.commit();
            System.out.println("Đã xóa " + rowsDeleted + " sinh viên có tuổi < " + ageThreshold);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Xảy ra lỗi, đang rollback...");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }

        return rowsDeleted;
    }
}