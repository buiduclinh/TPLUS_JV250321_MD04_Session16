import java.sql.*;

public class StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public boolean updateStudent(int id, String name, int age) {
        String sql = "{CALL update_student(?, ?, ?)}";
        boolean success = false;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {

            conn.setAutoCommit(false);

            cs.setInt(1, id);
            cs.setString(2, name);
            cs.setInt(3, age);

            int rowsAffected = cs.executeUpdate();

            if (rowsAffected > 0) {
                conn.commit();
                System.out.println("Cập nhật sinh viên thành công!");
                success = true;
            } else {
                System.out.println("⚠ Không tìm thấy sinh viên có id: " + id);
                conn.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi cập nhật sinh viên. Đang rollback...");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
        return success;
    }
}