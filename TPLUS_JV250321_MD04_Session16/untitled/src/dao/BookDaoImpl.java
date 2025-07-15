package dao;

import entity.Book;

import java.sql.*;
import java.util.*;

public class BookDaoImpl implements dao.BookDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/book_data_base";
    private static final String USER = "root";
    private static final String PASS = "123456789";

    @Override
    public List<entity.Book> getAllBook() {
        List<entity.Book> bookList = new ArrayList<>();
        String sql = "{CALL view_book_information()}";
        try (Connection conn = DriverManager.getConnection(URL);
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                entity.Book book = new entity.Book();
                book.setBookId(rs.getNString("book_id"));
                book.setBookName(rs.getString("book_name"));
                book.setBookTitle(rs.getString("book_title"));
                book.setBookPages(rs.getInt("book_pages"));
                book.setBookAuthor(rs.getString("book_author"));
                book.setBookPrice(rs.getFloat("book_price"));
                book.setTypeId(rs.getInt("type_id"));
                book.setBookStatus(rs.getInt("book_status"));
                bookList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    public boolean addBook(entity.Book book) {
        String sql = "{CALL add_book(?,?,?,?,?,?,?,?)}";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement cs = conn.prepareCall(sql);) {
            cs.setString(1, book.getBookId());
            cs.setString(2, book.getBookName());
            cs.setString(3, book.getBookTitle());
            cs.setInt(4, book.getBookPages());
            cs.setString(5, book.getBookAuthor());
            cs.setFloat(6, book.getBookPrice());
            cs.setInt(7, book.getTypeId());
            cs.setInt(8, book.getBookStatus());

            int row = cs.executeUpdate();
            return (row > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBook(entity.Book book) {
        String sql = "{CALL update_book(?,?,?,?,?,?,?)}";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement cs = conn.prepareCall(sql);) {
            cs.setString(1, book.getBookId());
            cs.setString(2, book.getBookName());
            cs.setString(3, book.getBookTitle());
            cs.setInt(4, book.getBookPages());
            cs.setString(5, book.getBookAuthor());
            cs.setFloat(6, book.getBookPrice());
            cs.setInt(7, book.getTypeId());
            cs.setInt(8, book.getBookStatus());

            int row = cs.executeUpdate();
            return (row > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //    update nhiều sách
    @Override
    public boolean addBooks(List<entity.Book> books) {
        String sql = "{CALL update_book(?,?,?,?,?,?,?)}";
        boolean suscess = false;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement cs = conn.prepareCall(sql);) {
            conn.setAutoCommit(false);

            for (entity.Book book : books) {
                cs.setString(1, book.getBookId());
                cs.setString(2, book.getBookName());
                cs.setString(3, book.getBookTitle());
                cs.setInt(4, book.getBookPages());
                cs.setString(5, book.getBookAuthor());
                cs.setFloat(6, book.getBookPrice());
                cs.setInt(7, book.getTypeId());
                cs.setInt(8, book.getBookStatus());
                cs.addBatch();
            }
            int[] rows = cs.executeBatch();
            conn.commit();

            suscess = rows.length == books.size();
            System.out.println("add" + rows.length);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println("Roll Back Transaction");
                DriverManager.getConnection(URL, USER, PASS).rollback();
            } catch (SQLException rollBackEx) {
                throw new RuntimeException(rollBackEx);
            }
        }
        return suscess;
    }

    @Override
    public boolean deleteBook(String id) {
        String sql = "{CALL delete_book(?)}";
        boolean success = false;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, id);
            int row = cs.executeUpdate();

            if (row > 0) {
                System.out.println(" Xóa sách thành công!");
                success = true;
            } else {
                System.out.println(" Không tìm thấy sách với id = " + id);
            }
        } catch (Exception e) {
            System.err.println(" Lỗi khi xóa sách: " + e.getMessage());
        }
        return success;
    }

    @Override
    public Book findBookById(String id) {
        Book book = null;
        String sql = "{CALL view_book_information_by_book_id()}";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement cs = conn.prepareCall(sql);) {
            cs.setString(1, id);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                book = new Book();
                book.setBookId(rs.getString("book_id"));
                book.setBookName(rs.getString("book_name"));
                book.setBookTitle(rs.getString("book_title"));
                book.setBookPages(rs.getInt("book_pages"));
                book.setBookAuthor(rs.getString("book_author"));
                book.setBookPrice(rs.getFloat("book_price"));
                book.setTypeId(rs.getInt("type_id"));
                book.setBookStatus(rs.getInt("book_status"));
                return book;
            } else {
                return book;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> totalBookByAuthor() {
        List<Book> books = new ArrayList<>();
        String sql = "{CALL total_book_by_author()}";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery();) {
            while (rs.next()) {
                Book book = new Book();
                book.setBookAuthor(rs.getString("book_author"));
                book.setTotalBooks(rs.getInt("total"));
                books.add(book);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return books;
    }
}
