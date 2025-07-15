package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookTypeBusinessDAO implements BookTypeDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/book_data_base";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";

    @Override
    public List<entity.BookType> findAllBookTypes() {
        List<entity.BookType> bookTypes = new ArrayList<>();
        String sql = "{CALL view_all_information()}";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                entity.BookType type = new entity.BookType();
                type.setTypeId(rs.getInt("type_id"));
                type.setTypeName(rs.getString("type_name"));
                type.setTypeDescription(rs.getString("type_description"));
                type.setTypeStatus(rs.getInt("type_status"));
                bookTypes.add(type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookTypes;
    }

    @Override
    public boolean addBookType(entity.BookType bookType) {
        String sql = "{CALL add_book_type(?,?)}";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql);) {
            cs.setString(1, bookType.getTypeName());
            cs.setString(2, bookType.getTypeDescription());

            int row = cs.executeUpdate();
            return (row > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBookType(entity.BookType bookType) {
        String sql = "{CALL update_book_type(?,?,?)}";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql);) {
            cs.setInt(1, bookType.getTypeId());
            cs.setString(2, bookType.getTypeName());
            cs.setString(3, bookType.getTypeDescription());

            int row = cs.executeUpdate();
            return (row > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBookType(int id) {
        String sql = "{CALL delete_book_type(?)}";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement cs = conn.prepareCall(sql);) {
            cs.setInt(1, id);
            int row = cs.executeUpdate();
            return (row > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
