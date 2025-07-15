import java.sql.*;

public class BankDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/bank_management";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public boolean transferFunds(int fromAccountId, int toAccountId, double amount) {
        String sql = "{CALL transfer_funds(?, ?, ?)}";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {

            conn.setAutoCommit(false);

            cs.setInt(1, fromAccountId);
            cs.setInt(2, toAccountId);
            cs.setDouble(3, amount);

            cs.execute();

            conn.commit();
            System.out.println("Chuyển " + amount + " từ tài khoản " + fromAccountId + " sang tài khoản " + toAccountId + " thành công.");
            return true;

        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
            try {
                DriverManager.getConnection(URL, USER, PASSWORD).rollback();
                System.out.println("Đã rollback transaction.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
}