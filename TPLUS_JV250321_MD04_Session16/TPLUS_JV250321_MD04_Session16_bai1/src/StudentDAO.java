import java.sql.*;
import java.util.List;

public class StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public boolean addStudents(List<String[]> students) {
        String sql = "{CALL add_student(?, ?)}";
        boolean success = false;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {

            conn.setAutoCommit(false);

            for (String[] student : students) {
                cs.setString(1, student[0]);
                cs.setInt(2, Integer.parseInt(student[1]));
                cs.addBatch();
            }

            cs.executeBatch();
            conn.commit();
            System.out.println("Thêm tất cả sinh viên thành công.");
            success = true;

        } catch (Exception e) {
            System.out.println("Lỗi khi thêm sinh viên: " + e.getMessage());
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                conn.rollback();
                System.out.println("⚠ Đã rollback dữ liệu.");
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
        return success;
    }
}